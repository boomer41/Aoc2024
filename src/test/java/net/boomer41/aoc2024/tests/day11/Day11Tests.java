package net.boomer41.aoc2024.tests.day11;

import net.boomer41.aoc2024.day11.Day11Solver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day11Tests {

    @Test
    public void testSolver1Simple() {
        var solution = Day11Solver.solve1("125 17");
        Assertions.assertEquals(55312, solution);
    }

    @Test
    public void testSolver1Complex() {
        var solution = Day11Solver.solve1("3279 998884 1832781 517 8 18864 28 0");
        Assertions.assertEquals(218956, solution);
    }

    @Test
    public void testSolver2Complex() {
        var solution = Day11Solver.solve2("3279 998884 1832781 517 8 18864 28 0");
        Assertions.assertEquals(259593838049805L, solution);
    }


}
