package com.simulations.mpt.entity;

import java.util.List;

public class PortfolioAnalysisResponse {
    private List<PortfolioAnalysisResult> results;

    public List<PortfolioAnalysisResult> getResults() {
        return results;
    }

    public void setResults(List<PortfolioAnalysisResult> results) {
        this.results = results;
    }
}
