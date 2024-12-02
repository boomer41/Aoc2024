package net.boomer41.aoc2024.day2;

import java.io.InputStream;
import java.util.*;

public class Report {

    private List<Integer> levels;

    public Report(String input) throws InvalidInputException {
        var levelStrings = input.split("\\s+");

        levels = new ArrayList<>();

        for (int i = 0; i < levelStrings.length; i++) {
            try {
                levels.add(Integer.parseInt(levelStrings[i]));
            } catch (NumberFormatException e) {
                throw new InvalidInputException("Invalid number", e);
            }
        }
    }

    public boolean isValid() {
        return getPossibleProblemLocation(levels).isEmpty();
    }

    public boolean isValidWithDampener() {
        var problemLocations = getPossibleProblemLocation(levels);

        // Works as is
        if (problemLocations.isEmpty()) {
            return true;
        }

        // Try removing each element in the problem locations
        for (var index : problemLocations) {
            // Value <0 equals unsolvable
            if (index < 0) {
                continue;
            }

            var newLevels = new ArrayList<>(levels);
            newLevels.remove((int) index); // Force usage of index method

            if (!getPossibleProblemLocation(newLevels).isEmpty()) {
                continue;
            }

            // Found one!
            return true;
        }

        return false;
    }

    private static List<Integer> getPossibleProblemLocation(List<Integer> levels) {
        if (levels.size() < 2) {
            return Collections.singletonList(-1);
        }

        var descending = false;

        for (var i = 1; i < levels.size(); i++) {
            var previous = levels.get(i - 1);
            var current = levels.get(i);

            // diff > 0 when previous > current, i.e. descending
            var diff = previous - current;

            if (diff == 0) {
                return Arrays.asList(i - 1, i);
            }

            if (Math.abs(diff) > 3) {
                return Arrays.asList(i - 1, i);
            }

            if (i == 1) {
                descending = diff > 0;
                continue;
            }

            // First pair was descending, but now we are ascending
            if (descending && diff < 0) {
                return Arrays.asList(i - 1, i);
            }

            // First pair was ascending, but now we are descending
            if (!descending && diff > 0) {
                return Arrays.asList(i - 1, i);
            }
        }

        return Collections.emptyList();
    }

    public static List<Report> parseReports(InputStream inputStream) throws InvalidInputException {
        var scanner = new Scanner(inputStream);
        var list = new ArrayList<Report>();

        while (scanner.hasNextLine()) {
            list.add(new Report(scanner.nextLine()));
        }

        return list;
    }

}
