package net.boomer41.aoc2024.day1;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Lists {

    private final List<Integer> left;

    private final List<Integer> right;

    public Lists(List<Integer> left, List<Integer> right) {
        this.left = left;
        this.right = right;
    }

    public Lists sort() {
        var leftSorted = left.stream().sorted().toList();
        var rightSorted = right.stream().sorted().toList();

        return new Lists(leftSorted, rightSorted);
    }

}
