package net.boomer41.aoc2024.day3;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MultiplyInstruction {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("(?<mulInstruction>mul)\\((?<num1>\\d{1,3}),(?<num2>\\d{1,3})\\)|(?<doInstruction>do(?:n't)?)\\(\\)");

    private int a;

    private int b;

    public static List<MultiplyInstruction> parseValidInstructions(InputStream inputStream, boolean handleDoInstructions) throws IOException {
        var allBytes = inputStream.readAllBytes();
        var inputString = new String(allBytes, StandardCharsets.US_ASCII);

        var instructions = INSTRUCTION_PATTERN
                .matcher(inputString)
                .results()
                .toList();

        var mulInstructions = new ArrayList<MultiplyInstruction>();
        var enabled = true;

        for (var instructionGroup : instructions) {
            var instructionName = instructionGroup.group("mulInstruction");

            if (null == instructionName && handleDoInstructions) {
                instructionName = instructionGroup.group("doInstruction");
            }

            // May be a deactivated functionality
            if (instructionName == null) {
                continue;
            }

            switch (instructionName) {
                case "mul" -> {
                    if (!enabled) {
                        continue;
                    }

                    var mulInstruction = new MultiplyInstruction(Integer.parseInt(instructionGroup.group("num1")), Integer.parseInt(instructionGroup.group("num2")));
                    mulInstructions.add(mulInstruction);
                }
                case "do" -> enabled = true;
                case "don't" -> enabled = false;
                default -> throw new RuntimeException("Internal error: Unknown instruction: " + instructionName);
            }
        }

        return mulInstructions;
    }

    public int getResult() {
        return a * b;
    }

}
