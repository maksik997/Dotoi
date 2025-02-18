/*TODO: START HERE NEXT*/

package pl.magzik.dotoi.service;

import pl.magzik.dotoi.repository.ITaskRepository;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class TaskSchedulerService {

    private final ScheduledExecutorService scheduler;
    private final ITaskRepository taskRepository;

    public TaskSchedulerService(ITaskRepository taskRepository) {
        this.scheduler = new ScheduledThreadPoolExecutor(1);
        this.taskRepository = taskRepository;
    }

    public void start() {

    }

    private void scheduleNextRecurrenceCheck() {

    }

    private void scheduleNextDeadlineCheck() {

    }
}
