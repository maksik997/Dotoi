package pl.magzik.dotoi.view;

import pl.magzik.dotoi.model.TaskModel;

public class TaskWindow extends Window {

    private static final String FXML = "/fxml/task-view.fxml";

    public TaskWindow() {
        super(FXML);

        new TaskModel(); // TODO: TEMP
    }
}
