package net.boomer41.aoc2024.util.graph;

import lombok.Getter;

import java.util.*;

public class Graph<T> {

    private long nextVertexId;

    private final List<Vertex<T>> vertices;

    private final List<Edge<T>> edges;

    @Getter
    private final AdjacencyMatrix<T> adjacencyMatrix;

    public Graph() {
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        adjacencyMatrix = new AdjacencyMatrix<>();
    }

    public List<Vertex<T>> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    public List<Edge<T>> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public Vertex<T> newVertex() {
        return newVertex(null);
    }

    public Vertex<T> newVertex(T value) {
        var v = new Vertex<T>(nextVertexId++);

        v.setValue(value);
        vertices.add(v);

        return v;
    }

    public Edge<T> connect(Vertex<T> from, Vertex<T> to, long weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("weight cannot be negative");
        }

        if (adjacencyMatrix.get(from, to).isPresent()) {
            throw new IllegalArgumentException("adjacency matrix already contains corresponding edge");
        }

        var edge = new Edge<>(from, to, weight);
        edges.add(edge);

        from.getOutgoingEdges().add(edge);
        to.getIncomingEdges().add(edge);

        adjacencyMatrix.put(from, to, weight);

        return edge;
    }

    public AdjacencyMatrix<T> floydWarshall() {
        var distanceMap = adjacencyMatrix.copy();

        // Put distances to own node
        for (var vertex : vertices) {
            distanceMap.put(vertex, vertex, 0L);
        }

        for (var middleNode : vertices) {
            for (var fromNode : vertices) {
                for (var toNode : vertices) {
                    var distanceWith =
                            distanceMap.get(fromNode, middleNode)
                                    .flatMap(d1 ->
                                            distanceMap.get(middleNode, toNode)
                                                    .map(d2 -> d1 + d2));

                    if (distanceWith.isEmpty()) {
                        continue;
                    }

                    var distanceWithout = distanceMap.get(fromNode, toNode);

                    if (distanceWithout.isEmpty() || distanceWith.get() < distanceWithout.get()) {
                        distanceMap.put(fromNode, toNode, distanceWith.get());
                    }
                }
            }
        }

        return distanceMap;
    }

    public Optional<Path<T>> getShortestPath(Vertex<T> from, Vertex<T> to) {
        return dijkstra(from, to, true).stream().findFirst();
    }

    public List<Path<T>> getAllPaths(Vertex<T> from, Vertex<T> to) {
        return dijkstra(from, to, false);
    }

    private List<Path<T>> dijkstra(Vertex<T> from, Vertex<T> to, boolean breakOnFirst) {
        var toDo = new PriorityQueue<>(new PathDistanceComparer<T>());

        // Create initial path
        toDo.add(new Path<>(from));

        var foundPaths = new ArrayList<Path<T>>();

        while (!toDo.isEmpty()) {
            var path = toDo.poll();
            var end = path.getEnd();

            if (end.equals(to)) {
                foundPaths.add(path);

                if (breakOnFirst) {
                    break;
                }

                continue;
            }

            var newEdges = end.getOutgoingEdges();

            for (var newEdge : newEdges) {
                if (path.isUsed(newEdge)) {
                    continue;
                }

                // Optimization: When only one path is being generated, reuse the old path
                var newPath = newEdges.size() != 1 ? path.copy() : path;
                newPath.addEdge(newEdge);

                toDo.add(newPath);
            }
        }

        return foundPaths;
    }

}
