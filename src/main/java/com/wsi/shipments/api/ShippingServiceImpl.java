package com.wsi.shipments.api;

import com.wsi.shipments.models.ZipcodeRange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class ShippingServiceImpl implements ShippingService {

    @Override
    public List<ZipcodeRange> mergeZipCodes(List<ZipcodeRange> inputZipcodeRanges) {

        List<ZipcodeRange> mergedZipcodeRanges = new ArrayList<ZipcodeRange>();

        //edge cases
        //zipcodeRanges with null or empty list, return an empty list
        if (inputZipcodeRanges == null || inputZipcodeRanges.isEmpty()) {
            return mergedZipcodeRanges;
        }

        //zipcodeRanges with 1 input, return the same input list
        if (inputZipcodeRanges.size() == 1) {
            return inputZipcodeRanges;
        }

        // For 2 or more entries in the input zip code list::
        // sort the input list
        // Add the first input to the result list.
        // For each of the rest inputs in the inputZipcodeRanges, compare with entries in the result list
        //    If it overlaps with any entry, then merge it
        //    else add the input to the result list.

        Collections.sort(inputZipcodeRanges, Comparator.comparingInt(ZipcodeRange::getZipcodeStart));
        mergedZipcodeRanges.add(inputZipcodeRanges.get(0));
        IntStream
                .range(1, inputZipcodeRanges.size())
                .mapToObj(index -> inputZipcodeRanges.get(index))
                .forEachOrdered(input -> merge(mergedZipcodeRanges, input));

        return mergedZipcodeRanges;
    }

    /**
     * This method compare the input against each member of mergedZipcodeRanges
     * and merge it if it overlaps with any of the entry,
     * else add the input to mergedZipcodeRanges
     *
     * @param mergedZipcodeRanges
     * @param input
     */
    private static void merge(List<ZipcodeRange> mergedZipcodeRanges, ZipcodeRange input) {
//        boolean merged= mergedZipcodeRanges.stream()
//                .anyMatch(range -> mergeIfOverlaps(range, input));

        //Since the inputlist is sorted,
        // if the input cant be merged with the last element of the result, it can't be merged with any earlier element anyway.
        // so we need to check against the last element only.
        boolean merged = mergeIfOverlaps(getLastElement(mergedZipcodeRanges), input);

        if(!merged) {
            mergedZipcodeRanges.add(input);
        }
    }

    /**
     * return the last element of the list
     * @param list
     * @return
     */
    private static ZipcodeRange getLastElement(List<ZipcodeRange> list) {
        return list.get(list.size()-1);
    }

    /**
     *This method will merge two zipcode ranges if they overlaps and return true, else return false
     *         //Different overlap conditions for zip ranges
     *         //
     *         //                 |--------|                   : Zip code range1 (reference)
     *         //            |--------|                        : 1. range2 left overlaps (true)
     *         //   |----|                                     : 2. range2 dont overlap (falls left of range1) (false)
     *         //                      |---------|             : 3. range2 right overlap (true)
     *         //                                   |-------|  : 4. range2 doesn't overlap (falls fully right of range1) (false)
     *         //                    |---|                     : 5. range2 two is fully inside range 1 (true)
     *         //              |---------------|               : 6. range2 fully overlaps range1 (true)
     * @param range1
     * @param range2
     * @return true or false
     */
    public static boolean mergeIfOverlaps(ZipcodeRange range1, ZipcodeRange range2) {
        if (range2.getZipcodeStart() < range1.getZipcodeStart()) {  // 1, 2, 6
            if (range2.getZipcodeEnd() < range1.getZipcodeStart()) { //case 2
                return false;
            } else { //1 or 6
                if (range2.getZipcodeEnd() <= range1.getZipcodeEnd()) { //case 1
                    range1.setZipcodeStart(range2.getZipcodeStart());
                } else { //case 6
                    range1.setZipcodeStart(range2.getZipcodeStart());
                    range1.setZipcodeEnd(range2.getZipcodeEnd());
                }

            }
        } else if (range2.getZipcodeEnd() > range1.getZipcodeEnd()) { //3, 4
            if (range2.getZipcodeStart() > range1.getZipcodeEnd()) { //4
                return false;
            } else { //case 3
                range1.setZipcodeEnd(range2.getZipcodeEnd());
            }
        }

        return true; //case 5
    }

}
