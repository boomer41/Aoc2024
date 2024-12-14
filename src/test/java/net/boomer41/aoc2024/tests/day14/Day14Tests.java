package net.boomer41.aoc2024.tests.day14;

import net.boomer41.aoc2024.day14.Day14Solver;
import net.boomer41.aoc2024.util.Vector2I;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class Day14Tests {

    @Test
    public void testSolver1Simple() throws Exception {
        try (var input = Day14Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day14/input_simple.txt")) {
            var solution = Day14Solver.solve1(input, new Vector2I(11, 7));
            Assertions.assertEquals(12L, solution);
        }
    }

    @Test
    public void testSolver1Complex() throws Exception {
        try (var input = Day14Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day14/input_complex.txt")) {
            var solution = Day14Solver.solve1(input, new Vector2I(101, 103));
            Assertions.assertEquals(231782040L, solution);
        }
    }

    @Test
    public void testSolver2Complex() throws Exception {
        try (var input = Day14Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day14/input_complex.txt")) {
            var solution = Day14Solver.solve2(input, new Vector2I(101, 103));
            Assertions.assertEquals(Optional.of(6475L), solution);
        }
    }

}
