package com.wsi.shipments.api;

import com.wsi.shipments.models.ZipcodeRange;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wsi.shipments.api.ShippingServiceImpl.*;
import static org.junit.jupiter.api.Assertions.*;

class ShippingServiceImplTest {

    private ShippingService shippingService = new ShippingServiceImpl();

    @Test
    public void mergeZipCodesTest_null() {

        //expected output
        ArrayList<ZipcodeRange> emptyArrayList = new ArrayList<>();

        //actual output
        List<ZipcodeRange> actualZipcodeRanges = shippingService.mergeZipCodes(null);

        //assert
        assertEquals(emptyArrayList, actualZipcodeRanges);
        assertEquals(0, actualZipcodeRanges.size());
    }

    @Test
    public void mergeZipCodesTest_empty() {

        //expected output
        ArrayList<ZipcodeRange> emptyArrayList = new ArrayList<>();

        //actual output
        List<ZipcodeRange> actualZipcodeRanges = shippingService.mergeZipCodes(emptyArrayList);

        //assert
        assertEquals(emptyArrayList, actualZipcodeRanges);
        assertEquals(0, actualZipcodeRanges.size());
    }

    @Test
    public void mergeZipCodesTest_single_input() {

        //input
        ZipcodeRange range1 = new ZipcodeRange(49679, 52015);
        List<ZipcodeRange> inputWithSingleEntry = Arrays.asList(range1);

        //expected output
        //same as input i.e. inputWithSingleEntry

        //actual output
        List<ZipcodeRange> actualZipcodeRanges = shippingService.mergeZipCodes(inputWithSingleEntry);

        //assert
        assertEquals(inputWithSingleEntry, actualZipcodeRanges);
        assertTrue(actualZipcodeRanges.contains(range1));
        assertEquals(1, actualZipcodeRanges.size());
    }

    @Test
    public void mergeZipCodesTest_withGivenInputInChallenge() {

        //input
        List<ZipcodeRange> inputZipcodeRangeList = Arrays.asList(
                new ZipcodeRange(49679, 52015),
                new ZipcodeRange(49800, 50000),
                new ZipcodeRange(51500, 53479),
                new ZipcodeRange(45012, 46937),
                new ZipcodeRange(54012, 59607),
                new ZipcodeRange(45500, 45590),
                new ZipcodeRange(45999, 47900),
                new ZipcodeRange(44000, 45000),
                new ZipcodeRange(43012, 45950)
        );

        //expected output
        ZipcodeRange zipcodeRange1 = new ZipcodeRange(43012, 47900);
        ZipcodeRange zipcodeRange2 = new ZipcodeRange(49679, 53479);
        ZipcodeRange zipcodeRange3 = new ZipcodeRange(54012, 59607);
        List<ZipcodeRange> expectedZipcodeRanges = Arrays.asList(zipcodeRange1, zipcodeRange2, zipcodeRange3);

        //actual output
        List<ZipcodeRange> actualZipcodeRanges = shippingService.mergeZipCodes(inputZipcodeRangeList);

        //assert
        assertEquals(expectedZipcodeRanges, actualZipcodeRanges);
        assertTrue(actualZipcodeRanges.contains(zipcodeRange1));
        assertTrue(actualZipcodeRanges.contains(zipcodeRange2));
        assertTrue(actualZipcodeRanges.contains(zipcodeRange3));
        assertEquals(expectedZipcodeRanges.size(), actualZipcodeRanges.size());
    }

    @Test
    public void mergeZipCodesTest_mergeOne() {
        //input
        List<ZipcodeRange> inputZipcodeRangeList = Arrays.asList(
                new ZipcodeRange(10000, 15000),
                new ZipcodeRange(18000, 20000),
                new ZipcodeRange(14000, 16000)
                );

        //expected output
        ZipcodeRange zipcodeRange1 = new ZipcodeRange(10000, 16000);
        ZipcodeRange zipcodeRange2 = new ZipcodeRange(18000, 20000);
        List<ZipcodeRange> expectedZipcodeRanges = Arrays.asList(zipcodeRange1, zipcodeRange2);

        //actual output
        List<ZipcodeRange> actualZipcodeRanges = shippingService.mergeZipCodes(inputZipcodeRangeList);

        //assert
        assertEquals(expectedZipcodeRanges, actualZipcodeRanges);
        assertTrue(actualZipcodeRanges.contains(zipcodeRange1));
        assertTrue(actualZipcodeRanges.contains(zipcodeRange2));
        assertEquals(expectedZipcodeRanges.size(), actualZipcodeRanges.size());
    }

    @Test
    public void mergeZipCodesTest_mergeNone() {
        //input
        List<ZipcodeRange> inputZipcodeRangeList = Arrays.asList(
                new ZipcodeRange(18000, 20000),
                new ZipcodeRange(10000, 15000),
                new ZipcodeRange(30000, 40000)
        );

        //actual output
        List<ZipcodeRange> actualZipcodeRanges = shippingService.mergeZipCodes(inputZipcodeRangeList);

        //assert
        assertEquals(3, actualZipcodeRanges.size());
    }

    @Test
    public void mergeIfOverlapsTest() {
        //Different overalap conditions
        //
        //           --------               : An output range (reference)
        //       --------                   : 1. left overlap
        //   ----                           : 2. left dont overlap (false)
        //               ---------          : 3. right overlap
        //                       -------    : 4. right dont overlap (false)
        //             ----                 : 5. inside // no action needed
        //         ---------------          : 6. fully overlap

        //case 1
        assertTrue(mergeIfOverlaps(new ZipcodeRange(10000, 20000),
                new ZipcodeRange(5000, 15000)));

        //case 2
        assertFalse(mergeIfOverlaps(new ZipcodeRange(10000, 20000),
                new ZipcodeRange(5000, 6000)));

        //case 3
        assertTrue(mergeIfOverlaps(new ZipcodeRange(10000, 20000),
                new ZipcodeRange(15000, 25000)));

        //case 4
        assertFalse(mergeIfOverlaps(new ZipcodeRange(10000, 20000),
                new ZipcodeRange(24000, 25000)));

        //case 5
        assertTrue(mergeIfOverlaps(new ZipcodeRange(10000, 20000),
                new ZipcodeRange(15000, 16000)));

        //case 6
        assertTrue(mergeIfOverlaps(new ZipcodeRange(10000, 20000),
                new ZipcodeRange(5000, 25000)));
    }

}