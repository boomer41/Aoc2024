package net.boomer41.aoc2024.tests.day8;

import net.boomer41.aoc2024.day8.Day8Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day8Tests {

    @Test
    public void testSolver1Simple() throws Exception {
        try (var input = Day8Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day8/input_simple.txt")) {
            var solution = Day8Solver.solve1(input);
            Assertions.assertEquals(14, solution);
        }
    }

    @Test
    public void testSolver1Complex() throws Exception {
        try (var input = Day8Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day8/input_complex.txt")) {
            var solution = Day8Solver.solve1(input);
            Assertions.assertEquals(295, solution);
        }
    }

    @Test
    public void testSolver2Simple() throws Exception {
        try (var input = Day8Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day8/input_simple.txt")) {
            var solution = Day8Solver.solve2(input);
            Assertions.assertEquals(34, solution);
        }
    }

    @Test
    public void testSolver2Complex() throws Exception {
        try (var input = Day8Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day8/input_complex.txt")) {
            var solution = Day8Solver.solve2(input);
            Assertions.assertEquals(1034, solution);
        }
    }

}
