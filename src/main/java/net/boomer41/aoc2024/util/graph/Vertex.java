package net.boomer41.aoc2024.util.graph;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vertex<T> {

    @EqualsAndHashCode.Include
    private final long id;

    private T value;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private final List<Edge<T>> outgoingEdges = new ArrayList<>();

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.NONE)
    @ToString.Exclude
    private final List<Edge<T>> incomingEdges = new ArrayList<>();

}
