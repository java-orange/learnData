线程池的单例模式



```java

public class ConnectionExecutor {
    private int corePoolSize = 8;
    private int maxPoolSize = 32;

    /**
     * TODO
     * the capacity of the work queue, maxPoolSize won't take effect unless queued task is
     * larger than WorkQueueCapacity
     */
    private int WorkQueueCapacity = 10000;

    private ExecutorService executor;

    private static class ConnectionExecutorHolder {
        private static ConnectionExecutor instance = new ConnectionExecutor();
    }

    private ConnectionExecutor() {
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(WorkQueueCapacity));
    }

    public static ConnectionExecutor inst() {
        return ConnectionExecutorHolder.instance;
    }

    public ExecutorService getExecutor() {
        return executor;
    }
}

```

