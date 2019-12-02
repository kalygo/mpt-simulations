package com.simulations.mpt.controller;

import com.simulations.mpt.service.PortfolioAnalysisService;
import com.simulations.mpt.entity.PortfolioAnalysisRequest;
import com.simulations.mpt.entity.PortfolioAnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class MptRestController {

    @Autowired
    private PortfolioAnalysisService performanceAnalysisService;

    @PutMapping("/mpt/analysis")
    public List<PortfolioAnalysisResult> analysisResults(@RequestBody PortfolioAnalysisRequest input){

        if(input.getPortfolios().isEmpty()) return new LinkedList<>();

        return performanceAnalysisService.analyze(input);
    }
}
