package com.simulations.mpt.framework;

import com.simulations.mpt.framework.TaskFactories.TaskFactory;
import com.simulations.mpt.utils.ExecutorUtils;

import javax.annotation.PreDestroy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor<T, Q extends BlockingQueue<T>> implements Supplier<T> {

    private TaskFactory<Q> taskFactory;
    private Supplier<TaskParameters> taskParametersSupplier;
    private BlockingQueue<T> taskOutputQueue;

    private ExecutorService supplementaryExecutor;
    private ExecutorService mainDaemonExecutor;
    private int numberOfParameters;

    public TaskExecutor(TaskFactory<Q> taskFactory, Supplier<TaskParameters> taskParametersSupplier){
        this.taskFactory=taskFactory;
        this.taskParametersSupplier = taskParametersSupplier;
        this.taskOutputQueue = new ArrayBlockingQueue<>(1000);

        this.supplementaryExecutor = Executors.newFixedThreadPool(100);
        this.mainDaemonExecutor = Executors.newFixedThreadPool(1);
        this.numberOfParameters = taskParametersSupplier.size();
    }

    @Override
    public void initialize(){
        mainDaemonExecutor.execute(() -> {
                for(int i = 0; i < numberOfParameters; i++){
                    TaskParameters taskParams = taskParametersSupplier.get();
                    supplementaryExecutor.execute(taskFactory.createTask(taskParams, (Q) taskOutputQueue));
                }
            });
    }

    @PreDestroy
    @Override
    public void close(){
        ExecutorUtils.shutdown(supplementaryExecutor);
        ExecutorUtils.shutdown(mainDaemonExecutor);
    }

    @Override
    public T get() {

        try {
            return taskOutputQueue.take();
        } catch (InterruptedException e) {
            //TODO - implement logger instead of err out printing
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int size(){
        return numberOfParameters;
    }
}
