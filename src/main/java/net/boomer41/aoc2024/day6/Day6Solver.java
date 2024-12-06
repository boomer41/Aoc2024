package net.boomer41.aoc2024.day6;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day6Solver {

    public static long solve1(InputStream inputStream) throws IOException {
        var map = Map.parse(inputStream);
        map.walkUntilGuardExistsOrHitsLoop();

        return map.countNumberOfUniqueWalkedFields();
    }

    public static long solve2(InputStream inputStream) throws IOException {
        var map = Map.parse(inputStream);
        return map.findMapCountWithLoops();
    }

}
