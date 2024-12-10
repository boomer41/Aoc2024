package net.boomer41.aoc2024.tests.day10;

import net.boomer41.aoc2024.day10.Day10Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day10Tests {

    @Test
    public void testSolver1SimpleFloydWarshall() throws Exception {
        try (var input = Day10Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day10/input_simple.txt")) {
            var result = Day10Solver.solve1(input, true);
            Assertions.assertEquals(36, result);
        }
    }

    @Test
    public void testSolver1ComplexFloydWarshall() throws Exception {
        try (var input = Day10Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day10/input_complex.txt")) {
            var result = Day10Solver.solve1(input, true);
            Assertions.assertEquals(468, result);
        }
    }

    @Test
    public void testSolver1SimpleDijkstra() throws Exception {
        try (var input = Day10Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day10/input_simple.txt")) {
            var result = Day10Solver.solve1(input, false);
            Assertions.assertEquals(36, result);
        }
    }

    @Test
    public void testSolver1ComplexDijkstra() throws Exception {
        try (var input = Day10Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day10/input_complex.txt")) {
            var result = Day10Solver.solve1(input, false);
            Assertions.assertEquals(468, result);
        }
    }

    @Test
    public void testSolver2Simple() throws Exception {
        try (var input = Day10Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day10/input_simple.txt")) {
            var result = Day10Solver.solve2(input);
            Assertions.assertEquals(81, result);
        }
    }

    @Test
    public void testSolver2Complex() throws Exception {
        try (var input = Day10Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day10/input_complex.txt")) {
            var result = Day10Solver.solve2(input);
            Assertions.assertEquals(966, result);
        }
    }
    
}
