package com.simulations.mpt.framework;

import com.simulations.mpt.framework.TaskFactories.TaskFactory;
import com.simulations.mpt.utils.ExecutorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 * This is a multi-threaded task manager which accepts a TaskFactory and TaskParameterSupplier as input.
 * The TaskParameterSupplier supplies the set of parameters necessary for instantiating the set of runnable tasks.
 * The TaskFactory produces individual runnable Tasks using the parameters supplied by the above TaskParameterSupplier
 * The Tasks produced by TaskFactory are expected to accept a taskOutputQueue (BlockingQueue<T>) and use it for output collection
 *
 * The task manager instance should be initialized explicitly by the client using initialize() method.
 * Once initialized, the task manager creates new runnable task instances using a single threaded daemon executorService - 'mainDaemonExecutor'
 * The newly created tasks are then executed using a multi-threaded fixed pool executorService - 'supplementaryExecutor'
 *
 * The results from the executions are then asynchronously collected in the taskOutputQueue in the order of task completions
 * The clients should use the get() method to poll the output values from the internal taskOutputQueue
 * The number of get() calls by the client should be limited to the value provided by the size(), which corresponds to the number of tasks created.
 * Calling close() method after collecting the results, will ensure that the executorServices are shutdown properly
 *
 * @param <T> type of the output in the queue
 * @param <Q> type of the output queue
 */
public class TaskManager<T, Q extends BlockingQueue<T>> implements Supplier<T>, Closeable {

    private static Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private TaskFactory<Q> taskFactory;
    private Supplier<TaskInputParameters> taskParametersSupplier;
    private BlockingQueue<T> taskOutputQueue;

    private ExecutorService supplementaryExecutor;
    private ExecutorService mainDaemonExecutor;
    private int numberOfParameters;

    public TaskManager(TaskFactory<Q> taskFactory, Supplier<TaskInputParameters> taskParametersSupplier){
        this.taskFactory=taskFactory;
        this.taskParametersSupplier = taskParametersSupplier;
        this.taskOutputQueue = new ArrayBlockingQueue<>(100);

        this.supplementaryExecutor = Executors.newFixedThreadPool(100);
        this.mainDaemonExecutor = Executors.newFixedThreadPool(1);
        this.numberOfParameters = taskParametersSupplier.size();
    }

    @Override
    public void initialize(){
        mainDaemonExecutor.execute(() -> {
                for(int i = 0; i < numberOfParameters; i++){
                    TaskInputParameters taskParams = taskParametersSupplier.get();
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
            logger.error("Exception occurred during processing - ",e);
            return null;
        }
    }

    @Override
    public int size(){
        return numberOfParameters;
    }
}
