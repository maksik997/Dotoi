package pl.magzik.dotoi.view;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class TaskListWindow extends Window {

    private static final String FXML = "/fxml/task-list-view.fxml";

    public TaskListWindow() {
        super(FXML);
    }

    @Override
    public void start(@NotNull Stage stage) throws IOException {
        super.start(stage);
        stage.setResizable(false);
    }
}
