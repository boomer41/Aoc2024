package net.boomer41.aoc2024.day10;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.boomer41.aoc2024.util.graph.Graph;
import net.boomer41.aoc2024.util.graph.Vertex;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HikeMap {

    private final int mapWidth;

    private final int mapHeight;

    private final Graph<Integer> graph;

    private final List<Vertex<Integer>> startVertices;

    private final List<Vertex<Integer>> endVertices;

    private final List<Vertex<Integer>> verticesByLocation;

    public static HikeMap fromData(InputStream inputStream) throws IOException {
        var input = new String(inputStream.readAllBytes(), StandardCharsets.US_ASCII);
        var lines = input.split("\n");
        var columnCount = lines[0].length();

        var graph = new Graph<Integer>();
        var map = new ArrayList<Vertex<Integer>>(lines.length * columnCount);
        map.addAll(Collections.nCopies(lines.length * columnCount, null));

        var startVertices = new ArrayList<Vertex<Integer>>();
        var endVertices = new ArrayList<Vertex<Integer>>();

        // Read all vertices
        for (var y = 0; y < lines.length; y++) {
            for (var x = 0; x < columnCount; x++) {
                var character = lines[y].charAt(x);

                if (character == '.') {
                    continue;
                }

                if (!(character >= '0' && character <= '9')) {
                    throw new NumberFormatException("Illegal character: " + character);
                }

                var height = character - '0';

                var vertex = graph.newVertex();
                vertex.setValue(height);

                map.set(y * columnCount + x, vertex);

                if (height == 0) {
                    startVertices.add(vertex);
                } else if (height == 9) {
                    endVertices.add(vertex);
                }
            }
        }

        var hikeMap = new HikeMap(columnCount, lines.length, graph, startVertices, endVertices, map);
        hikeMap.connectPaths();

        return hikeMap;
    }

    private void connectPaths() {
        // Connect them
        for (int fromY = 0; fromY < mapHeight; fromY++) {
            for (var fromX = 0; fromX < mapWidth; fromX++) {
                var from = verticesByLocation.get(fromY * mapWidth + fromX);

                if (from == null) {
                    continue;
                }

                for (var direction = 0; direction < 4; direction++) {
                    var toX = fromX;
                    var toY = fromY;

                    switch (direction) {
                        case 0:
                            toX++;
                            break;
                        case 1:
                            toX--;
                            break;
                        case 2:
                            toY++;
                            break;
                        case 3:
                            toY--;
                            break;
                    }

                    if (toX < 0 || toY < 0 || toX >= mapWidth || toY >= mapHeight) {
                        continue;
                    }

                    var to = verticesByLocation.get(toY * mapWidth + toX);

                    if (to == null) {
                        continue;
                    }

                    var delta = to.getValue() - from.getValue();

                    if (delta != 1) {
                        continue;
                    }

                    graph.connect(from, to, 1);
                }
            }
        }
    }

    public long getTotalScoreOfTrails(boolean floydWarshall) {
        if (floydWarshall) {
            var trails = graph.floydWarshall();

            var score = 0L;

            for (var startVertex : startVertices) {
                for (var endVertex : endVertices) {
                    score += trails.get(startVertex, endVertex).map(_ -> 1).orElse(0);
                }
            }

            return score;
        } else {
            var score = 0L;

            for (var startVertex : startVertices) {
                for (var endVertex : endVertices) {
                    score += graph.getShortestPath(startVertex, endVertex).map(_ -> 1).orElse(0);
                }
            }

            return score;
        }
    }

    public long getTotalRatingOfTrails() {
        var score = 0L;

        for (var startVertex : startVertices) {
            for (var endVertex : endVertices) {
                score += graph.getAllPaths(startVertex, endVertex).size();
            }
        }

        return score;
    }

}
