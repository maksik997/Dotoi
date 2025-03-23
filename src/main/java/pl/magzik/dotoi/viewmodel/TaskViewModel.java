package pl.magzik.dotoi.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pl.magzik.dotoi.model.RecurrenceRule;
import pl.magzik.dotoi.model.Task;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Mutable wrapper for {@link Task} class.
 * Provides smooth properties for easy task handling in {@link pl.magzik.dotoi.controller.TaskController}.
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 * */
public class TaskViewModel {

    private final Task task;

    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty content;
    private final ObservableList<String> hyperlinks;
    private final ObjectProperty<LocalDateTime> deadline;
    private final ObjectProperty<RecurrenceRule> recurrenceRule;

    public TaskViewModel(Task task) {
        this.task = task;

        this.title = new SimpleStringProperty(task.getTitle());
        this.description = new SimpleStringProperty(task.getDescription());
        this.content = new SimpleStringProperty(task.getContent());
        this.hyperlinks = FXCollections.observableArrayList(List.copyOf(task.getHyperlinks()));
        this.deadline = new SimpleObjectProperty<>(task.getDeadline().orElse(null));
        this.recurrenceRule = new SimpleObjectProperty<>(task.getRecurrenceRule().orElse(null));
    }

    public Task toTask() {
        return new Task.Builder(title.get(), description.get(), content.get(), hyperlinks, task.getCreatedAt())
                .id(task.getId()) ///< Reference - Task is compared to others via ID.
                .deadline(deadline.get())
                .recurrenceRule(recurrenceRule.get())
                .completed(task.isCompleted()) ///< Immutable
                .build();
    }

    public String getId() {
        return task.getId().toString();
    }

    public LocalDateTime getCreatedAt() {
        return task.getCreatedAt();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public StringProperty contentProperty() {
        return content;
    }

    public ObservableList<String> getHyperlinks() {
        return hyperlinks; ///< This list should be modifiable.
    }

    public ObjectProperty<LocalDateTime> deadlineProperty() {
        return deadline;
    }

    public ObjectProperty<RecurrenceRule> recurrenceRuleProperty() {
        return recurrenceRule;
    }
}
