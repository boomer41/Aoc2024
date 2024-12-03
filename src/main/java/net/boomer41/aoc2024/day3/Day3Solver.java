package net.boomer41.aoc2024.day3;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day3Solver {

    public static long solve1(InputStream inputStream) throws IOException {
        var foundInstructions = MultiplyInstruction.parseValidInstructions(inputStream, false);

        return foundInstructions.stream()
                .mapToInt(MultiplyInstruction::getResult)
                .sum();
    }

    public static long solve2(InputStream inputStream) throws IOException {
        var foundInstructions = MultiplyInstruction.parseValidInstructions(inputStream, true);

        return foundInstructions.stream()
                .mapToInt(MultiplyInstruction::getResult)
                .sum();
    }

}
