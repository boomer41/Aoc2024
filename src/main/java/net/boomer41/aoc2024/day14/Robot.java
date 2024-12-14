package net.boomer41.aoc2024.day14;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.boomer41.aoc2024.util.Vector2I;

import java.util.regex.Pattern;

@AllArgsConstructor
public class Robot {

    private static final Pattern LINE_FORMAT = Pattern.compile("^p=(?<px>-?\\d+),(?<py>-?\\d+) v=(?<vx>-?\\d+),(?<vy>-?\\d+)$");

    private final RobotMap map;

    @Getter
    private Vector2I position;

    private Vector2I velocity;

    public void tick(int count) {
        var newPos = position.add(velocity.multiply(count));

        var newX = applyBounds(newPos.getX(), map.getBounds().getX());
        var newY = applyBounds(newPos.getY(), map.getBounds().getY());

        position = new Vector2I(newX, newY);
    }

    static Robot fromString(RobotMap map, String input) {
        var match = LINE_FORMAT.matcher(input);

        if (!match.matches()) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }

        return new Robot(
                map,
                new Vector2I(Integer.parseInt(match.group("px")), Integer.parseInt(match.group("py"))),
                new Vector2I(Integer.parseInt(match.group("vx")), Integer.parseInt(match.group("vy")))
        );
    }

    private static int applyBounds(int num, int bounds) {
        if (num < 0) {
            var diff = (-num) / bounds + 1;
            num += diff * bounds;
        }

        return num % bounds;
    }

}
