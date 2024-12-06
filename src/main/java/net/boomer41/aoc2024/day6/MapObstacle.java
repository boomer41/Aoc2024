package net.boomer41.aoc2024.day6;

public class MapObstacle implements MapObject {

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public MapObject duplicate() {
        return new MapObstacle();
    }

}
