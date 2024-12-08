package net.boomer41.aoc2024.day8;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day8Solver {

    public static long solve1(InputStream inputStream) throws IOException {
        var map = WorldMap.parse(inputStream);
        var antinodes = map.calculateAntinodes();

        var seenLocations = new HashSet<>();

        for (var antinode : antinodes) {
            seenLocations.add(antinode.getPosition());
        }

        return seenLocations.size();
    }

    public static long solve2(InputStream inputStream) throws IOException {
        var map = WorldMap.parse(inputStream);
        var antinodes = map.calculateAntinodesWithNoLimit();

        var seenLocations = new HashSet<>();

        for (var antinode : antinodes) {
            seenLocations.add(antinode.getPosition());
        }

        return seenLocations.size();
    }

}
