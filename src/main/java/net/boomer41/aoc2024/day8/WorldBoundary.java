package net.boomer41.aoc2024.day8;

import lombok.Data;
import net.boomer41.aoc2024.util.Vector2I;

@Data
public class WorldBoundary {

    public final int width;

    public final int height;

    public boolean contains(Vector2I v) {
        return v.getX() >= 0 && v.getX() < width && v.getY() >= 0 && v.getY() < height;
    }

}
