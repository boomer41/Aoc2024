package net.boomer41.aoc2024.day1;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day1Solver {

    private static Lists read(InputStream inputStream) throws InvalidInputException {
        var left = new ArrayList<Integer>();
        var right = new ArrayList<Integer>();

        var scanner = new Scanner(inputStream);

        while (scanner.hasNext()) {
            var line = scanner.nextLine();

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            var split = line.split("\\s+");

            if (split.length != 2) {
                throw new InvalidInputException("There must be exactly two numbers per line");
            }

            try {
                left.add(Integer.parseInt(split[0]));
                right.add(Integer.parseInt(split[1]));
            } catch (NumberFormatException e) {
                throw new InvalidInputException("Invalid number format", e);
            }
        }

        return new Lists(left, right);
    }

    public static long solve1(InputStream inputStream) throws InvalidInputException {
        var lists = read(inputStream);
        lists = lists.sort();

        var sum = 0;

        // Assertion: Both lists contain the same number of elements
        for (var i = 0; i < lists.getLeft().size(); i++) {
            var leftNumber = lists.getLeft().get(i);
            var rightNumber = lists.getRight().get(i);

            var distance = Math.abs(leftNumber - rightNumber);
            sum += distance;
        }

        return sum;
    }

    public static long solve2(InputStream inputStream) throws InvalidInputException {
        var lists = read(inputStream);
        lists = lists.sort();

        var counts = new HashMap<Integer, Integer>();

        for (var i = 0; i < lists.getRight().size(); i++) {
            var num = counts.getOrDefault(lists.getRight().get(i), 0);
            num++;
            counts.put(lists.getRight().get(i), num);
        }

        var sum = 0;

        for (var i = 0; i < lists.getLeft().size(); i++) {
            var leftNumber = lists.getLeft().get(i);
            var rightCount = counts.getOrDefault(leftNumber, 0);

            sum += leftNumber * rightCount;
        }

        return sum;
    }

}
