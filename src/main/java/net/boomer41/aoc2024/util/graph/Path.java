package net.boomer41.aoc2024.util.graph;

import lombok.*;

import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public class Path<T> {

    private final Vertex<T> start;

    @Setter(AccessLevel.NONE)
    private long distance;

    @ToString.Exclude
    private final Set<Edge<T>> usedEdges = new HashSet<>();

    private final List<Edge<T>> edges = new ArrayList<>();

    public Set<Edge<T>> getUsedEdges() {
        return Collections.unmodifiableSet(usedEdges);
    }

    public List<Edge<T>> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public void addEdge(Edge<T> edge) {
        if (isUsed(edge)) {
            throw new IllegalArgumentException("Edge already used: " + edge);
        }

        edges.add(edge);
        usedEdges.add(edge);
        distance += edge.getWeight();
    }

    public Path<T> copy() {
        var path = new Path<T>(start);
        path.distance = distance;
        path.edges.addAll(edges);
        path.usedEdges.addAll(usedEdges);

        return path;
    }

    public boolean isUsed(Edge<T> edge) {
        return usedEdges.contains(edge);
    }

    public Vertex<T> getEnd() {
        if (edges.isEmpty()) {
            return start;
        }

        return edges.getLast().getTo();
    }

}
