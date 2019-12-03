package com.simulations.mpt.utils;

import com.simulations.mpt.framework.Supplier;

import java.util.Random;

/***
 * This class encloses different types of implementations for ReturnRateSupplier
 */
public class ReturnRateSuppliers {
    public interface ReturnRateSupplier<T> extends Supplier<T> {
    }


    /***
     * This implementation supplies random return rates to ensure a Gaussian distribution based on a mean and standard deviation value
     */
    public static class GaussianRandomNumberSupplier implements ReturnRateSupplier<Double> {
        private Double mean;
        private Double sd;
        private Random random;

        public GaussianRandomNumberSupplier(Double mean, Double sd){
            this.mean=mean;
            this.sd=sd;
            this.random=new Random();
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
            //unlimited - limited only by value of integer
            return Integer.MAX_VALUE;
        }

        @Override
        public Double get() {
            //TODO - confirm whether this is the right way to determine the return rate
            return mean + (sd * random.nextGaussian());
        }
    }
}
