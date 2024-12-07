package net.boomer41.aoc2024.day7;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class FormulaInput {

    @Getter
    private long expectedResult;

    private long[] numbers;

    public boolean isSolvable(boolean concatenationEnabled) {
        // Every to bits equals one operator encoded
        // when no concatenation operator is enabled
        // otherwise two bits.
        var factor = concatenationEnabled ? 2L : 1L;
        var mask = concatenationEnabled ? 3L : 1L;
        var maxI = 1L << (factor * (numbers.length - 1));

        for (var i = 0L; i < maxI; i++) {
            var result = numbers[0];

            for (var numberIndex = 1; numberIndex < numbers.length; numberIndex++) {
                var operator = (int) ((i >> (factor * (numberIndex - 1))) & mask);

                result = switch (operator) {
                    case 0 -> result + numbers[numberIndex];
                    case 1 -> result * numbers[numberIndex];
                    case 2 -> Long.parseLong(result + Long.toString(numbers[numberIndex]));
                    case 3 -> -1;
                    default -> throw new UnsupportedOperationException();
                };
            }

            if (result < 0) {
                continue;
            }

            if (expectedResult != result) {
                continue;
            }

            return true;
        }

        return false;
    }

}
