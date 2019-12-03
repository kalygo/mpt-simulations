package com.simulations.mpt.framework;


import com.simulations.mpt.utils.ReturnRateSuppliers.ReturnRateSupplier;

import java.util.Iterator;
import java.util.List;

/***
 * This class encloses different classes implementing Supplier<TaskInputParameters>.
 * These implementations can be used for supplying TaskInputParameters necessary for creating runnable tasks by a TaskFactory
 *
 *
 */
public class TaskParametersSuppliers {


    /***
     * This is a TaskParameterSupplier backed by a list internally and accepts an already fixed list of TaskInputParameters during construction
     *
     */
    public static class ListBasedTaskParametersSupplier implements Supplier<TaskInputParameters> {
        List<TaskInputParameters> taskParameters;
        Iterator<TaskInputParameters> taskParametersIterator;

        public ListBasedTaskParametersSupplier(List<TaskInputParameters> taskParameters) {
            this.taskParameters=taskParameters;
            this.taskParametersIterator=taskParameters.iterator();
        }

        @Override
        public void initialize() {
            taskParametersIterator=taskParameters.iterator();
        }

        @Override
        public void close() {
            taskParametersIterator=taskParameters.iterator();
        }

        @Override
        public int size() {
            return taskParameters.size();
        }

        @Override
        public TaskInputParameters get() {
            return taskParametersIterator.next();
        }
    }

    /***
     * This is a customized TaskParameterSupplier which will supply the set variable parameters required by each DistributionSupplierTask
     * for generating the distribution
     *
     * Note:
     * The returnRateSupplier supplies a different returnRate as per its implementation (a random value) and this makes the set of variables different from another set.
     * The TaskManager then uses these different set of variables to compute different distributions
     *
     */
    public static class DistributionGenerationVariableSupplier implements Supplier<TaskInputParameters> {

        private Double initialAmount;
        private ReturnRateSupplier<Double> returnRateGenerator;
        private int numberOfYears;
        private Double inflationRate;
        private int size;


        public DistributionGenerationVariableSupplier(Double initialAmount, ReturnRateSupplier<Double> returnRateGenerator, int numberOfYears, Double inflationRate, int size){
            this.initialAmount =initialAmount;
            this.returnRateGenerator=returnRateGenerator;
            this.numberOfYears=numberOfYears;
            this.inflationRate=inflationRate;
            this.size=size;
        }

        @Override
        public void initialize() {
            //noop
        }

        @Override
        public void close() {
            //noop
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public TaskInputParameters get() {
            return  new TaskInputParameters(initialAmount, returnRateGenerator, numberOfYears, inflationRate);
        }
    }
}
