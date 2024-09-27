package com.simulations.mpt.entity;

public class YearlyAnalysisResult {

    private Integer yearNumber;
    private Double bestCase;
    private Double worstCase;
    private Double median;

    public Integer getYearNumber() {
        return yearNumber;
    }

    public void setYearNumber(Integer yearNumber) {
        this.yearNumber = yearNumber;
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
                "bestCase='" + bestCase + '\'' +
                ", worstCase='" + worstCase + '\'' +
                ", median='" + median + '\'' +
                '}';
    }
}
