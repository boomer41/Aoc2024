package net.boomer41.aoc2024.day10;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day10Solver {

    public static long solve1(InputStream inputStream, boolean floydWarshall) throws IOException {
        var map = HikeMap.fromData(inputStream);
        return map.getTotalScoreOfTrails(floydWarshall);
    }

    public static long solve2(InputStream inputStream) throws IOException {
        var map = HikeMap.fromData(inputStream);
        return map.getTotalRatingOfTrails();
    }

}
