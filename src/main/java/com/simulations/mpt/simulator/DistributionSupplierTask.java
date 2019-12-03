package com.simulations.mpt.simulator;

import com.simulations.mpt.framework.TaskInputParameters;
import com.simulations.mpt.utils.PortfolioValueProjectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/***
 * This is a runnable task that will accept a set of input parameter variables and publish the output to a distributionOutputQueue.
 * This input variables are used to run an individual simulation for generating a single distribution value.
 *
 * Details:
 * The task accepts the initialAmount and inflationRate and uses them to project a future projection of portfolio value
 * with a randomly varying return rate (supplied by the return rate generator) for fixed numberOfYears.
 * The result, which is the projected future portfolio value, is then added to the distributionOutputQueue
 *
 */
public class DistributionSupplierTask implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(DistributionSupplierTask.class);

    private TaskInputParameters distributionVariables;
    private BlockingQueue<Double> distributionOutputQueue;

    public DistributionSupplierTask(TaskInputParameters distributionVariables, BlockingQueue<Double> distributionOutputQueue){
        this.distributionVariables = distributionVariables;
        this.distributionOutputQueue = distributionOutputQueue;
    }

    @Override
    public void run() {
        Double currentPortFolioValue = distributionVariables.getInitialAmount();
        for(int yearNumber = 0; yearNumber < distributionVariables.getNumberOfYears(); yearNumber++) {
            PortfolioValueProjectors.PortfolioValueProjector pvProjector = new PortfolioValueProjectors.InflationAdjustedPortfolioValueProjector(currentPortFolioValue, distributionVariables.getReturnRateSupplier().get(), distributionVariables.getInflationRate());
            currentPortFolioValue = pvProjector.evaluate();
        }
        try {
            distributionOutputQueue.put(currentPortFolioValue);
        } catch (InterruptedException e) {
            logger.error("Exception occurred during processing - ",e);
        }
    }


}
