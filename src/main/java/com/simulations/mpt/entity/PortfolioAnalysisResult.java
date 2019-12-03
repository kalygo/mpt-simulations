package com.simulations.mpt.entity;

import java.util.List;

public class PortfolioAnalysisResult {
    private String portfolioName;
    private List<YearlyAnalysisResult> yearlyResults;

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public List<YearlyAnalysisResult> getYearlyResults() {
        return yearlyResults;
    }

    public void setYearlyResults(List<YearlyAnalysisResult> yearlyResults) {
        this.yearlyResults = yearlyResults;
    }
}
