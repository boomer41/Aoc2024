package net.boomer41.aoc2024.util.graph;

import java.util.Comparator;

class PathDistanceComparer<T> implements Comparator<Path<T>> {

    @Override
    public int compare(Path<T> o1, Path<T> o2) {
        var result = o1.getDistance() - o2.getDistance();

        return Long.compare(result, 0L);
    }

}
