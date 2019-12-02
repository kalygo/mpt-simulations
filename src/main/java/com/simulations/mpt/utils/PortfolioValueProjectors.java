package com.simulations.mpt.utils;

public class PortfolioValueProjectors {
    public interface PortfolioValueProjector {

        Double evaluate();
    }

    public static class InflationAdjustedPortfolioValueProjector implements PortfolioValueProjector {

        private Double currentPortfolioValue;
        private Double returnRate;
        private Double inflationRate;

        public InflationAdjustedPortfolioValueProjector(Double currentPortfolioValue, Double returnRate, Double inflationRate){
            this.currentPortfolioValue=currentPortfolioValue;
            this.returnRate=returnRate;
            this.inflationRate=inflationRate;
        }

        @Override
        public Double evaluate() {
            Double rate = (100.0 + returnRate)/100.0;
            Double inflation = (100 - inflationRate)/100.0;

            double result = currentPortfolioValue * rate * inflation;
            return result;
        }

    }
}
