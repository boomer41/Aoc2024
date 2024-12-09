package net.boomer41.aoc2024.tests.day9;

import net.boomer41.aoc2024.day9.DiskMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Day9Tests {

    @Test
    public void testSolver1Complex() throws Exception {
        try (var input = DiskMap.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day9/input_complex.txt")) {
            var string = new String(Objects.requireNonNull(input).readAllBytes(), StandardCharsets.US_ASCII);

            var map = DiskMap.fromCompressedMap(string);
            map.consolidate(false);
            Assertions.assertEquals(6331212425418L, map.calculateChecksum());
        }
    }

    @Test
    public void testSolver2Complex() throws Exception {
        try (var input = DiskMap.class.getResourceAsStream("/net/boomer41/aoc2024/tests/day9/input_complex.txt")) {
            var string = new String(Objects.requireNonNull(input).readAllBytes(), StandardCharsets.US_ASCII);

            var map = DiskMap.fromCompressedMap(string);
            map.consolidate(true);
            Assertions.assertEquals(6363268339304L, map.calculateChecksum());
        }
    }
    
}
