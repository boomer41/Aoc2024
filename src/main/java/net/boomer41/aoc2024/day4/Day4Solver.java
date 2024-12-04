package net.boomer41.aoc2024.day4;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day4Solver {

    public static long solve1(InputStream inputStream) throws InvalidCharMatrixException, IOException {
        return CharMatrix.read(inputStream).findWordCount("XMAS");
    }

    public static long solve2(InputStream inputStream) throws InvalidCharMatrixException, IOException {
        return CharMatrix.read(inputStream).findWordCountAsCross("MAS");
    }

}
