package com.simulations.mpt.framework;


import com.simulations.mpt.utils.ReturnRateSuppliers.ReturnRateSupplier;

public class TaskParameters {
    private String portfolioName;
    private Double initialAmount;
    private ReturnRateSupplier<Double> returnRateSupplier;
    private int numberOfYears;
    private Double inflationRate;
    private int numberOfSimulations;

    public TaskParameters(Double initialAmount, ReturnRateSupplier<Double> returnRateSupplier, int numberOfYears, Double inflationRate) {
        this.initialAmount = initialAmount;
        this.returnRateSupplier = returnRateSupplier;
        this.numberOfYears = numberOfYears;
        this.inflationRate = inflationRate;
    }

    public TaskParameters(String portfolioName, Double initialAmount, ReturnRateSupplier<Double> returnRateSupplier, int numberOfYears, Double inflationRate, int numberOfSimulations) {
        this.portfolioName = portfolioName;
        this.initialAmount = initialAmount;
        this.returnRateSupplier = returnRateSupplier;
        this.numberOfYears = numberOfYears;
        this.inflationRate = inflationRate;
        this.numberOfSimulations = numberOfSimulations;
    }

    public Double getInitialAmount() {
        return initialAmount;
    }

    public ReturnRateSupplier<Double> getReturnRateSupplier() {
        return returnRateSupplier;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public Double getInflationRate() {
        return inflationRate;
    }

    public int getNumberOfSimulations() {
        return numberOfSimulations;
    }

    public String getPortfolioName() {
        return portfolioName;
    }
}
