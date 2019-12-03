package com.simulations.mpt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simulations.mpt.entity.PortfolioAnalysisRequest;
import com.simulations.mpt.entity.PortfolioVariables;
import com.simulations.mpt.service.PortfolioAnalysisService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MptRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private PortfolioAnalysisService portfolioAnalysisService;

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void when_validRequest_responds200() throws Exception {
        Mockito.when(portfolioAnalysisService.analyze(any())).thenReturn(new LinkedList<>());

        PortfolioAnalysisRequest request = new PortfolioAnalysisRequest();
        request.setPortfolios(Arrays.asList(portfolioVariables("Aggressive", 15.0,16.5)));
        request.setInflationRate(3.5);
        request.setIntialAmount(100.0);
        request.setNumberOfSimulations(10);
        request.setNumberOfyears(15);

        String requestBody = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/analysis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk());

    }
    @Test
    public void when_missingPortfolioVariables_responds400() throws Exception {
        Mockito.when(portfolioAnalysisService.analyze(any())).thenReturn(new LinkedList<>());

        PortfolioAnalysisRequest request = new PortfolioAnalysisRequest();
        request.setPortfolios(null);
        request.setInflationRate(3.5);
        request.setIntialAmount(100.0);
        request.setNumberOfSimulations(10);
        request.setNumberOfyears(15);

        String requestBody = new ObjectMapper().writeValueAsString(request);
        mockMvc.perform(MockMvcRequestBuilders.put("/analysis")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());

    }



    private static PortfolioVariables portfolioVariables(String name, Double meanReturnRate, Double standardDeviation){
        PortfolioVariables portfolioVariables = new PortfolioVariables();
        portfolioVariables.setName(name);
        portfolioVariables.setMeanReturnRate(meanReturnRate);
        portfolioVariables.setStandardDeviation(standardDeviation);
        return portfolioVariables;
    }


}