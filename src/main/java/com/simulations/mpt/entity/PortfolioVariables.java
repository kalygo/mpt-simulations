package com.simulations.mpt.entity;


import javax.validation.constraints.*;

/**
 * TODO: The validation criteria provided here are for demonstration purposes of validation.
 *
 */
public class PortfolioVariables {

    @NotNull
    @NotBlank
    @Size(min=1, max=100)
    private String name;

    @NotNull
    @Min(-100)
    @Max(100)
    private Double meanReturnRate;

    @NotNull
    @Min(-100)
    @Max(100)
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
