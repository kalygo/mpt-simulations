package com.simulations.mpt.entity;

import java.util.List;

public class PortfolioAnalysisRequest {

    private List<PortfolioVariables> portfolios;
    private Double intialAmount;
    private Double inflationRate;
    private int numberOfyears;
    private int numberOfSimulations;

    public List<PortfolioVariables> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<PortfolioVariables> portfolios) {
        this.portfolios = portfolios;
    }

    public int getNumberOfSimulations() {
        return numberOfSimulations;
    }

    public void setNumberOfSimulations(int numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
    }

    public Double getIntialAmount() {
        return intialAmount;
    }

    public void setIntialAmount(Double intialAmount) {
        this.intialAmount = intialAmount;
    }

    public Double getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(Double inflationRate) {
        this.inflationRate = inflationRate;
    }

    public int getNumberOfyears() {
        return numberOfyears;
    }

    public void setNumberOfyears(int numberOfyears) {
        this.numberOfyears = numberOfyears;
    }
}
