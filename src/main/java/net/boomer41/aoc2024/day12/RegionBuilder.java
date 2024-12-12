package net.boomer41.aoc2024.day12;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.boomer41.aoc2024.util.Vector2I;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RegionBuilder {

    @Getter
    private final char name;

    private final Set<Vector2I> locations = new HashSet<>();

    public void addLocation(Vector2I location) {
        locations.add(location);
    }

    public void mergeWith(RegionBuilder otherRegionBuilder) {
        if (name != otherRegionBuilder.name) {
            throw new IllegalArgumentException("Region name mismatch");
        }

        otherRegionBuilder.locations.forEach(this::addLocation);
    }

    public Region build() {
        // Build step can be used to cache something after all locations have been added
        return new Region(name, locations);
    }

}
