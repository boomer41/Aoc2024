package net.boomer41.aoc2024.day9;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DiskMap {

    private DiskMapEntry head;

    private DiskMapEntry tail;

    public static DiskMap fromCompressedMap(String map) {
        var characters = map.toCharArray();
        var isFreeBlock = false;
        var fileId = 0;
        var blockId = 0;

        DiskMapEntry head = null;
        DiskMapEntry previousEntry = null;

        for (var character : characters) {
            if (!(character >= '0' && character <= '9')) {
                throw new NumberFormatException("Invalid number \"" + character + "\"");
            }

            var length = (int) character - '0';

            File file = null;

            if (!isFreeBlock) {
                file = new File(fileId++, length);
            }

            isFreeBlock = !isFreeBlock;

            if (length == 0) {
                continue;
            }

            var entry = new DiskMapEntry(blockId, file, length);
            blockId += length;

            if (head == null) {
                head = entry;
            }

            if (previousEntry != null) {
                previousEntry.setNext(entry);
            }

            entry.setPrevious(previousEntry);
            previousEntry = entry;
        }

        return new DiskMap(head, previousEntry);
    }

    public String toExpandedRepresentation() {
        var sb = new StringBuilder();

        var currentNode = head;

        while (currentNode != null) {
            if (currentNode.getFile() == null) {
                sb.append(".".repeat(currentNode.getLength()));
            } else {
                sb.append(String.valueOf((char) ('0' + currentNode.getFile().getId())).repeat(currentNode.getLength()));
            }

            currentNode = currentNode.getNext();
        }

        return sb.toString();
    }

    public void consolidate(boolean onlyFullFile) {
        if (onlyFullFile) {
            consolidateNonFragmented();
        } else {
            consolidateFragmented();
        }
    }

    private void consolidateNonFragmented() {
        var fragments = new ArrayList<DiskMapEntry>();

        for (var entry = head; entry != null; entry = entry.getNext()) {
            if (entry.getFile() == null) {
                continue;
            }

            if (entry.getLength() != entry.getFile().getSize()) {
                continue;
            }

            fragments.add(entry);
        }

        fragments.sort(Comparator.comparing(f -> -f.getFile().getId()));


        for (var fragment : fragments) {
            var freeBlock = head;

            while (true) {
                while (freeBlock != null && freeBlock.getFile() != null) {
                    freeBlock = freeBlock.getNext();
                }

                if (freeBlock == null) {
                    break;
                }

                if (freeBlock.getLength() < fragment.getLength()) {
                    freeBlock = freeBlock.getNext();
                    continue;
                }

                if (freeBlock.getBlockId() >= fragment.getBlockId()) {
                    freeBlock = freeBlock.getNext();
                    continue;
                }

                break;
            }

            if (freeBlock == null) {
                continue;
            }

            consolidateBlocks(fragment, freeBlock);
        }
    }

    private void consolidateFragmented() {
        var freeBlock = head;
        var fileEntry = tail;

        while (true) {
            while (freeBlock != null && freeBlock.getFile() != null) {
                freeBlock = freeBlock.getNext();
            }

            if (freeBlock == null) {
                break;
            }

            while (fileEntry != null && fileEntry.getFile() == null) {
                fileEntry = fileEntry.getPrevious();
            }

            if (fileEntry == null) {
                break;
            }

            // Done!
            if (freeBlock.getBlockId() >= fileEntry.getBlockId()) {
                break;
            }

            consolidateBlocks(fileEntry, freeBlock);
        }
    }

    private void consolidateBlocks(DiskMapEntry fileEntry, DiskMapEntry freeBlock) {
        if (fileEntry.getFile() == null) {
            throw new IllegalArgumentException("File entry must reference a file");
        }

        if (freeBlock.getFile() != null) {
            throw new IllegalArgumentException("Free block entry must not reference a file");
        }

        final var toMove = Math.min(freeBlock.getLength(), fileEntry.getLength());

        if (toMove != freeBlock.getLength()) {
            var stillFree = freeBlock.getLength() - toMove;

            var newFreeBlock = new DiskMapEntry(freeBlock.getBlockId() + toMove, null, stillFree);
            var nextBlock = freeBlock.getNext();

            newFreeBlock.setNext(nextBlock);
            newFreeBlock.setPrevious(freeBlock);
            freeBlock.setNext(newFreeBlock);

            if (nextBlock != null) {
                nextBlock.setPrevious(newFreeBlock);
            }

            freeBlock.setLength(toMove);
            freeBlock.setFile(fileEntry.getFile());
        } else {
            freeBlock.setFile(fileEntry.getFile());
        }

        if (toMove != fileEntry.getLength()) {
            var newFileBlockSize = fileEntry.getLength() - toMove;

            var newFreeBlock = new DiskMapEntry(fileEntry.getBlockId() + newFileBlockSize, null, toMove);

            newFreeBlock.setPrevious(fileEntry);
            newFreeBlock.setNext(fileEntry.getNext());

            fileEntry.setNext(newFreeBlock);

            if (newFreeBlock.getNext() != null) {
                newFreeBlock.getNext().setPrevious(newFreeBlock);
            }

            fileEntry.setLength(newFileBlockSize);
        } else {
            fileEntry.setFile(null);
        }

        freeBlock = mergeBlocks(freeBlock, freeBlock.getNext(), freeBlock);
        mergeBlocks(freeBlock.getPrevious(), freeBlock, freeBlock);

        fileEntry = mergeBlocks(fileEntry, fileEntry.getNext(), fileEntry);
        mergeBlocks(fileEntry.getPrevious(), fileEntry, fileEntry);
    }

    private DiskMapEntry mergeBlocks(DiskMapEntry a, DiskMapEntry b, DiskMapEntry noopDefault) {
        if (a == null || b == null) {
            return noopDefault;
        }

        if (a.getFile() != b.getFile()) {
            return noopDefault;
        }

        if (a.isInvalid() || b.isInvalid()) {
            return noopDefault;
        }

        var next = b.getNext();
        a.setNext(next);

        if (next != null) {
            next.setPrevious(a);
        }

        a.setLength(a.getLength() + b.getLength());
        b.setInvalid(true);
        return a;
    }

    public long calculateChecksum() {
        var checksum = 0L;

        for (var block = head; block != null; block = block.getNext()) {
            if (block.getFile() == null) {
                continue;
            }

            for (var i = 0; i < block.getLength(); i++) {
                checksum += ((long) block.getBlockId() + i) * block.getFile().getId();
            }
        }

        return checksum;
    }

}
