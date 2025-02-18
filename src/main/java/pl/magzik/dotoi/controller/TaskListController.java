package pl.magzik.dotoi.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;
import pl.magzik.dotoi.manager.data.IDataSubscriber;
import pl.magzik.dotoi.model.Task;

import java.util.List;

public class TaskListController implements IDataSubscriber {

    private static final Logger log = LoggerFactory.getLogger(TaskListController.class);

    public TaskListController() {
        DataManager.getInstance().subscribe(this);
        DataManager.getInstance().notifySubscribers(new DataEvent.RequestTasks()); // TODO: TEMP
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