package net.boomer41.aoc2024.day13;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.boomer41.aoc2024.util.StraightLine2L;
import net.boomer41.aoc2024.util.Vector2L;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.regex.Pattern;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ArcadeMachine {

    private static final Pattern BUTTON_A_PATTERN = Pattern.compile("^Button A: X\\+(?<x>\\d+), Y\\+(?<y>\\d+)$");
    private static final Pattern BUTTON_B_PATTERN = Pattern.compile("^Button B: X\\+(?<x>\\d+), Y\\+(?<y>\\d+)$");
    private static final Pattern PRIZE_PATTERN = Pattern.compile("^Prize: X=(?<x>\\d+), Y=(?<y>\\d+)$");

    private static final Vector2L CORRECTION = new Vector2L(10000000000000L, 10000000000000L);

    private static final long BUTTON_A_PRICE = 3L;
    private static final long BUTTON_B_PRICE = 1L;

    private final Vector2L buttonADelta;

    private final Vector2L buttonBDelta;

    private final Vector2L prize;

    public static List<ArcadeMachine> parseList(InputStream inputStream) throws InvalidFormatException {
        var scanner = new Scanner(inputStream);
        var machineList = new ArrayList<ArcadeMachine>();

        while (scanner.hasNextLine()) {
            var buttonA = scanner.nextLine();

            if (buttonA.equalsIgnoreCase("")) {
                continue;
            }

            var buttonB = scanner.nextLine();
            var prize = scanner.nextLine();

            var buttonAMatch = BUTTON_A_PATTERN.matcher(buttonA);
            var buttonBMatch = BUTTON_B_PATTERN.matcher(buttonB);
            var prizeMatch = PRIZE_PATTERN.matcher(prize);

            if (!buttonAMatch.matches()) {
                throw new InvalidFormatException("Invalid button A line: " + buttonA);
            }

            if (!buttonBMatch.matches()) {
                throw new InvalidFormatException("Invalid button B line: " + buttonA);
            }

            if (!prizeMatch.matches()) {
                throw new InvalidFormatException("Invalid prize line: " + buttonA);
            }

            var buttonAVector = new Vector2L(
                Integer.parseInt(buttonAMatch.group("x")),
                Integer.parseInt(buttonAMatch.group("y"))
            );

            var buttonBVector = new Vector2L(
                    Integer.parseInt(buttonBMatch.group("x")),
                    Integer.parseInt(buttonBMatch.group("y"))
            );

            var prizeVector = new Vector2L(
                    Integer.parseInt(prizeMatch.group("x")),
                    Integer.parseInt(prizeMatch.group("y"))
            );

            machineList.add(new ArcadeMachine(buttonAVector, buttonBVector, prizeVector));
        }

        return machineList;
    }

    public Optional<Long> getMinimumPrice(boolean withPrizeCorrection) {
        var realPrize = prize;

        if (withPrizeCorrection) {
            realPrize = realPrize.add(CORRECTION);
        }

        var line1 = new StraightLine2L(Vector2L.ZERO, buttonADelta);
        var line2 = new StraightLine2L(realPrize, buttonBDelta);

        var p = line1.intersectInternal(line2);

        if (p.isEmpty()) {
            return Optional.empty();
        }

        var count1 = p.get().getX() / buttonADelta.getX();
        var count2 = realPrize.subtract(p.get()).getX() / buttonBDelta.getX();

        return Optional.of(count1 * BUTTON_A_PRICE + count2 * BUTTON_B_PRICE);
    }

}
