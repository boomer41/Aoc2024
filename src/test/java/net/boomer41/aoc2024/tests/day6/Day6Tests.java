package net.boomer41.aoc2024.tests.day6;

import net.boomer41.aoc2024.day6.Day6Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day6Tests {

    @Test
    public void testSolver1() throws Exception {
        try (var input = Day6Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day6/input.txt")) {
            var solution = Day6Solver.solve1(input);
            Assertions.assertEquals(5312, solution);
        }
    }

    @Test
    public void testSolver2() throws Exception {
        try (var input = Day6Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day6/input.txt")) {
            var solution = Day6Solver.solve2(input);
            Assertions.assertEquals(1748, solution);
        }
    }

}
