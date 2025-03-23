package pl.magzik.dotoi.repository;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.model.Task;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of the {@link ITaskRepository} interface.
 * <p>
 * This class provides an in-memory task repository using a thread-safe {@link CopyOnWriteArrayList}.
 * It ensures that tasks are stored safely and allows concurrent access without requiring external synchronization.
 * </p>
 *
 * <p>
 * This repository logs operations such as adding, removing, and fetching tasks.
 * Duplicate tasks are not added, and attempts to remove non-existent tasks are logged as warnings.
 * </p>
 *
 * @see Task
 * @see ITaskRepository
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class TaskRepository implements ITaskRepository {

    private static final Logger log = LoggerFactory.getLogger(TaskRepository.class);

    private final List<Task> tasks;

    /**
     * Initializes the task repository.
     * Uses a thread-safe {@link CopyOnWriteArrayList} to store tasks.
     */
    public TaskRepository() {
        this.tasks = new CopyOnWriteArrayList<>();
        log.info("Task repository initialized.");
    }

    /**
     * Saves a new task in the repository.
     * <p>
     * If the task already exists, it is not added again, and a warning is logged.
     * </p>
     *
     * @param task The {@link Task} object to be saved.
     */
    @Override
    public void save(@NotNull Task task) {
        if (tasks.contains(task)) {
            log.warn("Attempted to add a redundant task: {}", task);
            return;
        }
        log.debug("Saving task {} in the repository.", task);
        tasks.add(task);
    }

    /**
     * Deletes a task from the repository.
     * <p>
     * If the task does not exist, a warning is logged.
     * </p>
     *
     * @param task The {@link Task} object to be deleted.
     */
    @Override
    public void delete(@NotNull Task task) {
        if (!tasks.contains(task)) {
            log.warn("Attempted to deleted a non-existing task: {}", task);
            return;
        }
        log.debug("Deleting task {} from the repository.", task);
        tasks.remove(task);
    }

    /**
     * Retrieves all tasks stored in the repository.
     *
     * @return An immutable {@link List} containing all stored {@link Task} objects.
     */
    @Override
    public @NotNull List<Task> findAll() {
        log.debug("Fetching all tasks from the repository.");
        return List.copyOf(tasks);
    }
}
