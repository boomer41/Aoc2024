package net.boomer41.aoc2024.day14;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.boomer41.aoc2024.util.Vector2I;

import java.io.InputStream;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day14Solver {

    public static long solve1(InputStream inputStream, Vector2I bounds) {
        var map = RobotMap.fromInput(inputStream, bounds);
        map.tickAll(100);
        return map.getSafetyFactor();
    }

    public static Optional<Long> solve2(InputStream inputStream, Vector2I bounds) {
        var map = RobotMap.fromInput(inputStream, bounds);

        for (var i = 0; i < 100000; i++) {
            map.tickAll(1);

            if (map.containsTree()) {
                return Optional.of((long) map.getTicks());
            }
        }

        return Optional.empty();
    }

}
