package net.boomer41.aoc2024.tests.day2;

import net.boomer41.aoc2024.day2.Day2Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day2Tests {

    @Test
    public void testSolver1() throws Exception {
        try (var input = Day2Tests.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day2/input.txt")) {
            var solution = Day2Solver.solve1(input);
            Assertions.assertEquals(559, solution);
        }
    }

    @Test
    public void testSolver2() throws Exception {
        try (var input = Day2Tests.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day2/input.txt")) {
            var solution = Day2Solver.solve2(input);
            Assertions.assertEquals(601, solution);
        }
    }

}
