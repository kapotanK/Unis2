package com.kapot.unis2.ui.util;

import javafx.application.Platform;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadManager {

    private ExecutorService service;

    public ThreadManager() {
        this.service = Executors.newCachedThreadPool();
    }

    public Future<?> runParallel(Runnable runnable) {
        return service.submit(runnable);
    }

    public Future<?> runLater(Runnable runnable, long delayBeforeExecution) {
        return service.submit(() -> {
            try {
                Thread.sleep(delayBeforeExecution);
                Platform.runLater(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }


    public void shutdown() {
        service.shutdownNow();
    }

}
