package com.simulations.mpt.service;


import com.simulations.mpt.entity.PortfolioVariables;
import com.simulations.mpt.entity.PortfolioAnalysisRequest;
import com.simulations.mpt.entity.PortfolioAnalysisResult;
import com.simulations.mpt.framework.*;
import com.simulations.mpt.framework.TaskFactories.PortfolioAnalysisTaskFactory;
import com.simulations.mpt.framework.TaskParametersSuppliers.PortfolioAnalysisTaskParametersSupplier;
import com.simulations.mpt.utils.ReturnRateSuppliers.GaussianRandomNumberSupplier;
import com.simulations.mpt.utils.ReturnRateSuppliers.ReturnRateSupplier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioAnalysisService {

    public List<PortfolioAnalysisResult> analyze(PortfolioAnalysisRequest input){

        List<PortfolioAnalysisResult> outputList = new LinkedList<>();

        Supplier<TaskParameters> taskParamSupplier = new PortfolioAnalysisTaskParametersSupplier(createPortfolioAnalysisParameters(input));
        Supplier<PortfolioAnalysisResult> portfolioAnalysis =
                new TaskExecutor<>(new PortfolioAnalysisTaskFactory(), taskParamSupplier);

        portfolioAnalysis.initialize();
        for(int i=0;i<portfolioAnalysis.size();i++){
            outputList.add(portfolioAnalysis.get());
        }
        portfolioAnalysis.close();

        System.out.println(outputList.toString());

        return outputList;
    }

    private List<TaskParameters> createPortfolioAnalysisParameters(PortfolioAnalysisRequest input) {
        List<TaskParameters> portfolioAnalysisTaskParameters = new LinkedList<>();

        for(PortfolioVariables v : input.getPortfolios()) {
            ReturnRateSupplier<Double> returnRateSupplier = new GaussianRandomNumberSupplier(v.getMeanReturnRate(), v.getStandardDeviation());
            portfolioAnalysisTaskParameters.add(new TaskParameters(
                    v.getName(),
                    input.getIntialAmount(),
                    returnRateSupplier,
                    input.getNumberOfyears(), input.getInflationRate(),
                    input.getNumberOfSimulations()));

        }

        return portfolioAnalysisTaskParameters;
    }


}
