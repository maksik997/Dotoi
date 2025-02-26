package pl.magzik.dotoi.view;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import pl.magzik.dotoi.controller.TaskController;
import pl.magzik.dotoi.model.Task;

import java.io.IOException;

public class TaskWindow extends Window {

    private static final String FXML = "/fxml/task-view.fxml";

    private final Task task;

    public TaskWindow(Task task) {
        super(FXML);
        this.task = task;
    }

    public TaskWindow() {
        this(null);
    }

    @Override
    public void start(@NotNull Stage stage) throws IOException {
        super.start(stage);
        if (super.controller instanceof TaskController taskController) {
            taskController.setTask(task);
        }
    }
}
