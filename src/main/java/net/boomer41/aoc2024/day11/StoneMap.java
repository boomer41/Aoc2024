package net.boomer41.aoc2024.day11;

import lombok.*;

import java.util.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StoneMap {

    private final List<Long> stones;

    public static StoneMap fromString(String input) {
        var stones = new LinkedList<Long>();

        var stoneStrings = input.split(" ");

        for (var stoneString : stoneStrings) {
            stones.add(Long.parseLong(stoneString));
        }

        return new StoneMap(stones);
    }

    public long blink(long iterationCount) {
        var stoneCount = 0L;
        var cache = new HashMap<LevelCacheKey, Long>();

        for (var stone : stones) {
            stoneCount += blinkSingleStone(stone, 0L, iterationCount, cache);
        }

        return stoneCount;
    }

    private long blinkSingleStone(long stone, long level, long iterationCount, Map<LevelCacheKey, Long> cache) {
        var cacheKey = new LevelCacheKey(stone, level);

        var cacheValue = cache.get(cacheKey);

        if (cacheValue != null) {
            return cacheValue;
        }

        var result = blinkSingleStoneNonCached(stone, level, iterationCount, cache);

        cache.put(cacheKey, result);
        return result;
    }

    private long blinkSingleStoneNonCached(long stone, long level, long iterationCount, Map<LevelCacheKey, Long> cache) {
        if (level == iterationCount) {
            return 1L;
        }

        if (stone == 0) {
            return blinkSingleStone(1L, level + 1, iterationCount, cache);
        }

        var digitCount = calculateDigitCount(stone);

        if (digitCount % 2 != 0) {
            return blinkSingleStone(stone * 2024L, level + 1, iterationCount, cache);
        }

        var rightStoneMultiplicator = 1L;
        var rightStone = 0L;
        var leftStone = stone;

        for (var i = 0; i < digitCount / 2; i++) {
            var toMove = leftStone % 10L;
            leftStone /= 10L;

            rightStone = rightStone + toMove * rightStoneMultiplicator;
            rightStoneMultiplicator *= 10L;
        }

        return blinkSingleStone(leftStone, level + 1, iterationCount, cache)
                + blinkSingleStone(rightStone, level + 1, iterationCount, cache);
    }

    private static int calculateDigitCount(long stone) {
        var digits = 0;

        while (stone > 0) {
            digits++;
            stone /= 10;
        }

        return digits;
    }

}
