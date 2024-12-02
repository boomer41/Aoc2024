package net.boomer41.aoc2024.tests.day2;

import net.boomer41.aoc2024.day2.InvalidInputException;
import net.boomer41.aoc2024.day2.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ReportTests {

    @Test
    public void testValid() throws InvalidInputException {
        Assertions.assertTrue(new Report("1 2 3 4 5").isValid());
        Assertions.assertTrue(new Report("1 3 4 6 8").isValid());
        Assertions.assertTrue(new Report("8 6 4 3 1").isValid());
        Assertions.assertTrue(new Report("5 4 3 2 1").isValid());
    }

    @Test
    public void testInvalid() throws InvalidInputException {
        // Increasing but too wide gap
        Assertions.assertFalse(new Report("1 10 15").isValid());

        // Increasing but too little gap
        Assertions.assertFalse(new Report("1 10 10").isValid());

        // Decreasing but too wide gap
        Assertions.assertFalse(new Report("15 10 1").isValid());

        // Decreasing but too little gap
        Assertions.assertFalse(new Report("10 10 1").isValid());

        // Increasing first, but decreases after
        Assertions.assertFalse(new Report("1 2 3 4 3 2 1").isValid());

        // Decreasing first, but increasing after
        Assertions.assertFalse(new Report("4 3 2 1 2 3 4").isValid());
    }

    @Test
    public void testParseFailures() {
        Assertions.assertThrowsExactly(InvalidInputException.class, () -> {
            new Report("hans");
        });

        Assertions.assertThrowsExactly(InvalidInputException.class, () -> {
            new Report("1 2 3 4 5 dieter");
        });
    }

}
