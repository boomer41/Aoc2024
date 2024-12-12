package net.boomer41.aoc2024.day12;

import lombok.Data;
import net.boomer41.aoc2024.util.Vector2I;

@Data
class SeenSide {

    private final Vector2I position;

    private final Vector2I walkDirection;

    private final Vector2I lookDirection;

}
