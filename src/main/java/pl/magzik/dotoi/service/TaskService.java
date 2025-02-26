package pl.magzik.dotoi.service;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;
import pl.magzik.dotoi.manager.data.IDataSubscriber;
import pl.magzik.dotoi.model.Task;
import pl.magzik.dotoi.repository.ITaskRepository;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A service that acts as a bridge between the task repository and the event bus.
 * <p>
 * This service listens for {@link DataEvent} updates and processes them accordingly,
 * ensuring that task-related operations are executed in response to system-wide events.
 * </p>
 *
 * <p>
 * When initialized, the service automatically subscribes to the {@link DataManager},
 * allowing it to react to various events, such as adding, deleting, updating, and requesting tasks.
 * </p>
 *
 * <p>
 * Logged events:
 * <ul>
 *     <li>When a task is added or removed from the repository.</li>
 *     <li>When a task update occurs (deletes the old task and saves the updated one).</li>
 *     <li>When a task fetch request is received.</li>
 * </ul>
 * </p>
 *
 * Please note that this class does not unsubscribe when destroyed,
 * so it is the user's responsibility to do so.
 *
 * @see DataManager
 * @see DataEvent
 * @see ITaskRepository
 * @see IDataSubscriber
 * @see pl.magzik.dotoi.model.Task
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class TaskService implements IDataSubscriber {

    private static final Logger log = LoggerFactory.getLogger(TaskService.class);

    private final ITaskRepository taskRepository;

    public TaskService(@NotNull ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        DataManager.getInstance().subscribe(this);
        log.info("Task service initialized.");
    }

    @Override
    public void onDataUpdate(@NotNull DataEvent event) {
        log.debug("Service has received an event: {}.", event);
        switch (event) {
            case DataEvent.RequestTasks ignored -> DataManager.getInstance()
                                                              .notifySubscribers(new DataEvent.TasksFetched(taskRepository.findAll()));
            case DataEvent.TaskAdded taskAdded -> {
                log.info("Adding new task to the repository.");
                taskRepository.save(taskAdded.task());
            }
            case DataEvent.TaskDeleted taskDeleted -> {
                log.info("Removing a task from the repository.");
                taskRepository.delete(taskDeleted.task());
            }
            /* TODO: ADD HANDLING OF TASK_COMPLETED/TASK_UNCOMPLETED EVENTS */
            case DataEvent.TaskUpdate taskUpdate -> {
                /*
                * Please note: Task is compared to another task by its id.
                *              Which remains unchanged.
                * */
                log.info("Updating a task in repository.");
                taskRepository.delete(taskUpdate.task());
                taskRepository.save(taskUpdate.task());
            }
            case DataEvent.CheckRecurrence ignored -> {
                log.info("Performing recurrence tasks check.");
                LocalDateTime now = LocalDateTime.now();
                performCheck(
                    t -> t.getRecurrenceRule().isPresent(),
                    t -> t.getRecurrenceRule().get().shouldRepeat(now),
                    t -> {
                        t.uncomplete();
                        DataManager.getInstance().notifySubscribers(new DataEvent.TaskUpdate(t));
                    }
                );
            }
            case DataEvent.CheckDeadlines ignored -> {
                log.info("Performing deadline check.");
                LocalDateTime now = LocalDateTime.now();
                performCheck(
                    t -> t.getDeadline().isPresent(),
                    t -> t.getDeadline().get().isBefore(now),
                    t -> {
                        log.warn("Task {} is overdue!", t.getTitle());
                        DataManager.getInstance().notifySubscribers(new DataEvent.TaskOverdue(t));
                    }
                );
            }
            default -> {}
        }
    }

    /**
     * Performs a check on tasks in the repository based on the specified predicates and action.
     * <p>
     * This method iterates through all tasks in the repository, filtering tasks based on two conditions:
     * one defined by the {@code what} predicate (e.g., tasks that have a deadline) and another defined by
     * the {@code when} predicate (e.g., tasks that are overdue). The tasks that satisfy both conditions
     * will have the specified {@code check} action applied to them (e.g., logging a warning, notifying subscribers).
     * </p>
     *
     * @param what A {@link Predicate} defining the first condition that a task must satisfy (e.g., having a deadline).
     * @param when A {@link Predicate} defining the second condition that a task must satisfy (e.g., being overdue).
     * @param check A {@link Consumer} defining the action to perform on each task that satisfies both conditions.
     *
     * @see Predicate
     * @see Consumer
     * @since 0.1
     */
    private void performCheck(Predicate<Task> what, Predicate<Task> when, Consumer<Task> check) {
        taskRepository.findAll()
                      .stream()
                      .filter(what)
                      .filter(Predicate.not(Task::isCompleted))
                      .filter(when)
                      .forEach(check);
    }
}
