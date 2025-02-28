package pl.magzik.dotoi.model;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;
import pl.magzik.dotoi.repository.ITaskRepository;
import pl.magzik.dotoi.repository.TaskRepository;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Represents a task entity.
 * <p>
 * This class holds all relevant information about a task, such as its title, description, content, hyperlinks, and timestamps.
 * It also supports optional attributes like a deadline and recurrence rule.
 * <p>
 * Instances of this class are immutable except for the {@code completed} flag.
 *
 * @see TaskRepository
 * @see ITaskRepository
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class Task implements Serializable {

    private final UUID id;
    private final String title;
    private final String description;
    private final String content;
    private final List<String> hyperlinks; ///< Applications
    private final LocalDateTime createdAt;
    private final LocalDateTime deadline;
    private final RecurrenceRule recurrenceRule;
    private boolean completed;

    @Contract(pure = true)
    private Task(@NotNull Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.content = builder.content;
        this.hyperlinks = List.copyOf(builder.hyperlinks);
        this.createdAt = builder.createdAt;
        this.deadline = builder.deadline;
        this.recurrenceRule = builder.recurrenceRule;
        this.completed = builder.completed;
    }

    public static class Builder {
        private UUID id;
        private final String title;
        private final String description;
        private final String content;
        private final List<String> hyperlinks;
        private final LocalDateTime createdAt;
        private LocalDateTime deadline;
        private RecurrenceRule recurrenceRule;
        private boolean completed;

        public Builder(@NotNull String title, @NotNull String description, @NotNull String content, @NotNull List<String> hyperlinks, @NotNull LocalDateTime createdAt) {
            this.id = UUID.randomUUID();
            if (title.trim().isEmpty()) throw new IllegalArgumentException("Title shouldn't be empty.");
            this.title = title;
            this.description = description;
            this.content = content;
            this.hyperlinks = hyperlinks;
            this.createdAt = createdAt;
            this.completed = false;
        }

        public @NotNull Builder id(@NotNull UUID id) {
            this.id = id;
            return this;
        }

        public @NotNull Builder deadline(@NotNull LocalDateTime deadline) {
            this.deadline = deadline;
            return this;
        }

        public @NotNull Builder recurrenceRule(@NotNull RecurrenceRule recurrenceRule) {
            this.recurrenceRule = recurrenceRule;
            return this;
        }

        public @NotNull Builder completed(boolean completed) {
            this.completed = completed;
            return this;
        }

        public @NotNull Task build() {
            return new Task(this);
        }
    }

    public @NotNull UUID getId() {
        return id;
    }
    public @NotNull String getTitle() {
        return title;
    }
    public @NotNull String getDescription() {
        return description;
    }
    public @NotNull String getContent() {
        return content;
    }
    public @NotNull List<String> getHyperlinks() {
        return hyperlinks;
    }
    public @NotNull LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public Optional<LocalDateTime> getDeadline() {
        return Optional.ofNullable(deadline);
    }
    public Optional<RecurrenceRule> getRecurrenceRule() {
        return Optional.ofNullable(recurrenceRule);
    }
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Marks the task as completed and emits an event.
     */
    public void complete() {
        if (!completed) {
            completed = true;
            DataManager.getInstance().notifySubscribers(new DataEvent.TaskCompleted(this));
        }
    }
    /**
     * Marks the task as uncompleted and emits an event.
     * */
    public void uncomplete() {
        if (completed) {
            completed = false;
            DataManager.getInstance().notifySubscribers(new DataEvent.TaskUncompleted(this));
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Task.class.getSimpleName() + "[", "]")
                .add("title='" + title + "'")
                .add("description='" + description + "'")
                .add("content='" + content + "'")
                .add("hyperlinks=" + hyperlinks)
                .add("createdAt=" + createdAt)
                .add("deadline=" + deadline)
                .add("recurrenceRule=" + recurrenceRule)
                .add("completed=" + completed)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
