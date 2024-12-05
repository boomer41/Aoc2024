package net.boomer41.aoc2024.tests.day5;

import net.boomer41.aoc2024.day5.Day5Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day5Tests {

    @Test
    public void testSolver1() throws Exception {
        try (var input = Day5Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day5/input.txt")) {
            var solution = Day5Solver.solve1(input);
            Assertions.assertEquals(6384, solution);
        }
    }

    @Test
    public void testSolver2() throws Exception {
        try (var input = Day5Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day5/input.txt")) {
            var solution = Day5Solver.solve2(input);
            Assertions.assertEquals(5353, solution);
        }
    }

}
