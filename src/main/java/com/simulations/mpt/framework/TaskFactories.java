package com.simulations.mpt.framework;

import com.simulations.mpt.simulator.DistributionGeneratorTask;
import com.simulations.mpt.entity.PortfolioAnalysisResult;
import com.simulations.mpt.simulator.PortfolioAnalysisTask;

import java.util.concurrent.BlockingQueue;

public class TaskFactories {

    public interface TaskFactory<Q> {
        Runnable createTask(TaskParameters taskParameters, Q outputQueue);
    }

    public static class DistributionsGeneratorTaskFactory implements TaskFactory<BlockingQueue<Double>> {
        @Override
        public Runnable createTask(TaskParameters taskParameters, BlockingQueue<Double> outputQueue) {
            return new DistributionGeneratorTask(taskParameters, outputQueue);
        }
    }


    public static class PortfolioAnalysisTaskFactory implements TaskFactory<BlockingQueue<PortfolioAnalysisResult>> {
        @Override
        public Runnable createTask(TaskParameters taskParameters, BlockingQueue<PortfolioAnalysisResult> outputQueue) {
            return new PortfolioAnalysisTask(taskParameters, outputQueue);
        }
    }

}
