package pl.magzik.dotoi.view;

import pl.magzik.dotoi.repository.TaskRepository;

public class TaskWindow extends Window {

    private static final String FXML = "/fxml/task-view.fxml";

    public TaskWindow() {
        super(FXML);
    }
}
