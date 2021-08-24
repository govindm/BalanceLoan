package com.affirm.loan.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Facility {
    private final Long id;
    private Integer amount;
    private final Long bankId;
    private float interestRate;
    private Covenants covenants;

    public Facility(Long id, Long bankId, Integer amount, float interestRate) {
        this.id = id;
        this.bankId = bankId;
        this.amount = amount;
        this.interestRate = interestRate;
    }

    public Long getId() {
        return id;
    }
    public Long getBankId() {
        return bankId;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public float getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }
    public Covenants getCovenants() {
        return covenants;
    }
    public void setCovenants(Covenants covenants) {
        this.covenants = covenants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return Float.compare(facility.interestRate, interestRate) == 0
                && id.equals(facility.id)
                && amount.equals(facility.amount)
                && bankId.equals(facility.bankId)
                && Objects.equals(covenants, facility.covenants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bankId);
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", amount=" + amount +
                ", bankId=" + bankId +
                ", interestRate=" + interestRate +
                ", covenants=" + covenants +
                '}';
    }
}
