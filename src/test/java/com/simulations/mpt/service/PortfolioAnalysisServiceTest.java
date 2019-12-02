package com.simulations.mpt.service;

import com.simulations.mpt.entity.PortfolioAnalysisRequest;
import com.simulations.mpt.entity.PortfolioVariables;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class PortfolioAnalysisServiceTest {

    private PortfolioAnalysisService testService = new PortfolioAnalysisService();

    @Test
    void test_analyze() {
        PortfolioVariables p = new PortfolioVariables();
        p.setMeanReturnRate(new Double(9.4324));
        p.setStandardDeviation(new Double(15.675));
        p.setName("Aggressive");
        PortfolioAnalysisRequest input = new PortfolioAnalysisRequest();
        input.setInflationRate(3.5);
        input.setPortfolios(Arrays.asList(p));
        input.setIntialAmount(100000.00);
        input.setNumberOfyears(20);
        input.setNumberOfSimulations(200000);
        testService.analyze(input);
    }
}