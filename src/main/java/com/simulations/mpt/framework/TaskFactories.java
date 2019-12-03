package com.simulations.mpt.framework;

import com.simulations.mpt.entity.PortfolioAnalysisResult;
import com.simulations.mpt.simulator.DistributionSupplierTask;
import com.simulations.mpt.entity.YearlyAnalysisResult;
import com.simulations.mpt.simulator.PortfolioAnalyzerTask;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/***
 * This class encloses the TaskFactory classes used for creating different types of runnable tasks managed by the TaskManager
 *
 */
public class TaskFactories {

    /***
     * An interface through which TaskManager accepts the task creation function.
     * TaksFactory type classes creates tasks which uses TaskInputParameters for input and an outputQueue for collecting outputs.
     *
     * @param <Q>
     */
    public interface TaskFactory<Q> {
        Runnable createTask(TaskInputParameters taskParameters, Q outputQueue);
    }

    /***
     * This class creates a task which can compute and produce a single distribution value.
     * The value is collected in the outputQueue.
     *
     */
    public static class DistributionSupplierTaskFactory implements TaskFactory<BlockingQueue<Map<Integer, Double>>> {
        @Override
        public Runnable createTask(TaskInputParameters taskParameters, BlockingQueue<Map<Integer, Double>> outputQueue) {
            return new DistributionSupplierTask(taskParameters, outputQueue);
        }
    }

    /***
     * This class creates a task which can compute and produce the result of analysis on a specific portfolio.
     * The value is collected in the outputQueue.
     *
     */
    public static class PortfolioAnalyzerTaskFactory implements TaskFactory<BlockingQueue<PortfolioAnalysisResult>> {
        @Override
        public Runnable createTask(TaskInputParameters taskParameters, BlockingQueue<PortfolioAnalysisResult> outputQueue) {
            return new PortfolioAnalyzerTask(taskParameters, outputQueue);
        }
    }

}
