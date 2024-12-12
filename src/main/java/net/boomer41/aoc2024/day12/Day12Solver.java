package net.boomer41.aoc2024.day12;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day12Solver {

    public static long solve1(InputStream inputStream) {
        var map = RegionMap.fromInputStream(inputStream);
        var regions = map.getRegions();

        return regions.stream()
                .mapToLong(r -> r.getPerimeter() * r.getArea())
                .sum();
    }

    public static long solve2(InputStream inputStream) {
        var map = RegionMap.fromInputStream(inputStream);
        var regions = map.getRegions();

        return regions.stream()
                .mapToLong(r -> r.getSides() * r.getArea())
                .sum();
    }

}
