package net.boomer41.aoc2024.tests.day7;

import net.boomer41.aoc2024.day7.Day7Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day7Tests {

    @Test
    public void testSolver1Simple() throws Exception {
        try (var input = Day7Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day7/input_simple.txt")) {
            var solution = Day7Solver.solve1(input);
            Assertions.assertEquals(3749, solution);
        }
    }

    @Test
    public void testSolver1Complex() throws Exception {
        try (var input = Day7Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day7/input_complex.txt")) {
            var solution = Day7Solver.solve1(input);
            Assertions.assertEquals(267566105056L, solution);
        }
    }

    @Test
    public void testSolver2Simple() throws Exception {
        try (var input = Day7Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day7/input_simple.txt")) {
            var solution = Day7Solver.solve2(input);
            Assertions.assertEquals(11387, solution);
        }
    }

    @Test
    public void testSolver2Complex() throws Exception {
        try (var input = Day7Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day7/input_complex.txt")) {
            var solution = Day7Solver.solve2(input);
            Assertions.assertEquals(116094961956019L, solution);
        }
    }

}
