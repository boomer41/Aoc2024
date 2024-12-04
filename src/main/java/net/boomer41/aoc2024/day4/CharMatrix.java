package net.boomer41.aoc2024.day4;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CharMatrix {

    private final int columns;

    private final char[] data;

    public static CharMatrix read(InputStream inputStream) throws IOException, InvalidCharMatrixException {
        var bytes = inputStream.readAllBytes();
        var string = new String(bytes, StandardCharsets.US_ASCII);

        var lines = Arrays.stream(string
                .split("\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        var columns = 0;
        var lineIdx = 0;
        char[] data = null;

        for (var line : lines) {
            // First line
            if (data == null) {
                columns = line.length();
                data = new char[columns * lines.size()];
            }

            if (columns != line.length()) {
                throw new InvalidCharMatrixException("Line lengths are not uniform");
            }

            var chars = line.toCharArray();
            System.arraycopy(chars, 0, data, lineIdx * columns, chars.length);
            lineIdx++;
        }

        return new CharMatrix(columns, data);
    }

    public long findWordCount(String word) {
        var wordChars = word.toCharArray();
        var count = 0L;

        for (var y = 0; y < (data.length / columns); y++) {
            for (var x = 0; x < columns; x++) {
                count += matchCharacterGrid(x, y, wordChars);
            }
        }

        return count;
    }

    private long matchCharacterGrid(int x, int y, char[] chars) {
        var count = 0L;

        for (var deltaX = -1; deltaX <= 1; deltaX++) {
            for (var deltaY = -1; deltaY <= 1; deltaY++) {
                count += matchCharacterGridInstance(x, y, chars, deltaX, deltaY) ? 1 : 0;
            }
        }

        return count;
    }

    private boolean matchCharacterGridInstance(int x, int y, char[] chars, int deltaX, int deltaY) {
        for (char charToMatch : chars) {
            if (!isValidPosition(x, y)) {
                return false;
            }

            var foundChar = getChar(x, y);

            if (charToMatch != foundChar) {
                return false;
            }

            x += deltaX;
            y += deltaY;
        }

        return true;
    }

    public long findWordCountAsCross(String word) {
        var wordChars = word.toCharArray();
        var count = 0L;

        for (var y = 0; y < (data.length / columns); y++) {
            for (var x = 0; x < columns; x++) {
                if (!matchLocationForCross(x, y, wordChars)) {
                    continue;
                }

                count++;
            }
        }

        return count;
    }

    private boolean matchLocationForCross(int x, int y, char[] chars) {
        // First part: Down Right or Up Left
        var firstPart = matchCharacterGridInstance(x, y, chars, 1, 1) ||
                matchCharacterGridInstance(x + chars.length - 1, y + chars.length - 1, chars, -1, -1);

        if (!firstPart) {
            return false;
        }

        // Second part: Down Left or Up Right
        return matchCharacterGridInstance(x + chars.length - 1, y, chars, -1, 1) ||
                matchCharacterGridInstance(x, y + chars.length - 1, chars, 1, -1);
    }

    private boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < columns && y < (data.length / columns);
    }

    private char getChar(int x, int y) {
        return data[y * columns + x];
    }

}
