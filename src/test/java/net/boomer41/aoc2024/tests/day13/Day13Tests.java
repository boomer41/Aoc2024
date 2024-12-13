package net.boomer41.aoc2024.tests.day13;

import net.boomer41.aoc2024.day13.ArcadeMachine;
import net.boomer41.aoc2024.day13.Day13Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day13Tests {

    @Test
    public void testSolver1Simple() throws Exception {
        try (var input = Day13Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day13/input_simple.txt")) {
            var solution = Day13Solver.solve1(input);
            Assertions.assertEquals(480, solution);
        }
    }

    @Test
    public void testSolver1Complex() throws Exception {
        try (var input = Day13Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day13/input_complex.txt")) {
            var solution = Day13Solver.solve1(input);
            Assertions.assertEquals(37680, solution);
        }
    }

    @Test
    public void testSolver2Complex() throws Exception {
        try (var input = Day13Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day13/input_complex.txt")) {
            var solution = Day13Solver.solve2(input);
            Assertions.assertEquals(87550094242995L, solution);
        }
    }

}
