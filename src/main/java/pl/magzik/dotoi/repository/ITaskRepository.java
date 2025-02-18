package pl.magzik.dotoi.repository;

import org.jetbrains.annotations.NotNull;
import pl.magzik.dotoi.model.Task;

import java.util.List;

/**
 * Interface defining the necessary CRUD operations for any task repository implementation.
 * <p>
 * Classes implementing this interface should enforce appropriate data validation.
 * This interface does not impose any specific type of data storage, allowing flexibility in implementation.
 * It is extensively used in {@link pl.magzik.dotoi.service.TaskService} to manage task-related operations.
 * </p>
 *
 * @see pl.magzik.dotoi.service.TaskService
 * @see Task
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public interface ITaskRepository {
    /**
     * Saves the given task in the repository.
     * If the task already exists, the implementation should handle updates accordingly.
     *
     * @param task The {@link Task} object to be saved.
     */
    void save(@NotNull Task task);
    /**
     * Deletes the given task from the repository.
     * If the task does not exist, the implementation should handle it gracefully.
     *
     * @param task The {@link Task} object to be deleted.
     */
    void delete(@NotNull Task task);
    /**
     * Retrieves all tasks stored in the repository.
     *
     * @return A {@link List} containing all stored {@link Task} objects.
     */
    @NotNull List<Task> findAll();
}
