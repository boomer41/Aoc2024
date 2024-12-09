package net.boomer41.aoc2024.tests.day9;

import net.boomer41.aoc2024.day9.DiskMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DiskMapTests {

    @Test
    public void testParsingAndExpandedRepresentation() {
        var map = DiskMap.fromCompressedMap("2333133121414131402");
        Assertions.assertEquals("00...111...2...333.44.5555.6666.777.888899", map.toExpandedRepresentation());
    }

    @Test
    public void testConsolidationFragmented() {
        var map = DiskMap.fromCompressedMap("2333133121414131402");
        map.consolidate(false);
        Assertions.assertEquals("0099811188827773336446555566..............", map.toExpandedRepresentation());
        Assertions.assertEquals(1928, map.calculateChecksum());
    }

    @Test
    public void testConsolidationNonFragmented() {
        var map = DiskMap.fromCompressedMap("2333133121414131402");
        map.consolidate(true);
        Assertions.assertEquals("00992111777.44.333....5555.6666.....8888..", map.toExpandedRepresentation());
        Assertions.assertEquals(2858, map.calculateChecksum());
    }

}
