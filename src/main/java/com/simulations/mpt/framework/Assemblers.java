package com.simulations.mpt.framework;

import com.simulations.mpt.entity.PortfolioAnalysisResult;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

        private Supplier<Double> distributionSupplier;
        private TaskInputParameters porfolioProperties;

        public PerformancePercentileCalculator(TaskInputParameters porfolioProperties, Supplier<Double> distributionSupplier){
            this.distributionSupplier = distributionSupplier;
            this.porfolioProperties=porfolioProperties;
        }
        @Override
        public PortfolioAnalysisResult execute() {
            Set<Double> sortedDistributionsSet = new TreeSet<>();
            try {
                distributionSupplier.initialize();
                for (int i = 0; i < distributionSupplier.size(); i++) {
                    sortedDistributionsSet.add(distributionSupplier.get());
                }
            } finally {
                distributionSupplier.close();
            }
            Double[] sortedDistributionsArray = sortedDistributionsSet.toArray(new Double[sortedDistributionsSet.size()]);

            PortfolioAnalysisResult output = new PortfolioAnalysisResult();
            output.setPortfolioName(porfolioProperties.getPortfolioName());
            output.setMedian(getPercentileValue(sortedDistributionsArray, 50));
            output.setBestCase(getPercentileValue(sortedDistributionsArray, 90));
            output.setWorstCase(getPercentileValue(sortedDistributionsArray, 10));
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
