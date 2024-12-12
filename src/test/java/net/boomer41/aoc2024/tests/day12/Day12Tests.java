package net.boomer41.aoc2024.tests.day12;

import net.boomer41.aoc2024.day12.Day12Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day12Tests {

    @Test
    public void testSolver1Simple() throws Exception {
        try (var input = Day12Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day12/input_simple.txt")) {
            var solution = Day12Solver.solve1(input);
            Assertions.assertEquals(1930, solution);
        }
    }

    @Test
    public void testSolver1Complex() throws Exception {
        try (var input = Day12Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day12/input_complex.txt")) {
            var solution = Day12Solver.solve1(input);
            Assertions.assertEquals(1375574, solution);
        }
    }

    @Test
    public void testSolver2Simple() throws Exception {
        try (var input = Day12Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day12/input_simple.txt")) {
            var solution = Day12Solver.solve2(input);
            Assertions.assertEquals(1206, solution);
        }
    }

    @Test
    public void testSolver2Complex() throws Exception {
        try (var input = Day12Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day12/input_complex.txt")) {
            var solution = Day12Solver.solve2(input);
            Assertions.assertEquals(830566, solution);
        }
    }
    
}
