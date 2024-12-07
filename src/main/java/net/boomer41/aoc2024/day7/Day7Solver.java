package net.boomer41.aoc2024.day7;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day7Solver {

    public static long solve1(InputStream inputStream) throws IOException {
        var formulas = FormulaList.parse(inputStream);
        var possibleFormulas = formulas.findAllPossible();

        return possibleFormulas.stream().mapToLong(FormulaInput::getExpectedResult).sum();
    }

    public static long solve2(InputStream inputStream) throws IOException {
        var formulas = FormulaList.parse(inputStream);
        var possibleFormulas = formulas.findAllPossibleIncludingConcatenation();

        return possibleFormulas.stream().mapToLong(FormulaInput::getExpectedResult).sum();
    }

}
