package net.boomer41.aoc2024.day6;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Map {

    @Getter
    private final int mapColumns;

    private final MapObject[] map;

    private final MapGuard guard;

    private final WalkedField[] walkedFields;

    private Map(int mapColumns, MapObject[] map, MapGuard guard) {
        this.mapColumns = mapColumns;
        this.map = map;
        this.guard = guard;

        this.walkedFields = Arrays.stream(this.map)
                .map(g -> g instanceof MapGuard ? new WalkedField(((MapGuard) g).getDirection()) : new WalkedField())
                .toArray(WalkedField[]::new);
    }

    public static Map parse(InputStream inputStream) throws IOException {
        var bytes = inputStream.readAllBytes();
        var string = new String(bytes, StandardCharsets.US_ASCII);

        var lines = string.split("\n");
        var columns = lines[0].length();

        var objects = new MapObject[columns * lines.length];
        MapGuard guard = null;

        for (var y = 0; y < lines.length; y++) {
            var chars = lines[y].toCharArray();

            for (var x = 0; x < chars.length; x++) {
                switch (chars[x]) {
                    case '.':
                        break;
                    case '#':
                        objects[y * columns + x] = new MapObstacle();
                        break;
                    case '^':
                        objects[y * columns + x] = guard = new MapGuard(y * columns + x, GuardDirection.UP);
                        break;
                    default:
                        throw new UnsupportedOperationException("Unsupported character: " + chars[x]);
                }
            }
        }

        if (guard == null) {
            throw new UnsupportedOperationException("No guard found");
        }

        return new Map(columns, objects, guard);
    }

    public boolean walkUntilGuardExistsOrHitsLoop() {
        while (true) {
            var oldPosition = guard.getCurrentPosition();
            walkedFields[oldPosition].markDirection(guard.getDirection());

            var newPosition = guard.walk(this);

            if (newPosition.isEmpty()) {
                break;
            }

            var targetField = walkedFields[newPosition.get()];

            if (targetField.isDirectionMarked(guard.getDirection())) {
                return true;
            }

            map[oldPosition] = null;
            map[newPosition.get()] = guard;
            targetField.markDirection(guard.getDirection());
            guard.setCurrentPosition(newPosition.get());
        }

        return false;
    }

    public long countNumberOfUniqueWalkedFields() {
        return Arrays.stream(this.walkedFields)
                .filter(WalkedField::hasAnyMarking)
                .count();
    }

    public boolean isValidPosition(int x, int y) {
        return x >= 0 && y >= 0 && x < mapColumns && y < (map.length / mapColumns);
    }

    public boolean isObstacle(int x, int y) {
        var mapObject = map[y * mapColumns + x];

        return mapObject != null && mapObject.isObstacle();
    }

    public long findMapCountWithLoops() {
        var withLoop = 0;

        for (var position = 0; position < map.length; position++) {
            // Maybe something is already there?
            if (map[position] != null) {
                continue;
            }

            var newMap = this.duplicate();
            newMap.putObstacle(position);

            if (!newMap.walkUntilGuardExistsOrHitsLoop()) {
                continue;
            }

            withLoop++;
        }

        return withLoop;
    }

    private void putObstacle(int position) {
        map[position] = new MapObstacle();
    }

    private Map duplicate() {
        var newMap = Arrays.stream(map).map(o -> o != null ? o.duplicate() : null).toArray(MapObject[]::new);
        var newGuard = Arrays.stream(newMap)
                .filter(o -> o instanceof MapGuard)
                .map(o -> (MapGuard) o)
                .findFirst();

        if (newGuard.isEmpty()) {
            throw new IllegalStateException();
        }

        return new Map(mapColumns, newMap, newGuard.get());
    }

}
