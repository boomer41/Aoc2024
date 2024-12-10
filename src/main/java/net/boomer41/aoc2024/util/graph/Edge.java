package net.boomer41.aoc2024.util.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Edge<T> {

    private final Vertex<T> from;

    private final Vertex<T> to;

    private final long weight;

}
