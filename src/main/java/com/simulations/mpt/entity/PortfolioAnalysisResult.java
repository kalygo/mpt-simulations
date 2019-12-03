package com.simulations.mpt.entity;

public class PortfolioAnalysisResult {

    private String portfolioName;
    private Double bestCase;
    private Double worstCase;
    private Double median;

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public Double getBestCase() {
        return bestCase;
    }

    public void setBestCase(Double bestCase) {
        this.bestCase = bestCase;
    }

    public Double getWorstCase() {
        return worstCase;
    }

    public void setWorstCase(Double worstCase) {
        this.worstCase = worstCase;
    }

    public Double getMedian() {
        return median;
    }

    public void setMedian(Double median) {
        this.median = median;
    }

    @Override
    public String toString() {
        return "PerformanceAnalysisResult{" +
                "portfolioName='" + portfolioName + '\'' +
                ", bestCase='" + bestCase + '\'' +
                ", worstCase='" + worstCase + '\'' +
                ", median='" + median + '\'' +
                '}';
    }
}
