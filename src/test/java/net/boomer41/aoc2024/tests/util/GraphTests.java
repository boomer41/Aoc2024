package net.boomer41.aoc2024.tests.util;

import net.boomer41.aoc2024.util.graph.Graph;
import net.boomer41.aoc2024.util.graph.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

public class GraphTests {

    @Test
    public void testAddEdge() {
        var graph = new Graph<>();
        var v1 = graph.newVertex();
        var v2 = graph.newVertex();

        graph.connect(v1, v2, 1);
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> graph.connect(v1, v2, 2));
    }

    @Test
    public void testFloydWarshall() {
        var graph = new Graph<>();
        var v0 = graph.newVertex(0);
        var v1 = graph.newVertex(1);
        var v2 = graph.newVertex(2);
        var v3 = graph.newVertex(3);

        graph.connect(v0, v1, 1);
        graph.connect(v0, v2, 10);
        graph.connect(v1, v2, 1);
        graph.connect(v3, v1, 100);

        var floydWarshall = graph.floydWarshall();

        Assertions.assertEquals(Optional.of(0L), floydWarshall.get(v0, v0));
        Assertions.assertEquals(Optional.of(1L), floydWarshall.get(v0, v1));
        Assertions.assertEquals(Optional.of(2L), floydWarshall.get(v0, v2));
        Assertions.assertEquals(Optional.empty(), floydWarshall.get(v0, v3));

        Assertions.assertEquals(Optional.empty(), floydWarshall.get(v3, v0));
        Assertions.assertEquals(Optional.of(100L), floydWarshall.get(v3, v1));
        Assertions.assertEquals(Optional.of(101L), floydWarshall.get(v3, v2));
    }

    @Test
    public void testFloydWarshall2() {
        var graph = new Graph<>();
        var start = graph.newVertex("start");
        var end = graph.newVertex("end");

        {
            var previous = start;

            for (var i = 0; i < 30; i++) {
                var next = graph.newVertex(i);
                graph.connect(previous, next, 1);
                previous = next;
            }

            graph.connect(previous, end, 1);
        }

        var floydWarshall = graph.floydWarshall();

        Assertions.assertEquals(Optional.of(31L), floydWarshall.get(start, end));
    }

    @Test
    public void testShortestPath() {
        var graph = new Graph<>();
        var v0 = graph.newVertex(0);
        var v1 = graph.newVertex(1);
        var v2 = graph.newVertex(2);
        var v3 = graph.newVertex(3);

        graph.connect(v0, v1, 1);
        graph.connect(v0, v2, 10);
        graph.connect(v1, v2, 1);
        graph.connect(v3, v1, 100);

        Assertions.assertEquals(Optional.of(0L), graph.getShortestPath(v0, v0).map(Path::getDistance));
        Assertions.assertEquals(Optional.of(1L), graph.getShortestPath(v0, v1).map(Path::getDistance));
        Assertions.assertEquals(Optional.of(2L), graph.getShortestPath(v0, v2).map(Path::getDistance));
        Assertions.assertEquals(Optional.empty(), graph.getShortestPath(v0, v3).map(Path::getDistance));

        Assertions.assertEquals(Optional.empty(), graph.getShortestPath(v3, v0).map(Path::getDistance));
        Assertions.assertEquals(Optional.of(100L), graph.getShortestPath(v3, v1).map(Path::getDistance));
        Assertions.assertEquals(Optional.of(101L), graph.getShortestPath(v3, v2).map(Path::getDistance));
    }

    @Test
    public void testShortestPath2() {
        var graph = new Graph<>();
        var start = graph.newVertex("start");
        var end = graph.newVertex("end");

        {
            var previous = start;

            for (var i = 0; i < 30; i++) {
                var next = graph.newVertex(i);
                graph.connect(previous, next, 1);
                previous = next;
            }

            graph.connect(previous, end, 1);
        }

        Assertions.assertEquals(Optional.of(31L), graph.getShortestPath(start, end).map(Path::getDistance));
    }

    @Test
    public void testAllPaths1() {
        var graph = new Graph<>();
        var start = graph.newVertex("start");
        var end = graph.newVertex("end");

        graph.connect(start, end, 1);

        var paths = graph.getAllPaths(start, end);
        Assertions.assertEquals(1, paths.size());
    }

    @Test
    public void testAllPaths2() {
        var graph = new Graph<>();
        var start = graph.newVertex("start");
        var middle = graph.newVertex("middle");
        var end = graph.newVertex("end");

        graph.connect(start, middle, 1);
        graph.connect(middle, end, 1);

        var paths = graph.getAllPaths(start, end);
        Assertions.assertEquals(1, paths.size());
    }

    @Test
    public void testAllPaths3() {
        var graph = new Graph<>();
        var start = graph.newVertex("start");
        var middle1 = graph.newVertex("middle 1");
        var middle2 = graph.newVertex("middle 2");
        var end = graph.newVertex("end");

        graph.connect(start, middle1, 1);
        graph.connect(middle1, end, 1);

        graph.connect(start, middle2, 1);
        graph.connect(middle2, end, 1);

        var paths = graph.getAllPaths(start, end);
        Assertions.assertEquals(2, paths.size());
    }

    @Test
    public void testAllPaths4() {
        var graph = new Graph<>();
        var start = graph.newVertex("start");
        var middle1 = graph.newVertex("middle 1");
        var middle2 = graph.newVertex("middle 2");
        var end = graph.newVertex("end");

        var useless1 = graph.newVertex("useless 1");
        var useless2 = graph.newVertex("useless 2");
        var useless3 = graph.newVertex("useless 3");

        graph.connect(start, middle1, 1);
        graph.connect(middle1, end, 1);

        graph.connect(start, middle2, 1);
        graph.connect(middle2, end, 1);

        graph.connect(start, useless1, 1);
        graph.connect(middle1, useless2, 1);
        graph.connect(middle2, useless3, 1);

        graph.connect(useless1, useless2, 1);
        graph.connect(useless1, useless3, 1);
        graph.connect(useless1, useless1, 1);

        var paths = graph.getAllPaths(start, end);
        Assertions.assertEquals(2, paths.size());
    }

    @Test
    public void testGetters() {
        var graph = new Graph<>();

        var v0 = graph.newVertex(0);
        Assertions.assertEquals(1, graph.getVertices().size());
        Assertions.assertEquals(v0, graph.getVertices().getFirst());
        Assertions.assertEquals(0, graph.getEdges().size());

        var v1 = graph.newVertex(1);
        Assertions.assertEquals(2, graph.getVertices().size());
        Assertions.assertIterableEquals(Arrays.asList(v0, v1), graph.getVertices());
        Assertions.assertEquals(0, graph.getEdges().size());

        var e1 = graph.connect(v0, v1, 42);
        Assertions.assertEquals(e1, graph.getEdges().getFirst());
        Assertions.assertEquals(42, e1.getWeight());
        Assertions.assertEquals(v0, e1.getFrom());
        Assertions.assertEquals(v1, e1.getTo());

        var path = graph.getShortestPath(v0, v1);
        Assertions.assertTrue(path.isPresent());
        Assertions.assertEquals(42, path.get().getDistance());

        Assertions.assertEquals(1, path.get().getUsedEdges().size());
        Assertions.assertEquals(Optional.of(e1), path.get().getUsedEdges().stream().findFirst());

        Assertions.assertEquals(1, path.get().getEdges().size());
        Assertions.assertEquals(e1, path.get().getEdges().getFirst());
    }

    @Test
    public void testUnmodifiable() {
        var graph = new Graph<>();

        Assertions.assertThrows(UnsupportedOperationException.class, () -> graph.getVertices().add(null));
        Assertions.assertThrows(UnsupportedOperationException.class, () -> graph.getEdges().add(null));
    }

}
