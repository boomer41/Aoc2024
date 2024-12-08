package net.boomer41.aoc2024.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Vector2I {

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

}
