package net.boomer41.aoc2024.util;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class StraightLine2L {

    private final Vector2L position;

    private final Vector2L direction;

    public Optional<Vector2L> intersectInternal(StraightLine2L other) {
        if (direction.equals(other.direction)) {
            return Optional.empty();
        }

        if (direction.getX() == 0) {
            // Calculation of the first point works only with this.direction.x != 0
            return other.intersectInternal(this);
        }

        return intersectNormal(other);
    }

    private Optional<Vector2L> intersectNormal(StraightLine2L other) {
        var x1 = position.getX();
        var x2 = x1 + direction.getX();
        var y1 = position.getY();
        var y2 = y1 + direction.getY();

        var x3 = other.position.getX();
        var x4 = x3 + other.direction.getX();
        var y3 = other.position.getY();
        var y4 = y3 + other.direction.getY();

        var denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (denominator == 0) {
            return Optional.empty();
        }

        var x = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4);
        x /= denominator;

        var xCount1 = (x - x1) / direction.getX();
        var p1 = position.add(direction.multiply(xCount1));
        var y = p1.getY();

        long secondCount;

        if (other.direction.getX() != 0) {
            secondCount = (x - x3) / other.direction.getX();
        } else {
            secondCount = (y - y3) / other.direction.getY();
        }

        var p2 = other.position.add(other.direction.multiply(secondCount));

        if (!p1.equals(p2)) {
            return Optional.empty();
        }

        return Optional.of(p2);
    }


    private static long toLong(double d) {
        d *= 1000.0;

        var l = (long) d;
        var remainder = l % 1000;

        if (remainder != 0) {
            if (remainder >= 500) {
                l += 1000 - remainder;
            } else {
                l -= remainder;
            }
        }

        return l / 1000L;
    }

}
