package net.boomer41.aoc2024.day5;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day5Solver {

    public static long solve1(InputStream inputStream) throws IOException {
        return PrinterQueue.parse(inputStream).getSumOfMiddleNumbersOfValidJobs();
    }

    public static long solve2(InputStream inputStream) throws IOException {
        return PrinterQueue.parse(inputStream).getSumOfMiddleNumbersOfIncorrectButFixedJobs();
    }

}
