package net.boomer41.aoc2024.day11;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day11Solver {

    public static long solve1(String input) {
        var map = StoneMap.fromString(input);

        return map.blink(25);
    }

    public static long solve2(String input) {
        var map = StoneMap.fromString(input);

        return map.blink(75);
    }

}
