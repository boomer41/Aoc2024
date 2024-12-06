package net.boomer41.aoc2024.day6;

import java.util.HashSet;
import java.util.Set;

public class WalkedField {

    private final Set<GuardDirection> hitDirections;

    public WalkedField() {
        hitDirections = new HashSet<>();
    }

    public WalkedField(GuardDirection initialDirection) {
        this();

        markDirection(initialDirection);
    }

    public void markDirection(GuardDirection direction) {
        hitDirections.add(direction);
    }

    public boolean isDirectionMarked(GuardDirection direction) {
        return hitDirections.contains(direction);
    }

    public boolean hasAnyMarking() {
        return !hitDirections.isEmpty();
    }

}
