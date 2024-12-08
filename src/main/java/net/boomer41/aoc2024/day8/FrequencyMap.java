package net.boomer41.aoc2024.day8;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.boomer41.aoc2024.util.Vector2I;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class FrequencyMap {

    @Getter
    private final char frequency;

    private final WorldBoundary worldBoundary;

    private final List<Vector2I> antennas;

    Set<Antinode> calculateAntinodes(int limit, boolean includeAntennasAsAntinodes) {
        var antinodes = new HashSet<Antinode>();

        for (var antenna1 = 0; antenna1 < antennas.size(); antenna1++) {
            for (var antenna2 = 0; antenna2 < antennas.size() / 2 + 1; antenna2++) {
                if (antenna1 == antenna2) {
                    continue;
                }

                calculateAntinodesBetweenAntennas(antinodes, antennas.get(antenna1), antennas.get(antenna2), limit, includeAntennasAsAntinodes);
            }
        }

        return antinodes;
    }

    private void calculateAntinodesBetweenAntennas(Set<Antinode> targetList, Vector2I a1, Vector2I a2, int limit, boolean includeAntennasAsAntinodes) {
        var difference = a2.subtract(a1);

        if (includeAntennasAsAntinodes) {
            a2 = a2.subtract(difference);
            a1 = a1.add(difference);
        }

        calculateSingleLine(targetList, a2, difference, limit);
        calculateSingleLine(targetList, a1, difference.reverse(), limit);
    }

    private void calculateSingleLine(Set<Antinode> targetList, Vector2I start, Vector2I direction, int limit) {
        for (var i = 0; i < limit; i++) {
            start = start.add(direction);

            if (!worldBoundary.contains(start)) {
                break;
            }

            targetList.add(new Antinode(start, frequency));
        }
    }

}
