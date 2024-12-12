package net.boomer41.aoc2024.day12;

import lombok.Getter;
import net.boomer41.aoc2024.util.Vector2I;

import java.util.*;

public class Region {

    private static final Vector2I DIRECTION_LEFT = new Vector2I(-1, 0);
    private static final Vector2I DIRECTION_RIGHT = new Vector2I(1, 0);
    private static final Vector2I DIRECTION_UP = new Vector2I(0, -1);
    private static final Vector2I DIRECTION_DOWN = new Vector2I(0, 1);

    @Getter
    private final char name;

    private final Set<Vector2I> locations;

    public Region(char name, Set<Vector2I> locations) {
        this.name = name;
        this.locations = new HashSet<>(locations);
    }

    public long getArea() {
        return locations.size();
    }

    public long getPerimeter() {
        var perimeter = 0L;

        for (Vector2I location : locations) {
            perimeter += !isContained(location.add(DIRECTION_LEFT)) ? 1 : 0;
            perimeter += !isContained(location.add(DIRECTION_RIGHT)) ? 1 : 0;
            perimeter += !isContained(location.add(DIRECTION_DOWN)) ? 1 : 0;
            perimeter += !isContained(location.add(DIRECTION_UP)) ? 1 : 0;
        }

        return perimeter;
    }

    public long getSides() {
        // Set contains the location, which is furthest in the given direction
        var seenSides = new HashSet<SeenSide>();

        // Map contains a lookup from any side member to its root part
        var sideCache = new HashMap<SeenSide, SeenSide>();

        var orderedLocations = new ArrayList<>(locations);
        orderedLocations.sort(
                Comparator.comparingInt(Vector2I::getY)
                        .thenComparingInt(Vector2I::getX)
        );

        for (Vector2I location : orderedLocations) {
            walkSide(location, DIRECTION_UP, DIRECTION_LEFT, seenSides, sideCache);
            walkSide(location, DIRECTION_UP, DIRECTION_RIGHT, seenSides, sideCache);
            walkSide(location, DIRECTION_LEFT, DIRECTION_UP, seenSides, sideCache);
            walkSide(location, DIRECTION_LEFT, DIRECTION_DOWN, seenSides, sideCache);
        }

        return seenSides.size();
    }

    private void walkSide(Vector2I location, Vector2I walkDirection, Vector2I lookDirection, Set<SeenSide> seenSides, Map<SeenSide, SeenSide> sideCache) {
        var lookLocation = location.add(lookDirection);

        // No edge there
        if (isContained(lookLocation)) {
            return;
        }

        var cacheLocation = location.add(walkDirection);
        var cachedResult = sideCache.get(new SeenSide(cacheLocation, walkDirection, lookDirection));

        if (cachedResult != null) {
            sideCache.put(new SeenSide(location, walkDirection, lookDirection), cachedResult);
            return;
        }

        // We have not found any cache entry, this must be the first one
        var side = new SeenSide(location, walkDirection, lookDirection);
        seenSides.add(side);
        sideCache.put(side, side);
    }

    public boolean isContained(Vector2I location) {
        return locations.contains(location);
    }

}
