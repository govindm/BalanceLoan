package com.affirm.loan.common;

import java.util.HashSet;
import java.util.Set;

public class Covenants {
    private Float maxDefaultLikelihood;
    private Set<String> bannedState;

    public Covenants(Float maxDefaultLikelihood){
        this.maxDefaultLikelihood = maxDefaultLikelihood;
        bannedState = new HashSet<>();
    }
    public Float getMaxDefaultLikelihood() {
        return maxDefaultLikelihood;
    }
    public void setMaxDefaultLikelihood(Float maxDefaultLikelihood) {
        this.maxDefaultLikelihood = maxDefaultLikelihood;
    }
    public Set<String> getBannedState() {
        return bannedState;
    }
    public void addBandedState(String state){
        bannedState.add(state);
    }

    public boolean isSatisfied(float defaultLikelihood, String state) {
        return this.maxDefaultLikelihood >= defaultLikelihood && !bannedState.contains(state);
    }
    @Override
    public String toString() {
        return "Covenants{" +
                "maxDefaultLikelihood=" + maxDefaultLikelihood +
                ", bannedState=" + bannedState +
                '}';
    }

}
