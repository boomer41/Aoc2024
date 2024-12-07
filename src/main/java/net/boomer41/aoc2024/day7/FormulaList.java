package net.boomer41.aoc2024.day7;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FormulaList {

    private List<FormulaInput> rawFormulas;

    public static FormulaList parse(InputStream input) {
        var scanner = new Scanner(input);

        var formulas = new ArrayList<FormulaInput>();

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            var split1 = line.split(":", 2);
            var expectedResult = Long.parseLong(split1[0]);

            var numberStrings = split1[1].split(" ");
            var numbers = Arrays.stream(numberStrings)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .mapToLong(Long::parseLong)
                    .toArray();

            formulas.add(new FormulaInput(expectedResult, numbers));
        }

        return new FormulaList(formulas);
    }

    public List<FormulaInput> findAllPossible() {
        return rawFormulas.stream().filter(f -> f.isSolvable(false)).toList();
    }

    public List<FormulaInput> findAllPossibleIncludingConcatenation() {
        return rawFormulas.stream().filter(f -> f.isSolvable(true)).toList();
    }

}
