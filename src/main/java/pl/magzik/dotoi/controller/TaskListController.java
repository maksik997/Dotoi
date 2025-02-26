package pl.magzik.dotoi.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.WindowManager;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;
import pl.magzik.dotoi.manager.data.IDataSubscriber;
import pl.magzik.dotoi.model.Task;
import pl.magzik.dotoi.view.TaskWindow;
import pl.magzik.dotoi.view.table.ButtonTableCell;
import pl.magzik.dotoi.view.table.CheckBoxTableCell;
import pl.magzik.dotoi.view.table.TaskTableCell;

import java.time.LocalDateTime;
import java.util.List;

public class TaskListController implements IDataSubscriber {

    private static final Logger log = LoggerFactory.getLogger(TaskListController.class);

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, Boolean> completeColumn;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, String> editButtonColumn;

    @FXML
    private TableColumn<Task, String> deleteButtonColumn;

    public TaskListController() {
        /// TODO: TEMPORARY FOR DEVELOPMENT PURPOSES
        DataManager.getInstance().notifySubscribers(new DataEvent.TaskAdded(new Task.Builder("Test", "This is test taskThis is test taskThis is test taskThis is test taskThis is test task", "This is content of test task", List.of(), LocalDateTime.now()).build()));
    }

    @FXML
    private void initialize() {
        DataManager.getInstance().subscribe(this);
        DataManager.getInstance().notifySubscribers(new DataEvent.RequestTasks());

        completeColumn.setCellFactory(param -> new CheckBoxTableCell());
        taskColumn.setCellFactory(param -> new TaskTableCell());
        editButtonColumn.setCellFactory(param -> new ButtonTableCell("fas-edit", (e, t) -> {
            log.debug("Open a new window with this task {}.", t);
            WindowManager.getInstance().openWindow("general.title", new TaskWindow(t));
        }));
        deleteButtonColumn.setCellFactory(param -> new ButtonTableCell("fas-minus", (e, t) -> {
            ///< TODO: Show confirmation alert.
            log.debug("Received delete command on task {}. Emitting delete signal.", t);
            DataManager.getInstance().notifySubscribers(new DataEvent.TaskDeleted(t));
        }));
    }

    @FXML
    public void handleNewTaskButton() {
        WindowManager.getInstance().openWindow("task-editor.new-task.title", new TaskWindow());
    }

    @Override
    public void onDataUpdate(@NotNull DataEvent event) { // TODO: Could be changed...
        switch (event) {
            case DataEvent.TasksFetched(List<Task> tasks) -> {
                log.debug("Fetched {} tasks: \n{}", tasks.size(), tasks);
                Platform.runLater(() -> {
                    taskTable.getItems().setAll(tasks);
                });
            }
            case DataEvent.TaskCompleted ignored -> DataManager.getInstance().notifySubscribers(new DataEvent.RequestTasks());
            case DataEvent.TaskUncompleted ignored -> DataManager.getInstance().notifySubscribers(new DataEvent.RequestTasks());
            case DataEvent.TaskDeleted(Task task) -> {
                log.debug("Deleting {} task.", task);
                Platform.runLater(() -> taskTable.getItems().remove(task));
            }
            default -> {}
        }
    }
}