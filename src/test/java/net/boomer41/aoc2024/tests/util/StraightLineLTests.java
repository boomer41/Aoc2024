package net.boomer41.aoc2024.tests.util;

import net.boomer41.aoc2024.util.StraightLine2L;
import net.boomer41.aoc2024.util.Vector2L;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

public class StraightLineLTests {

    @ParameterizedTest
    @ValueSource(longs = {0, 1_000_000_000_000L})
    public void testIntersectionInteger(long offset) {
        var l1 = new StraightLine2L(new Vector2L(4L + offset, 6L + offset), new Vector2L(1L, 2L));
        var l2 = new StraightLine2L(new Vector2L(offset, 6L + offset), new Vector2L(3L, -2L));

        var p1 = l1.intersectInternal(l2);
        var p2 = l2.intersectInternal(l1);
        var expected = Optional.of(new Vector2L(3L + offset, 4L + offset));

        Assertions.assertEquals(expected, p1);
        Assertions.assertEquals(expected, p2);
        Assertions.assertEquals(p1, p2);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1_000_000_000_000L})
    public void testIntersectionNotExactlyInteger(long offset) {
        var l1 = new StraightLine2L(new Vector2L(4L + offset, 6L + offset), new Vector2L(1L, 2L));
        var l2 = new StraightLine2L(new Vector2L(1L + offset, 7L + offset), new Vector2L(3L, -2L));

        var p1 = l1.intersectInternal(l2);
        var p2 = l2.intersectInternal(l1);
        var expected = Optional.empty();

        Assertions.assertEquals(expected, p1);
        Assertions.assertEquals(expected, p2);
        Assertions.assertEquals(p1, p2);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1_000_000_000_000L})
    public void testIntersectionParallel(long offset) {
        var d = new Vector2L(4L, 6L);
        var l1 = new StraightLine2L(new Vector2L(4L + offset, 6L + offset), d);
        var l2 = new StraightLine2L(new Vector2L(1L + offset, 7L + offset), d);

        var p1 = l1.intersectInternal(l2);
        var p2 = l2.intersectInternal(l1);
        var expected = Optional.empty();

        Assertions.assertEquals(expected, p1);
        Assertions.assertEquals(expected, p2);
        Assertions.assertEquals(p1, p2);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1_000_000_000_000L})
    public void testIntersectionParallelSamePoint(long offset) {
        var d = new Vector2L(4L, 6L);
        var l1 = new StraightLine2L(new Vector2L(4L + offset, 6L + offset), d);
        var l2 = new StraightLine2L(new Vector2L(4L + offset, 6L + offset), d);

        var p1 = l1.intersectInternal(l2);
        var p2 = l2.intersectInternal(l1);
        var expected = Optional.empty();

        Assertions.assertEquals(expected, p1);
        Assertions.assertEquals(expected, p2);
        Assertions.assertEquals(p1, p2);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1_000_000_000_000L})
    public void testIntersectionParallelDownwards(long offset) {
        var d = new Vector2L(0, 6L);
        var l1 = new StraightLine2L(new Vector2L(4L + offset, 6L + offset), d);
        var l2 = new StraightLine2L(new Vector2L(1L + offset, 7L + offset), d);

        var p1 = l1.intersectInternal(l2);
        var p2 = l2.intersectInternal(l1);
        var expected = Optional.empty();

        Assertions.assertEquals(expected, p1);
        Assertions.assertEquals(expected, p2);
        Assertions.assertEquals(p1, p2);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1_000_000_000_000L})
    public void testIntersectionDownwards(long offset) {
        var l1 = new StraightLine2L(new Vector2L(1L + offset, 7L + offset), new Vector2L(3L, -2L));
        var l2 = new StraightLine2L(new Vector2L(4L + offset, 2L + offset), new Vector2L(0L, 1L));

        var p1 = l1.intersectInternal(l2);
        var p2 = l2.intersectInternal(l1);
        var expected = Optional.of(new Vector2L(4L + offset, 5L + offset));

        Assertions.assertEquals(expected, p1);
        Assertions.assertEquals(expected, p2);
        Assertions.assertEquals(p1, p2);
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1_000_000_000_000L})
    public void testIntersectionOrder(long offset) {
        var s1 = new Vector2L(1L + offset, 1L + offset);
        var s2 = new Vector2L(4L + offset, 6L + offset);

        var d1 = new Vector2L(-1L, 3L);
        var d2 = new Vector2L(4L, 2L);

        var l11 = new StraightLine2L(s1, d1);
        var l12 = new StraightLine2L(s2, d2);
        var p11 = l11.intersectInternal(l12);
        var p12 = l12.intersectInternal(l11);
        var e1 = Optional.of(new Vector2L(offset, 4L + offset));
        Assertions.assertEquals(e1, p11);
        Assertions.assertEquals(e1, p12);

        var l21 = new StraightLine2L(s1, d2);
        var l22 = new StraightLine2L(s2, d1);
        var p21 = l21.intersectInternal(l22);
        var p22 = l22.intersectInternal(l21);
        var e2 = Optional.of(new Vector2L(5L + offset, 3L + offset));
        Assertions.assertEquals(e2, p21);
        Assertions.assertEquals(e2, p22);
    }

}
