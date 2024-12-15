package net.boomer41.aoc2024.day15;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.boomer41.aoc2024.util.Vector2I;

import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Warehouse {

    private static final Vector2I DIR_UP = new Vector2I(0, -1);
    private static final Vector2I DIR_DOWN = new Vector2I(0, 1);
    private static final Vector2I DIR_LEFT = new Vector2I(-1, 0);
    private static final Vector2I DIR_RIGHT = new Vector2I(1, 0);

    private final int scaleX;

    private final Set<Vector2I> boxes = new HashSet<>();

    private final Set<Vector2I> walls = new HashSet<>();

    private final Queue<Vector2I> robotMovements = new LinkedList<>();

    private Vector2I robotPosition;

    @Setter
    private WarehouseVisualizationCallback visualizationCallback;

    public static Warehouse fromInput(InputStream inputStream, boolean scaleUp) {
        var scaleX = scaleUp ? 2 : 1;
        var scanner = new Scanner(inputStream);
        var warehouse = new Warehouse(scaleX);

        // Read map
        {
            // Skip first line
            var firstLine = scanner.nextLine();
            for (var x = 0; x < firstLine.length(); x++) {
                if (firstLine.charAt(x) != '#') {
                    throw new IllegalArgumentException("Invalid first line " + firstLine);
                }

                for (var i = 0; i < scaleX; i++) {
                    warehouse.walls.add(new Vector2I(scaleX * x + i, 0));
                }
            }

            for (var y = 1;; y++) {
                var line = scanner.nextLine();

                if (line.equals(firstLine)) {
                    for (var x = 0; x < line.length() * scaleX; x++) {
                        warehouse.walls.add(new Vector2I(x, y));
                    }

                    break;
                }

                for (var x = 0; x < line.length(); x++) {
                    var realX = x * scaleX;
                    var character = line.charAt(x);

                    if (character == '#') {
                        for (var i = 0; i < scaleX; i++) {
                            warehouse.walls.add(new Vector2I(realX + i, y));
                        }

                        continue;
                    }

                    if (character == '.') {
                        continue;
                    }

                    if (character == '@') {
                        if (warehouse.robotPosition != null) {
                            throw new IllegalArgumentException("Multiple robot positions");
                        }

                        warehouse.robotPosition = new Vector2I(realX, y);
                        continue;
                    }

                    if (character == 'O') {
                        warehouse.boxes.add(new Vector2I(realX, y));
                        continue;
                    }

                    throw new IllegalArgumentException("Invalid symbol " + character);
                }
            }

            if (warehouse.robotPosition == null) {
                throw new IllegalArgumentException("No robot position found");
            }
        }

        var line = scanner.nextLine();

        if (!line.isEmpty()) {
            throw new IllegalArgumentException("Expected empty line, got: " + line);
        }

        // Read instructions
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();

            for (var character : line.toCharArray()) {
                warehouse.robotMovements.add(switch (character) {
                    case '^' -> DIR_UP;
                    case 'v' -> DIR_DOWN;
                    case '<' -> DIR_LEFT;
                    case '>' -> DIR_RIGHT;
                    default -> throw new IllegalArgumentException("Unexpected movement: " + character);
                });
            }
        }

        return warehouse;
    }

    public void executeMovements() {
        if (visualizationCallback != null) {
            visualizationCallback.callback("Initial state", visualize());
        }

        while (!robotMovements.isEmpty()) {
            var move = robotMovements.poll();

            executeMove(move);

            if (visualizationCallback != null) {
                var headline = getMoveHeadline(move);
                visualizationCallback.callback(headline, visualize());
            }
        }

    }

    private static String getMoveHeadline(Vector2I move) {
        if (move == DIR_UP) {
            return ("Move ^");
        } else if (move == DIR_DOWN) {
            return ("Move v");
        } else if (move == DIR_LEFT) {
            return ("Move <");
        } else if (move == DIR_RIGHT) {
            return ("Move >");
        } else {
            throw new IllegalArgumentException("Invalid movement: " + move);
        }
    }

    private void executeMove(Vector2I move) {
        var boxesToRemove = new HashSet<Vector2I>();
        var boxesToAdd = new HashSet<Vector2I>();

        var boxesToMove = new HashSet<Vector2I>();

        var newRobotPosition = robotPosition.add(move);

        if (isWall(newRobotPosition)) {
            return;
        }

        // Robot handling is special, as it's only 1 wide
        {
            var box = getBoxCoordinates(newRobotPosition);

            box.ifPresent(boxesToMove::add);
        }

        while (true) {
            var currentBox = boxesToMove.stream().findFirst();

            if (currentBox.isEmpty()) {
                break;
            }

            boxesToMove.remove(currentBox.get());

            // No box
            if (getBoxCoordinates(currentBox.get()).isEmpty()) {
                continue;
            }

            var targetPositionDirect = currentBox.get().add(move);

            // Check for any walls...
            for (var i = 0; i < scaleX; i++) {
                if (isWall(targetPositionDirect.add(DIR_RIGHT.multiply(i)))) {
                    return;
                }
            }

            boxesToRemove.add(currentBox.get());
            boxesToAdd.add(targetPositionDirect);

            // Simple case: Left
            if (move.equals(DIR_LEFT)) {
                var realNextBoxToMove = getBoxCoordinates(targetPositionDirect);
                realNextBoxToMove.ifPresent(boxesToMove::add);

                continue;
            }

            // Simple case: Right
            if (move.equals(DIR_RIGHT)) {
                var realNextBoxToMove = getBoxCoordinates(targetPositionDirect.add(move.multiply(scaleX - 1)));
                realNextBoxToMove.ifPresent(boxesToMove::add);

                continue;
            }

            // Up or down, multiple boxes possible
            var targetPositionRight = targetPositionDirect.add(DIR_RIGHT.multiply(scaleX - 1));

            // Box aligned above -> No second box possible
            var boxLeft = getBoxCoordinates(targetPositionDirect);
            boxLeft.ifPresent(boxesToMove::add);

            var boxRight = getBoxCoordinates(targetPositionRight);

            if (boxRight.isPresent() && !boxRight.equals(boxLeft)) {
                boxesToMove.add(targetPositionRight);
            }
        }

        boxes.removeAll(boxesToRemove);
        boxes.addAll(boxesToAdd);
        robotPosition = newRobotPosition;
    }

    private Optional<Vector2I> getBoxCoordinates(Vector2I pos) {
        for (var i = 0; i < scaleX; i++) {
            var p = pos.add(DIR_LEFT.multiply(i));

            if (boxes.contains(p)) {
                return Optional.of(p);
            }
        }

        return Optional.empty();
    }

    private boolean isWall(Vector2I pos) {
        return walls.contains(pos);
    }

    public long getSumOfBoxGpsCoordinates() {
        return boxes.stream().mapToLong(Warehouse::toGpsCoordinate).sum();
    }

    private static long toGpsCoordinate(Vector2I pos) {
        return pos.getY() * 100L + pos.getX();
    }

    private String visualize() {
        var boundX = 0;
        var boundY = 0;

        var m = new HashMap<Vector2I, Character>();

        for (var wall : walls) {
            m.put(wall, '#');

            boundX = Math.max(boundX, wall.getX());
            boundY = Math.max(boundY, wall.getY());
        }

        for (var box : boxes) {
            if (scaleX == 1) {
                m.put(box, 'O');
            } else {
                m.put(box, '[');
                m.put(box.add(DIR_RIGHT), ']');
            }

            boundX = Math.max(boundX, box.getX() + scaleX - 1);
            boundY = Math.max(boundY, box.getY() + scaleX - 1);
        }

        m.put(robotPosition, '@');
        boundX = Math.max(boundX, robotPosition.getX());
        boundY = Math.max(boundY, robotPosition.getY());

        var sb = new StringBuilder();

        for (var y = 0; y <= boundY; y++) {
            for (var x = 0; x <= boundX; x++) {
                var c = m.get(new Vector2I(x, y));

                if (c == null) {
                    c = '.';
                }

                sb.append(c);
            }

            sb.append('\n');
        }

        sb.setLength(sb.length() - 1);

        return sb.toString();
    }

}
