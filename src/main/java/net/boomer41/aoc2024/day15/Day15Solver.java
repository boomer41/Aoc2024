package net.boomer41.aoc2024.day15;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day15Solver {

    public static long solve1(InputStream inputStream, WarehouseVisualizationCallback callback) {
        var warehouse = Warehouse.fromInput(inputStream, false);
        warehouse.setVisualizationCallback(callback);
        warehouse.executeMovements();

        return warehouse.getSumOfBoxGpsCoordinates();
    }

    public static long solve2(InputStream inputStream, WarehouseVisualizationCallback callback) {
        var warehouse = Warehouse.fromInput(inputStream, true);
        warehouse.setVisualizationCallback(callback);
        warehouse.executeMovements();

        return warehouse.getSumOfBoxGpsCoordinates();
    }

}
