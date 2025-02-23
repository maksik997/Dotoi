package pl.magzik.dotoi.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;
import pl.magzik.dotoi.manager.data.IDataSubscriber;
import pl.magzik.dotoi.model.Task;
import pl.magzik.dotoi.view.table.ButtonTableCell;
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
        DataManager.getInstance().subscribe(this);
        DataManager.getInstance().notifySubscribers(new DataEvent.RequestTasks()); // TODO: TEMP
    }

    @FXML
    private void initialize() {
        completeColumn.setCellFactory(CheckBoxTableCell.forTableColumn(completeColumn));

        taskColumn.setCellFactory(param -> new TaskTableCell());
        editButtonColumn.setCellFactory(param -> new ButtonTableCell("TEDIT", (e, t) -> {
            log.debug("Open a new window with this task {}.", t);
        }));
        deleteButtonColumn.setCellFactory(param -> new ButtonTableCell("TDELET", (e, t) -> {
            log.debug("Emit an event to delete this task {}.", t);

        }));

        // TODO: TEMPORARY
        taskTable.setItems(FXCollections.observableArrayList(new Task.Builder("Test", "This is test taskThis is test taskThis is test taskThis is test taskThis is test task", "This is content of test task", List.of(), LocalDateTime.now()).build()));
    }

    @Override
    public void onDataUpdate(@NotNull DataEvent event) { // TODO: Could be changed...
        if (event instanceof DataEvent.TasksFetched(List<Task> tasks)) {
            log.debug("Fetched {} tasks: \n{}", tasks.size(), tasks);
            if (!tasks.isEmpty())
                tasks.getFirst().complete();
        }
    }
}