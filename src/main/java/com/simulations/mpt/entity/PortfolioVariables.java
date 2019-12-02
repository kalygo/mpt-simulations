package com.simulations.mpt.entity;


public class PortfolioVariables {

    private String name;
    private Double meanReturnRate;
    private Double standardDeviation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMeanReturnRate() {
        return meanReturnRate;
    }

    public void setMeanReturnRate(Double meanReturnRate) {
        this.meanReturnRate = meanReturnRate;
    }

    public Double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }
}
