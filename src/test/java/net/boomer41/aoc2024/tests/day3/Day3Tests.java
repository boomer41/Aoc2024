package net.boomer41.aoc2024.tests.day3;

import net.boomer41.aoc2024.day3.Day3Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day3Tests {

    @Test
    public void testSolver1() throws Exception {
        try (var input = Day3Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day3/input.txt")) {
            var solution = Day3Solver.solve1(input);
            Assertions.assertEquals(163931492, solution);
        }
    }

    @Test
    public void testSolver2() throws Exception {
        try (var input = Day3Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day3/input.txt")) {
            var solution = Day3Solver.solve2(input);
            Assertions.assertEquals(76911921, solution);
        }
    }

}
