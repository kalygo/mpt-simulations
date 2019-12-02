package com.simulations.mpt.simulator;

import com.simulations.mpt.framework.*;
import com.simulations.mpt.entity.PortfolioAnalysisResult;
import com.simulations.mpt.framework.TaskFactories.DistributionsGeneratorTaskFactory;
import com.simulations.mpt.framework.TaskParametersSuppliers.DistributionsGenerationTaskParametersSupplier;

import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;

public class PortfolioAnalysisTask implements Runnable {

    private TaskParameters portfolioAnalysisParams;
    private BlockingQueue<PortfolioAnalysisResult> analysisOutputQueue;

    public PortfolioAnalysisTask(TaskParameters portfolioAnalysisParams, BlockingQueue<PortfolioAnalysisResult> analysisOutputQueue) {
        this.portfolioAnalysisParams = portfolioAnalysisParams;
        this.analysisOutputQueue = analysisOutputQueue;
    }

    @Override
    public void run() {
        Supplier<TaskParameters> taskParamSupplier = new DistributionsGenerationTaskParametersSupplier(
                portfolioAnalysisParams.getInitialAmount(),
                portfolioAnalysisParams.getReturnRateSupplier(),
                portfolioAnalysisParams.getNumberOfYears(),
                portfolioAnalysisParams.getInflationRate(),
                portfolioAnalysisParams.getNumberOfSimulations());
        Supplier<Double> distributions = new TaskExecutor<>(new DistributionsGeneratorTaskFactory(), taskParamSupplier);

        distributions.initialize();
        Set<Double> sortedDistributionsSet = new TreeSet<>();
        for (int i = 0; i < distributions.size(); i++) {
            sortedDistributionsSet.add(distributions.get());
        }
        distributions.close();

        Double[] sortedDistributionsArray = sortedDistributionsSet.toArray(new Double[sortedDistributionsSet.size()]);

        PortfolioAnalysisResult output = new PortfolioAnalysisResult();
        output.setPortfolioName(portfolioAnalysisParams.getPortfolioName());
        output.setMedian(getPercentileValue(sortedDistributionsArray, 50).toString());
        output.setBestCase(getPercentileValue(sortedDistributionsArray, 90).toString());
        output.setWorstCase(getPercentileValue(sortedDistributionsArray, 10).toString());

        try {
            this.analysisOutputQueue.put(output);
        } catch (InterruptedException e) {
            //TODO - implement logger
            e.printStackTrace();
        }
    }

    private static Double getPercentileValue(Double[] valueArray, int percentile){
        int index = (int) (valueArray.length * ((double) percentile/100.0));
        return valueArray[index];
    }


}
