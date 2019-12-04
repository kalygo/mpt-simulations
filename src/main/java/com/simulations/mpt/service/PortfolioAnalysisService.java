package com.simulations.mpt.service;


import com.simulations.mpt.entity.PortfolioAnalysisResult;
import com.simulations.mpt.entity.PortfolioVariables;
import com.simulations.mpt.entity.PortfolioAnalysisRequest;
import com.simulations.mpt.entity.YearlyAnalysisResult;
import com.simulations.mpt.framework.*;
import com.simulations.mpt.framework.TaskFactories.PortfolioAnalyzerTaskFactory;
import com.simulations.mpt.framework.Assemblers.PortfolioAnalysisResultsAccumulator;
import com.simulations.mpt.framework.TaskParametersSuppliers.ListBasedTaskParametersSupplier;
import com.simulations.mpt.utils.ReturnRateSuppliers.GaussianRandomNumberSupplier;
import com.simulations.mpt.utils.ReturnRateSuppliers.ReturnRateSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.BlockingQueue;

/***
 * Used for analyzing the different portfolios by simulating the portfolio projections over the next few years
 * and selecting the best, worst and median cases
 *
 */
@Service
public class PortfolioAnalysisService {

    private static Logger logger = LoggerFactory.getLogger(PortfolioAnalysisService.class);

    public List<PortfolioAnalysisResult> analyze(PortfolioAnalysisRequest input){

        Supplier<TaskInputParameters> taskInputSupplier = new ListBasedTaskParametersSupplier(createIndividualTaskInputs(input));
        TaskManager<PortfolioAnalysisResult, BlockingQueue<PortfolioAnalysisResult>> portfolioAnalyzer = new TaskManager<>(new PortfolioAnalyzerTaskFactory(), taskInputSupplier);
        PortfolioAnalysisResultsAccumulator accumulator = new PortfolioAnalysisResultsAccumulator(portfolioAnalyzer);

        List<PortfolioAnalysisResult> accumulatedOutput = accumulator.execute();
        logger.info("portfolio analysis result -> "+accumulatedOutput);
        return accumulatedOutput;
    }

    /***
     * Uses the list of PortfolioVariables in the PortfolioAnalysisRequest instance to prepare the input for the simulations of each portfolio
     *
     * @param input
     * @return
     */
    private List<TaskInputParameters> createIndividualTaskInputs(PortfolioAnalysisRequest input) {
        List<TaskInputParameters> portfolioAnalysisTaskParameters = new LinkedList<>();

        for(PortfolioVariables v : input.getPortfolios()) {
            ReturnRateSupplier<Double> returnRateSupplier = new GaussianRandomNumberSupplier(v.getMeanReturnRate(), v.getStandardDeviation());
            portfolioAnalysisTaskParameters.add(new TaskInputParameters(
                    v.getName(),
                    input.getIntialAmount(),
                    returnRateSupplier,
                    input.getNumberOfyears(), input.getInflationRate(),
                    input.getNumberOfSimulations()));

        }

        return portfolioAnalysisTaskParameters;
    }


}
