package com.simulations.mpt.framework;

import com.simulations.mpt.entity.PortfolioAnalysisResult;
import com.simulations.mpt.entity.YearlyAnalysisResult;

import java.util.*;

public class Assemblers {

    /***
     * Classes of this type is used for assembling.
     * Usually accumulating a stream of instances or joining several pieces of information about some thing
     * @param <A>
     */
    public interface Assembler<A> {
        A execute();
    }

    /***
     * Computes the median (50th percentile), best case (90the percentile) and the worst case (10th percentile) from stream of distributions
     * Assembles the results into an instance of PortfolioAnalysisResult
     *
     */
    public static class PerformancePercentileCalculator implements Assembler<PortfolioAnalysisResult> {

        private Supplier<Map<Integer, Double>> distributionSupplier;
        private TaskInputParameters porfolioProperties;

        public PerformancePercentileCalculator(TaskInputParameters porfolioProperties, Supplier<Map<Integer, Double>> distributionSupplier){
            this.distributionSupplier = distributionSupplier;
            this.porfolioProperties=porfolioProperties;
        }
        @Override
        public PortfolioAnalysisResult execute() {
            Map<Integer, Set<Double>> yearToSortedValues = new HashMap<>();
            try {
                distributionSupplier.initialize();
                for (int i = 0; i < distributionSupplier.size(); i++) {
                    Map<Integer, Double> yearToValueMap = distributionSupplier.get();
                    for(Map.Entry<Integer, Double> entry : yearToValueMap.entrySet()) {
                        if(!yearToSortedValues.containsKey(entry.getKey())){
                            yearToSortedValues.put(entry.getKey(), new TreeSet<>());
                        }
                        yearToSortedValues.get(entry.getKey()).add(entry.getValue());
                    }
                }
            } finally {
                distributionSupplier.close();
            }

            PortfolioAnalysisResult output = new PortfolioAnalysisResult();
            output.setPortfolioName(porfolioProperties.getPortfolioName());

            List<YearlyAnalysisResult> yearlyResults = new LinkedList<>();
            for(Map.Entry<Integer, Set<Double>> entry : yearToSortedValues.entrySet()){
                Integer yearNumber = entry.getKey();
                Set<Double> allDistributionsForTheYear = entry.getValue();

                Double[] sortedDistributionsArray = allDistributionsForTheYear.toArray(new Double[allDistributionsForTheYear.size()]);
                YearlyAnalysisResult yearlyResult = new YearlyAnalysisResult();
                yearlyResult.setYearNumber(yearNumber);
                yearlyResult.setMedian(getPercentileValue(sortedDistributionsArray, 50));
                yearlyResult.setBestCase(getPercentileValue(sortedDistributionsArray, 90));
                yearlyResult.setWorstCase(getPercentileValue(sortedDistributionsArray, 10));
                yearlyResults.add(yearlyResult);
            }
            output.setYearlyResults(yearlyResults);

            return output;
        }

        private static Double getPercentileValue(Double[] valueArray, int percentile){
            int index = (int) (valueArray.length * ((double) percentile/100.0));
            return valueArray[index];
        }

    }

    /***
     * Accumulates the instances of PortfolioAnalysisResult from a producer stream into a List<PortfolioAnalysisResult>
     */
    public static class PortfolioAnalysisResultsAccumulator implements Assembler<List<PortfolioAnalysisResult>> {

        private Supplier<PortfolioAnalysisResult> resultProducer;

        public PortfolioAnalysisResultsAccumulator(Supplier<PortfolioAnalysisResult> resultProducer){
            this.resultProducer = resultProducer;
        }

        @Override
        public List<PortfolioAnalysisResult> execute() {
            List<PortfolioAnalysisResult> outputList = new LinkedList<>();
            try {
                resultProducer.initialize();
                for (int i = 0; i < resultProducer.size(); i++) {
                    outputList.add(resultProducer.get());
                }
            } finally {
                resultProducer.close();
            }

            return outputList;
        }
    }
}
