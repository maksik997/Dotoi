package pl.magzik.dotoi.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.IDataSubscriber;
import pl.magzik.dotoi.model.Task;

public class TaskController implements IDataSubscriber {

    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private Task task;

    public void setTask(Task task) {
        log.debug("Task {} has been attached to this instance.", task);
        this.task = task;
    }

    @Override
    public void onDataUpdate(@NotNull DataEvent event) {

    }
}
