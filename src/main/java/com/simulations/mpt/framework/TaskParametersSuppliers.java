package com.simulations.mpt.framework;


import com.simulations.mpt.utils.ReturnRateSuppliers;
import com.simulations.mpt.utils.ReturnRateSuppliers.ReturnRateSupplier;

import java.util.Iterator;
import java.util.List;

public class TaskParametersSuppliers {


    public static class PortfolioAnalysisTaskParametersSupplier implements Supplier<TaskParameters> {
        List<TaskParameters> taskParameters;
        Iterator<TaskParameters> taskParametersIterator;

        public PortfolioAnalysisTaskParametersSupplier(List<TaskParameters> taskParameters) {
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
        public TaskParameters get() {
            return taskParametersIterator.next();
        }
    }


    public static class DistributionsGenerationTaskParametersSupplier implements Supplier<TaskParameters> {

        private Double initialAmount;
        private ReturnRateSupplier<Double> returnRateGenerator;
        private int numberOfYears;
        private Double inflationRate;
        private int size;


        public DistributionsGenerationTaskParametersSupplier(Double initialAmount, ReturnRateSupplier<Double> returnRateGenerator, int numberOfYears, Double inflationRate, int size){
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
        public TaskParameters get() {
            return  new TaskParameters(initialAmount, returnRateGenerator, numberOfYears, inflationRate);
        }
    }
}
