package pl.magzik.dotoi.manager.data;

import pl.magzik.dotoi.model.Task;

import java.util.List;

/**
 * A sealed interface representing different types of events that can occur.
 * <p>
 * This interface only allows specified implementations, such as {@link DataEvent.TaskAdded} and {@link DataEvent.TaskDeleted}.
 * <p>
 * Please note that different types of {@link DataEvent} are implemented as records.
 * Because of this, the context may vary: in one case, the context could be a task name, while in another, it could be the task itself.
 * <p>
 * This interface is extensively used by {@link DataManager} to manage the data workflow.
 *
 * @see DataManager
 * @see IDataSubscriber
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public sealed interface DataEvent permits DataEvent.TaskAdded, DataEvent.TaskDeleted, DataEvent.TaskUpdate,
        DataEvent.TaskOverdue, DataEvent.TaskCompleted, DataEvent.TaskUncompleted, DataEvent.RequestTasks,
        DataEvent.TasksFetched, DataEvent.CheckRecurrence, DataEvent.CheckDeadlines {
    // Basic tasks: (TODO: Could be changed)
    // Addition, Deletion, Update, Deadline, Completion

    record TaskAdded(Task task) implements DataEvent {}
    record TaskDeleted(Task task) implements DataEvent {}
    record TaskUpdate(Task task) implements DataEvent {}
    record TaskOverdue(Task task) implements DataEvent {}
    record TaskCompleted(Task task) implements DataEvent {}
    record TaskUncompleted(Task task) implements DataEvent {}
    record RequestTasks() implements DataEvent {}
    record TasksFetched(List<Task> tasks) implements DataEvent {}
    record CheckRecurrence() implements DataEvent {}
    record CheckDeadlines() implements DataEvent {}

}
