package com.simulations.mpt.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorUtils {
    public static void shutdown(ExecutorService executorService){
        if(executorService==null) return;

        try{
            executorService.shutdown();

            if(!executorService.awaitTermination(1, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }
        } catch (Exception e){
            //TODO log exception
        } finally {
            executorService.shutdownNow();
        }
    }
}
