package com.simulations.mpt.entity;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * TODO: The validation criteria provided here are for demonstration purposes of validation.
 *
 */
public class PortfolioAnalysisRequest {

    @NotEmpty
    @Valid
    private List<PortfolioVariables> portfolios;

    @NotNull
    @Min(1)
    @Max(100 * 1000 * 1000 * 1000)
    private Double intialAmount;

    @NotNull
    @Min(0)
    @Max(100)
    private Double inflationRate;

    @NotNull
    @Min(1)
    @Max(1000)
    private Integer numberOfyears;

    @NotNull
    @Min(1)
    @Max(1000 * 1000)
    private Integer numberOfSimulations;

    public List<PortfolioVariables> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<PortfolioVariables> portfolios) {
        this.portfolios = portfolios;
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

    public Integer getNumberOfyears() {
        return numberOfyears;
    }

    public void setNumberOfyears(Integer numberOfyears) {
        this.numberOfyears = numberOfyears;
    }

    public Integer getNumberOfSimulations() {
        return numberOfSimulations;
    }

    public void setNumberOfSimulations(Integer numberOfSimulations) {
        this.numberOfSimulations = numberOfSimulations;
    }
}
