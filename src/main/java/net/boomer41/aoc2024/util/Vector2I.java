package net.boomer41.aoc2024.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector2I {

    public static final Vector2I ZERO = new Vector2I(0, 0);

    private final int x;

    private final int y;

    public Vector2I add(Vector2I vector2) {
        return new Vector2I(x + vector2.x, y + vector2.y);
    }

    public Vector2I subtract(Vector2I vector2) {
        return new Vector2I(x - vector2.x, y - vector2.y);
    }

    public Vector2I reverse() {
        return new Vector2I(-x, -y);
    }

    public Vector2I multiply(Integer count) {
        return new Vector2I(x * count, y * count);
    }

}
