package com.affirm.loan.common;

import java.util.Objects;

public class Loan {
    private Long id;
    private Integer amount;
    private float defaultLikelihood;
    private float interestRate;
    private String state;

    public Loan(Long id, Integer amount, float defaultLikelihood, float interestRate, String state) {
        this.id = id;
        this.amount = amount;
        this.defaultLikelihood = defaultLikelihood;
        this.interestRate = interestRate;
        this.state = state;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public float getDefaultLikelihood() {
        return defaultLikelihood;
    }
    public void setDefaultLikelihood(float defaultLikelihood) {
        this.defaultLikelihood = defaultLikelihood;
    }
    public float getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(float interestRate) {
        this.interestRate = interestRate;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Float.compare(loan.defaultLikelihood, defaultLikelihood) == 0
                && Float.compare(loan.interestRate, interestRate) == 0
                && id.equals(loan.id) && amount.equals(loan.amount)
                && state.equals(loan.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", amount=" + amount +
                ", defaultLikelihood=" + defaultLikelihood +
                ", interestRate=" + interestRate +
                ", state='" + state + '\'' +
                '}';
    }
}
