package net.boomer41.aoc2024.day2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day2Solver {

    public static long solve1(InputStream inputStream) throws InvalidInputException {
        var reports = Report.parseReports(inputStream);

        return reports.stream()
                .filter(Report::isValid)
                .count();
    }

    public static long solve2(InputStream inputStream) throws InvalidInputException {
        var reports = Report.parseReports(inputStream);

        return reports.stream()
                .filter(Report::isValidWithDampener)
                .count();
    }

}
