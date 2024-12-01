package net.boomer41.aoc2024.tests.day1;

import net.boomer41.aoc2024.day1.Day1Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day1Tests {

    @Test
    public void testSolver1() throws Exception {
        try (var input = Day1Tests.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day1/input1.txt")) {
            var solution = Day1Solver.solve1(input);
            Assertions.assertEquals(765748, solution);
        }
    }

    @Test
    public void testSolver2() throws Exception {
        try (var input = Day1Tests.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day1/input2.txt")) {
            var solution = Day1Solver.solve2(input);
            Assertions.assertEquals(27732508, solution);
        }
    }

}
