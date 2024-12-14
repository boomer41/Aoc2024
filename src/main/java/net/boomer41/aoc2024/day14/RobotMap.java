package net.boomer41.aoc2024.day14;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.boomer41.aoc2024.util.Vector2I;

import java.io.InputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RobotMap {

    private static final Vector2I DIR_UP = new Vector2I(0, -1);
    private static final Vector2I DIR_DOWN = new Vector2I(0, 1);
    private static final Vector2I DIR_LEFT = new Vector2I(-1, 0);
    private static final Vector2I DIR_RIGHT = new Vector2I(1, 0);

    private static final Vector2I[] DIRECTIONS = new Vector2I[] {
            DIR_UP, DIR_DOWN, DIR_LEFT, DIR_RIGHT
    };

    @Getter
    private final Vector2I bounds;

    private final List<Robot> robots = new ArrayList<>();

    @Getter
    private int ticks;

    public static RobotMap fromInput(InputStream inputStream, Vector2I bounds) {
        var scanner = new Scanner(inputStream);
        var map = new RobotMap(bounds);

        while (scanner.hasNextLine()) {
            map.robots.add(Robot.fromString(map, scanner.nextLine()));
        }

        return map;
    }

    public void tickAll(int count) {
        robots.forEach(robot -> robot.tick(count));
        ticks += count;
    }

    public long getSafetyFactor() {
        var quadrands = new int[4];

        for (var robot : robots) {
            var pos = robot.getPosition();
            var posX = pos.getX();
            var posY = pos.getY();

            var halfX = bounds.getX() / 2;
            var halfY = bounds.getY() / 2;

            if (posX < halfX && posY < halfY) {
                quadrands[0]++;
            } else if (posX < halfX && posY > halfY) {
                quadrands[1]++;
            } else if (posX > halfX && posY < halfY) {
                quadrands[2]++;
            } else if (posX > halfX && posY > halfY) {
                quadrands[3]++;
            }
        }

        return Arrays.stream(quadrands).reduce(1, (a, b) -> a * b);
    }

    public boolean containsTree() {
        var locations = robots.stream().map(Robot::getPosition).collect(Collectors.toSet());

        // Crude, but does the job
        return containsCluster(locations, 100);
    }

    private static boolean containsCluster(Set<Vector2I> locations, @SuppressWarnings("SameParameterValue") int expectedSize) {
        var nonVisited = new HashSet<>(locations);

        while (true) {
            var next = nonVisited.stream().findFirst();

            if (next.isEmpty()) {
                break;
            }

            var clusterSize = 0;

            var queue = new LinkedList<Vector2I>();
            queue.add(next.get());

            while (!queue.isEmpty()) {
                var current = queue.remove();
                clusterSize++;
                nonVisited.remove(current);

                for (var dir : DIRECTIONS) {
                    var pos = current.add(dir);

                    if (nonVisited.contains(pos)) {
                        queue.add(pos);
                    }
                }
            }

            if (clusterSize < expectedSize) {
                continue;
            }

            return true;
        }

        return false;
    }

}
