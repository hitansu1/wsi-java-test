package com.wsi.shipments.api;

import com.wsi.shipments.models.ZipcodeRange;

import java.util.List;

public interface ShippingService {

    /**
     * This method takes the user input list of zip code ranges,
     * merge them with each other if they overlaps
     * and returns the shorter list.
     *
     * @param inputZipcodeRanges List of zipcode ranges
     * @return List of merged zipcode ranges
     */
    List<ZipcodeRange> mergeZipCodes(List<ZipcodeRange> inputZipcodeRanges);
}
