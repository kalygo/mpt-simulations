package com.simulations.mpt.controller;

import com.simulations.mpt.service.PortfolioAnalysisService;
import com.simulations.mpt.entity.PortfolioAnalysisRequest;
import com.simulations.mpt.entity.PortfolioAnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

/**
 * Controller class for rest based api for mpt-simulations application
 *
 */
@RestController
public class MptRestController {

    @Autowired
    private PortfolioAnalysisService portfolioAnalysisService;

    /**
     * Accepts and processes the requests with the portfolio details to be analyzed using the simulations.
     * After successful processing returns a list of results for each portfolio.
     *
     *
     * @param portfolioAnalysisRequest
     * @return
     */
    @PutMapping("/api/v1/analysis")
    public List<PortfolioAnalysisResult> analysisResults(@Valid @RequestBody PortfolioAnalysisRequest portfolioAnalysisRequest){

        if(portfolioAnalysisRequest.getPortfolios().isEmpty()) return new LinkedList<>();

        return portfolioAnalysisService.analyze(portfolioAnalysisRequest);
    }
}
