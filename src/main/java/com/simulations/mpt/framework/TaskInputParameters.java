package com.simulations.mpt.framework;


import com.simulations.mpt.utils.ReturnRateSuppliers.ReturnRateSupplier;

/***
 * This is a generic properties class whose instances are used to store and transfer parameters into a runnable task created by the TaskFactory.
 * The instances need not have all the properties and will have only the necessary properties required for executing the runnable task.
 *
 */
public class TaskInputParameters {
    private String portfolioName;
    private Double initialAmount;
    private ReturnRateSupplier<Double> returnRateSupplier;
    private int numberOfYears;
    private Double inflationRate;
    private int numberOfSimulations;

    public TaskInputParameters(Double initialAmount, ReturnRateSupplier<Double> returnRateSupplier, int numberOfYears, Double inflationRate) {
        this.initialAmount = initialAmount;
        this.returnRateSupplier = returnRateSupplier;
        this.numberOfYears = numberOfYears;
        this.inflationRate = inflationRate;
    }

    public TaskInputParameters(String portfolioName, Double initialAmount, ReturnRateSupplier<Double> returnRateSupplier, int numberOfYears, Double inflationRate, int numberOfSimulations) {
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
