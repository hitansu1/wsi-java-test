package com.wsi.shipments.models;

import com.google.common.base.Objects;

public class ZipcodeRange {
    private Integer zipcodeStart;
    private Integer zipcodeEnd;

    public ZipcodeRange(Integer zipcodeStart, Integer zipcodeEnd) {
        this.zipcodeStart = zipcodeStart;
        this.zipcodeEnd = zipcodeEnd;
    }

    public Integer getZipcodeStart() {
        return zipcodeStart;
    }

    public void setZipcodeStart(Integer zipcodeStart) {
        this.zipcodeStart = zipcodeStart;
    }

    public Integer getZipcodeEnd() {
        return zipcodeEnd;
    }

    public void setZipcodeEnd(Integer zipcodeEnd) {
        this.zipcodeEnd = zipcodeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZipcodeRange that = (ZipcodeRange) o;
        return Objects.equal(zipcodeStart, that.zipcodeStart) &&
                Objects.equal(zipcodeEnd, that.zipcodeEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(zipcodeStart, zipcodeEnd);
    }

    @Override
    public String toString() {
        return com.google.common.base.MoreObjects.toStringHelper(this)
                .add("zipcodeStart", zipcodeStart)
                .add("zipcodeEnd", zipcodeEnd)
                .toString();
    }

}
