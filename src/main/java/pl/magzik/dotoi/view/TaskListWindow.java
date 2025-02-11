package pl.magzik.dotoi.view;

import javafx.application.Application;
import org.jetbrains.annotations.NotNull;

public class TaskListWindow extends Window {

    private static final String FXML = "/fxml/task-list-view.fxml";

    public TaskListWindow() {
        super(FXML);
    }
}
