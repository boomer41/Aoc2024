package net.boomer41.aoc2024.day5;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.util.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PrinterQueue {

    private final List<List<Integer>> jobs;

    private final Map<Integer, Set<Integer>> dependencies;

    public static PrinterQueue parse(InputStream inputStream) {
        var scanner = new Scanner(inputStream);

        // First: Dependencies
        var dependencies = new HashMap<Integer, Set<Integer>>();

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            if (line.isEmpty()) {
                break;
            }

            var lineSplit = line.split("\\|", 2);
            var prerequisite = Integer.parseInt(lineSplit[0]);
            var thePage = Integer.parseInt(lineSplit[1]);

            dependencies.computeIfAbsent(thePage, _ -> new HashSet<>()).add(prerequisite);
        }

        // Second: Jobs
        var jobs = new ArrayList<List<Integer>>();

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();

            if (line.isEmpty()) {
                break;
            }

            var lineSplit = line.split(",");
            var job = Arrays.stream(lineSplit).map(Integer::parseInt).toList();
            jobs.add(job);
        }

        return new PrinterQueue(jobs, dependencies);
    }

    private Optional<Integer> getProblemLocationInJob(List<Integer> job) {
        var seen = new HashSet<Integer>();
        var relevantPages = new HashSet<>(job);

        Optional<Integer> problemLocation = Optional.empty();

        for (var i = 0; i < job.size(); i++) {
            var page = job.get(i);

            var dependenciesForPage = dependencies.computeIfAbsent(page, _ -> Collections.emptySet());

            for (var prerequisite : dependenciesForPage) {
                if (!relevantPages.contains(prerequisite)) {
                    continue;
                }

                if (seen.contains(prerequisite)) {
                    continue;
                }

                problemLocation = Optional.of(i);
                break;
            }

            // Do not break the outer loop, find the _last_ problem location
            // to start swapping in the end.

            seen.add(page);
        }

        return problemLocation;
    }

    private boolean isValidJob(List<Integer> job) {
        return getProblemLocationInJob(job).isEmpty();
    }

    public long getSumOfMiddleNumbersOfValidJobs() {
        return jobs.stream()
                .filter(this::isValidJob)
                .mapToLong(j -> j.get(j.size() / 2))
                .sum();
    }

    private List<Integer> fixJob(List<Integer> job) {
        // Make modifiable
        var modifiableJob = new ArrayList<>(job);

        while (true) {
            var problemLocation = getProblemLocationInJob(modifiableJob);

            if (problemLocation.isEmpty()) {
                return modifiableJob;
            }

            // The problem location found is the _last_ problem in the job
            // So we basically start swapping in the end to fix the job
            var page = modifiableJob.get(problemLocation.get());
            modifiableJob.remove((int) problemLocation.get());
            modifiableJob.add(problemLocation.get() + 1, page);
        }
    }

    public long getSumOfMiddleNumbersOfIncorrectButFixedJobs() {
        return jobs.stream()
                .filter(j -> !isValidJob(j))
                .map(this::fixJob)
                .mapToLong(j -> j.get(j.size() / 2))
                .sum();
    }

}
