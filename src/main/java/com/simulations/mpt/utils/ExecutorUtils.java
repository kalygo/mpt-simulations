package com.simulations.mpt.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


public class ExecutorUtils {
    /***
     * Shuts down the executorService after waiting for a second, for the submitted tasks to terminate after the first shutdown command is issued.
     *
     * @param executorService
     */
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
