package net.boomer41.aoc2024.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector2L {

    public static final Vector2L ZERO = new Vector2L(0, 0);

    private final long x;

    private final long y;

    public Vector2L add(Vector2L vector2) {
        return new Vector2L(x + vector2.x, y + vector2.y);
    }

    public Vector2L subtract(Vector2L vector2) {
        return new Vector2L(x - vector2.x, y - vector2.y);
    }

    public Vector2L reverse() {
        return new Vector2L(-x, -y);
    }

    public Vector2L multiply(Long count) {
        return new Vector2L(x * count, y * count);
    }

}
