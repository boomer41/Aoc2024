package net.boomer41.aoc2024.tests.day4;

import net.boomer41.aoc2024.day4.Day4Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day4Tests {

    @Test
    public void testSolver1() throws Exception {
        try (var input = Day4Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day4/input.txt")) {
            var solution = Day4Solver.solve1(input);
            Assertions.assertEquals(2642, solution);
        }
    }

    @Test
    public void testSolver2() throws Exception {
        try (var input = Day4Solver.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day4/input.txt")) {
            var solution = Day4Solver.solve2(input);
            Assertions.assertEquals(1974, solution);
        }
    }

}
