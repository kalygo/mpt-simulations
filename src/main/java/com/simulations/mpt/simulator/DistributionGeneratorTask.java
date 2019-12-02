package com.simulations.mpt.simulator;

import com.simulations.mpt.framework.TaskParameters;
import com.simulations.mpt.utils.PortfolioValueProjectors;

import java.util.concurrent.BlockingQueue;

public class DistributionGeneratorTask implements Runnable {

    private TaskParameters distributionGeneratorParams;
    private BlockingQueue<Double> distributionOutputQueue;

    public DistributionGeneratorTask(TaskParameters distributionGeneratorParams, BlockingQueue<Double> distributionOutputQueue){
        this.distributionGeneratorParams = distributionGeneratorParams;
        this.distributionOutputQueue = distributionOutputQueue;
    }

    @Override
    public void run() {
        Double currentPortFolioValue = distributionGeneratorParams.getInitialAmount();
        for(int yearNumber = 0; yearNumber < distributionGeneratorParams.getNumberOfYears(); yearNumber++) {
            PortfolioValueProjectors.PortfolioValueProjector pvProjector = new PortfolioValueProjectors.InflationAdjustedPortfolioValueProjector(currentPortFolioValue, distributionGeneratorParams.getReturnRateSupplier().get(), distributionGeneratorParams.getInflationRate());
            currentPortFolioValue = pvProjector.evaluate();
        }
        //System.out.println("20th year -> "+currentPortFolioValue);
        try {
            distributionOutputQueue.put(currentPortFolioValue);
        } catch (InterruptedException e) {
            //TODO - implement logger
            e.printStackTrace();
        }
    }


}
