package net.boomer41.aoc2024.day9;

import lombok.Data;
import lombok.ToString;

@Data
public class DiskMapEntry {

    private int blockId;

    private File file;

    private int length;

    private boolean invalid;

    @ToString.Exclude
    private DiskMapEntry next;

    @ToString.Exclude
    private DiskMapEntry previous;

    public DiskMapEntry(int blockId, File file, int length) {
        this.blockId = blockId;
        this.file = file;
        this.length = length;
    }

}
