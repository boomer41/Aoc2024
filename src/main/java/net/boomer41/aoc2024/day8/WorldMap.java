package net.boomer41.aoc2024.day8;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.boomer41.aoc2024.util.Vector2I;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WorldMap {

    @Getter
    private final WorldBoundary boundary;

    private final Map<Character, FrequencyMap> mapsByFrequency;

    public static WorldMap parse(InputStream inputStream) throws IOException {
        var string = new String(inputStream.readAllBytes(), StandardCharsets.US_ASCII);

        var lines = string.split("\n");
        var width = -1;

        var frequencies = new HashMap<Character, List<Vector2I>>();

        for (var y = 0; y < lines.length; y++) {
            var columns = lines[y].toCharArray();

            if (y == 0) {
                width = columns.length;
            } else if (width != columns.length) {
                throw new NumberFormatException("Map width is not equal across all rows");
            }

            for (var x = 0; x < columns.length; x++) {
                var frequency = columns[x];

                if (frequency == '.') {
                    continue;
                }

                var frequencyMap = frequencies.computeIfAbsent(frequency, _ -> new ArrayList<>());
                frequencies.put(frequency, frequencyMap);

                var antenna = new Vector2I(x, y);
                frequencyMap.add(antenna);
            }
        }

        var boundary = new WorldBoundary(width, lines.length);

        var frequencyMaps = frequencies.entrySet()
                .stream()
                .map(f -> new FrequencyMap(f.getKey(), boundary, f.getValue()))
                .collect(Collectors.toMap(FrequencyMap::getFrequency, v -> v));

        return new WorldMap(boundary, frequencyMaps);
    }

    private List<Antinode> calculateAntinodes(int limit, boolean includeAntennasAsAntinodes) {
        return mapsByFrequency.values()
                .stream()
                .flatMap(m -> m.calculateAntinodes(limit, includeAntennasAsAntinodes).stream())
                .toList();
    }

    public List<Antinode> calculateAntinodes() {
        return calculateAntinodes(1, false);
    }

    public List<Antinode> calculateAntinodesWithNoLimit() {
        return calculateAntinodes(Integer.MAX_VALUE, true);
    }

}
