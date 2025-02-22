package pl.magzik.dotoi.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;
import pl.magzik.dotoi.manager.data.IDataSubscriber;
import pl.magzik.dotoi.model.Task;

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

        taskColumn.setCellFactory(param -> new TableCell<>() {
            /*
                TODO: Move it to separate class
            */
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    Task task = getTableRow().getItem();
                    if (task == null) return;
                    VBox box = new VBox();

                    Label date = new Label(task.getCreatedAt().toLocalDate().toString());
                    date.setStyle("-fx-font-size: 10px;");
                    Label title = new Label(task.getTitle());
                    title.setStyle("-fx-font-weight: bold");
                    Label description = new Label(task.getDescription());
                    description.setStyle("-fx-font-size: 10px;");

                    box.getChildren().addAll(date, title, description);
                    setGraphic(box);
                }
            }
        });

        /* TODO: THESE TO BELOW WILL SURELY GO AS ONE CLASS, RIGHT NOW THERE IS A LOT OF REDUNDANT CODE - ON PURPOSE */
        editButtonColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    Task task = getTableRow().getItem();
                    Button button = new Button("TEDIT");
                    button.setOnAction(event -> {
                        log.debug("Open a new window with this task");
                    });
                    setGraphic(button);
                }
            }
        });

        deleteButtonColumn.setCellFactory(param -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) setGraphic(null);
                else {
                    Task task = getTableRow().getItem();
                    Button button = new Button("TDELET");
                    button.setOnAction(event -> {
                        log.debug("Emit an event to delete this task.");
                    });
                    setGraphic(button);
                }
            }
        });

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