package net.boomer41.aoc2024.util.graph;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class AdjacencyMatrix<T> {

    private final Map<Vertex<T>, Map<Vertex<T>, Long>> adjacencyMatrix = new HashMap<>();

    void put(Vertex<T> from, Vertex<T> to, Long distance) {
        var fromMap = adjacencyMatrix.computeIfAbsent(from, _ -> new HashMap<>());
        fromMap.put(to, distance);
    }

    public Optional<Long> get(Vertex<T> from, Vertex<T> to) {
        var fromMap = adjacencyMatrix.get(from);

        if (fromMap == null) {
            return Optional.empty();
        }

        var toValue = fromMap.get(to);

        if (toValue == null) {
            return Optional.empty();
        }

        return Optional.of(toValue);
    }

    public AdjacencyMatrix<T> copy() {
        var target = new AdjacencyMatrix<T>();

        for (var from : adjacencyMatrix.entrySet()) {
            for (var to : from.getValue().entrySet()) {
                target.put(from.getKey(), to.getKey(), to.getValue());
            }
        }

        return target;
    }

}
