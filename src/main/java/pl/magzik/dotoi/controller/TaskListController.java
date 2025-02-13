package pl.magzik.dotoi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;

public class TaskListController {

    private static final Logger log = LoggerFactory.getLogger(TaskListController.class);

    public TaskListController() {
        DataManager.getInstance().subscribe(evt -> { // TODO: TEMP
            if (evt instanceof DataEvent.TaskAdded) {
                log.debug("Task has been added, and list has been notified.");
            }
        });
    }
}