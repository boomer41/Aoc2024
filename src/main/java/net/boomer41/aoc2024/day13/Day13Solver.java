package net.boomer41.aoc2024.day13;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day13Solver {

    public static long solve1(InputStream inputStream) throws InvalidFormatException {
        var machines = ArcadeMachine.parseList(inputStream);

        return machines.stream()
                .map(m -> m.getMinimumPrice(false))
                .mapToLong(o -> o.orElse(0L))
                .sum();
    }

    public static long solve2(InputStream inputStream) throws InvalidFormatException {
        var machines = ArcadeMachine.parseList(inputStream);

        var withSolution = machines.stream().filter(m -> m.getMinimumPrice(true).isPresent()).toList();
        var withoutSolution = machines.stream().filter(m -> m.getMinimumPrice(true).isEmpty()).toList();

        for (var m : withoutSolution) {
            m.getMinimumPrice(true);
        }

        return machines.stream()
                .map(m -> m.getMinimumPrice(true))
                .mapToLong(o -> o.orElse(0L))
                .sum();
    }

}
