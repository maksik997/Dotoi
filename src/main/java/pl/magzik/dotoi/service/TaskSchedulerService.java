package pl.magzik.dotoi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A service responsible for scheduling periodic checks related to task data.
 * <p>
 *     This service periodically emits {@link DataEvent.CheckDeadlines} and {@link pl.magzik.dotoi.manager.data.DataEvent.CheckRecurrence} events
 *     to indicate when these checks should occur. These events are useful for tasks that require time-based operations, such as checking deadlines
 *     or handling task recurrences.
 * </p>
 * <p>
 *     The current implementation of this service is a simple one, using a scheduled executor to run checks every minute.
 *     However, this design can be enhanced in the future to handle different intervals or additional checks.
 * </p>
 *
 * @see TaskService
 * @see DataEvent.CheckDeadlines
 * @see DataEvent.CheckRecurrence
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class TaskSchedulerService {

    private static final Logger log = LoggerFactory.getLogger(TaskSchedulerService.class);

    private final ScheduledExecutorService scheduler;

    /**
     * Initializes the task scheduler service and starts the periodic checks.
     * <p>
     *     This constructor sets up the scheduler to emit {@link DataEvent.CheckDeadlines} and {@link pl.magzik.dotoi.manager.data.DataEvent.CheckRecurrence}
     *     events every minute. The initial delay before the first event is emitted is zero, meaning the events will start immediately.
     * </p>
     */
    public TaskSchedulerService() {
        this.scheduler = new ScheduledThreadPoolExecutor(1);
        log.info("Task scheduler service initialized.");

        scheduler.scheduleAtFixedRate(() -> {
            DataManager.getInstance().notifySubscribers(new DataEvent.CheckDeadlines());
            DataManager.getInstance().notifySubscribers(new DataEvent.CheckRecurrence());
        }, 0, 1, TimeUnit.MINUTES);
    }

    /**
     * Shuts down the scheduler service in a controlled manner.
     * <p>
     * The shutdown process follows these steps:
     * <ol>
     *     <li>Attempts a graceful shutdown.</li>
     *     <li>Waits up to 10 seconds for all scheduled tasks to complete.</li>
     *     <li>If tasks do not complete within the timeout, forces a shutdown.</li>
     * </ol>
     * <p>
     * If the shutdown is forced, any remaining unfinished tasks are logged.
     * If the thread is interrupted during shutdown, the interrupt flag is restored.
     * </p>
     *
     * <p><b>Note:</b> This method ensures that the thread's interrupt status is properly maintained if an {@link InterruptedException} occurs.</p>
     */
    public void shutdown() {
        scheduler.shutdown();
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                var pendingTasks = scheduler.shutdownNow();
                log.warn("{} was forced to shut down. {} tasks remain unfinished.", getClass().getSimpleName(), pendingTasks.size());
            } else {
                log.info("{} shut down gracefully.", getClass().getSimpleName());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); ///< Fix interrupt flag.
            log.error("{} has been interrupted during shutting down.", getClass().getSimpleName());
        }
    }
}
