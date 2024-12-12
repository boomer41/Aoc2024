package net.boomer41.aoc2024.day12;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import net.boomer41.aoc2024.util.Vector2I;

import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RegionMap {
    
    private final List<Region> regions;

    public static RegionMap fromInputStream(InputStream inputStream) {
        var scanner = new Scanner(inputStream);

        var regionBuilders = new ArrayList<RegionBuilder>();
        var regionsAbove = new HashMap<Integer, RegionBuilder>();
        var regionsThisRow = new HashMap<Integer, RegionBuilder>();
        RegionBuilder regionLeft;

        var y = -1;

        while (scanner.hasNextLine()) {
            var line = scanner.nextLine().toCharArray();
            y++;

            regionLeft = null;

            {
                var tmp = regionsAbove;
                regionsAbove = regionsThisRow;
                regionsThisRow = tmp;

                // No need to clear regionsThisRow, as we sweep from left to right and will not
                // access any elements more right than our current index
            }

            for (var x = 0; x < line.length; x++) {
                var regionId = line[x];
                var location = new Vector2I(x, y);

                RegionBuilder thisRegion = null;
                var regionAbove = regionsAbove.get(x);

                if (regionAbove != null && regionAbove.getName() == regionId) {
                    regionAbove.addLocation(location);
                    thisRegion = regionAbove;
                }

                if (regionLeft != null && regionLeft.getName() == regionId) {
                    if (thisRegion != null && thisRegion != regionLeft) {
                        regionAbove.mergeWith(regionLeft);

                        // leftRegion is no longer valid
                        // Replace all occurences
                        regionBuilders.remove(regionLeft);

                        var finalRegionLeft = regionLeft;
                        regionsThisRow.replaceAll((_, r ) -> {
                            if (r != finalRegionLeft) {
                                return r;
                            }

                            return regionAbove;
                        });

                        // thisRegion has not changed, still aboveRegion
                    } else {
                        regionLeft.addLocation(location);
                        thisRegion = regionLeft;
                    }
                }

                if (thisRegion == null) {
                    thisRegion = new RegionBuilder(regionId);
                    thisRegion.addLocation(location);

                    regionBuilders.add(thisRegion);
                }

                regionsThisRow.put(x, thisRegion);
                regionLeft = thisRegion;
            }
        }

        var regions = regionBuilders.stream().map(RegionBuilder::build).toList();

        return new RegionMap(regions);
    }

    public List<Region> getRegions() {
        return Collections.unmodifiableList(regions);
    }

}
