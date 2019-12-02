package com.simulations.mpt.entity;

public class PortfolioAnalysisResult {

    private String portfolioName;
    private String bestCase;
    private String worstCase;
    private String median;

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getBestCase() {
        return bestCase;
    }

    public void setBestCase(String bestCase) {
        this.bestCase = bestCase;
    }

    public String getWorstCase() {
        return worstCase;
    }

    public void setWorstCase(String worstCase) {
        this.worstCase = worstCase;
    }

    public String getMedian() {
        return median;
    }

    public void setMedian(String median) {
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
