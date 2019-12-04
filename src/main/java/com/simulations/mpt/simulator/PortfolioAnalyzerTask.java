package com.simulations.mpt.simulator;

import com.simulations.mpt.entity.PortfolioAnalysisResult;
import com.simulations.mpt.framework.*;
import com.simulations.mpt.entity.YearlyAnalysisResult;
import com.simulations.mpt.framework.Assemblers.PerformancePercentileCalculator;
import com.simulations.mpt.framework.TaskFactories.DistributionSupplierTaskFactory;
import com.simulations.mpt.framework.TaskParametersSuppliers.DistributionGenerationVariableSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/***
 * This is a runnable task that will accept the properties of one portfolio and publish the output of analysis to a analyzerOutputQueue.
 * This portfolio properties (or variables) are then used to run a fixed number of simulations with varying inputs, collect the distributions and calculate the percentiles.
 *
 */
public class PortfolioAnalyzerTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(PortfolioAnalyzerTask.class);

    private TaskInputParameters portfolioProperties;
    private BlockingQueue<PortfolioAnalysisResult> analyzerOutputQueue;

    public PortfolioAnalyzerTask(TaskInputParameters portfolioProperties, BlockingQueue<PortfolioAnalysisResult> analyzerOutputQueue) {
        this.portfolioProperties = portfolioProperties;
        this.analyzerOutputQueue = analyzerOutputQueue;
    }

    @Override
    public void run() {
        Supplier<TaskInputParameters> taskInputSupplier = new DistributionGenerationVariableSupplier(
                portfolioProperties.getInitialAmount(),
                portfolioProperties.getReturnRateSupplier(),
                portfolioProperties.getNumberOfYears(),
                portfolioProperties.getInflationRate(),
                portfolioProperties.getNumberOfSimulations());
        TaskManager<Map<Integer, Double>, BlockingQueue<Map<Integer, Double>>> distributionSupplier = new TaskManager<>(new DistributionSupplierTaskFactory(), taskInputSupplier);

        PerformancePercentileCalculator calculator = new PerformancePercentileCalculator(portfolioProperties, distributionSupplier);
        try {
            this.analyzerOutputQueue.put(calculator.execute());
        } catch (InterruptedException e) {
            logger.error("Exception occurred during processing - "+e);
        }
    }



}
