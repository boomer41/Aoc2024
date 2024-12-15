package net.boomer41.aoc2024.tests.day15;

import net.boomer41.aoc2024.day15.Day15Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day15Tests {

    @Test
    public void testSolver1Simple() throws Exception {
        try (var input = Day15Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day15/input_simple.txt")) {
            var solution = Day15Solver.solve1(input, this::visualize);
            Assertions.assertEquals(2028, solution);
        }
    }

    @Test
    public void testSolver1Medium() throws Exception {
        try (var input = Day15Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day15/input_medium.txt")) {
            var solution = Day15Solver.solve1(input, this::visualize);
            Assertions.assertEquals(10092, solution);
        }
    }

    @Test
    public void testSolver1Complex() throws Exception {
        try (var input = Day15Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day15/input_complex.txt")) {
            var solution = Day15Solver.solve1(input, null);
            Assertions.assertEquals(1492518, solution);
        }
    }

    @Test
    public void testSolver2Simple() throws Exception {
        try (var input = Day15Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day15/input_simple2.txt")) {
            var solution = Day15Solver.solve2(input, this::visualize);
            Assertions.assertEquals(618, solution);
        }
    }

    @Test
    public void testSolver2Medium() throws Exception {
        try (var input = Day15Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day15/input_medium.txt")) {
            var solution = Day15Solver.solve2(input, this::visualize);
            Assertions.assertEquals(9021, solution);
        }
    }

    @Test
    public void testSolver2Complex() throws Exception {
        try (var input = Day15Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day15/input_complex.txt")) {
            var solution = Day15Solver.solve2(input, null);
            Assertions.assertEquals(1512860, solution);
        }
    }

    private void visualize(String headline, String map) {
        System.out.println(headline + ":\n" + map);
        System.out.println();
    }

}
