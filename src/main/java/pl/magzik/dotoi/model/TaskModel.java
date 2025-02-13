package pl.magzik.dotoi.model;

import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;

public class TaskModel {

    public TaskModel() {
        DataManager.getInstance().notifySubscribers(new DataEvent.TaskAdded());
    }
}
