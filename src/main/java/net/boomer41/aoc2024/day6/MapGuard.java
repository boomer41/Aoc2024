package net.boomer41.aoc2024.day6;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class MapGuard implements MapObject {

    @Setter
    private int currentPosition;

    private GuardDirection direction;

    public Optional<Integer> walk(Map map) {
        var x = currentPosition % map.getMapColumns();
        var y = currentPosition / map.getMapColumns();

        switch (direction) {
            case UP:
                y--;
                break;
            case DOWN:
                y++;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
            default:
                throw new UnsupportedOperationException();
        }

        if (!map.isValidPosition(x, y)) {
            return Optional.empty();
        }

        if (!map.isObstacle(x, y)) {
            return Optional.of(y * map.getMapColumns() + x);
        }

        direction = switch (direction) {
            case UP -> GuardDirection.RIGHT;
            case RIGHT -> GuardDirection.DOWN;
            case DOWN -> GuardDirection.LEFT;
            case LEFT -> GuardDirection.UP;
        };

        return Optional.of(currentPosition);
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public MapObject duplicate() {
        return new MapGuard(currentPosition, direction);
    }

}
