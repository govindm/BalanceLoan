package com.affirm.loan.common;

public class Assignment {
    private final Long loanId;
    private final Long facilityId;
    public Assignment(Long loanId, Long facilityId) {
        this.loanId = loanId;
        this.facilityId = facilityId;
    }
    public String[] convertToStringArray(){
        return new String[]{loanId.toString(), facilityId.toString()};
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "loanId=" + loanId +
                ", facilityId=" + facilityId +
                '}';
    }
}