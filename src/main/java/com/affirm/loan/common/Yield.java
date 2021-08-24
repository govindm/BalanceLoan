package com.affirm.loan.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Yield {
    private final Long facilityId;
    private  BigDecimal yield;

    public Yield(Long facilityId, BigDecimal yield) {
        this.facilityId = facilityId;
        this.yield = yield;
    }
    public Long getFacilityId() {
        return facilityId;
    }
    public BigDecimal getYield() {
        return yield;
    }
    public void addYield(BigDecimal yield){
        this.yield = this.yield.add(yield);
    }
    public String[] convertToStringArray(){
        return new String[]{facilityId.toString(), yield.setScale(2, RoundingMode.CEILING).toString()};
    }
    @Override
    public String toString() {
        return "Yield{" +
                "facilityId='" + facilityId + '\'' +
                ", yield=" + yield +
                '}';
    }
}
