package com.simulations.mpt.utils;

/***
 * This class encloses the different types of PortfolioValueProjectors for projecting the future portfolio value
 */
public class PortfolioValueProjectors {
    public interface PortfolioValueProjector {

        Double evaluate();
    }

    /***
     * This is a PortfolioValueProjector which projects a future value of portfolio adjusted by the inflation rate
     */
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
            Double inflation = (100.0 - inflationRate)/100.0;

            double result = currentPortfolioValue * rate * inflation;
            return result;
        }

    }
}
