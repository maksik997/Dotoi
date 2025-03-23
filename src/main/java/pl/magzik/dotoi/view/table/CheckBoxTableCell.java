package pl.magzik.dotoi.view.table;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.DataEvent;
import pl.magzik.dotoi.manager.data.DataManager;
import pl.magzik.dotoi.model.Task;

/**
 * A custom {@link TableCell} that displays a {@link CheckBox} for managing the completion state of a {@link Task}.
 * <p>
 * The checkbox reflects the current completion state of the task and allows the user to toggle it.
 * Whenever the checkbox state changes, the appropriate {@link DataEvent} is emitted via {@link DataManager}
 * to notify subscribers of the change.
 * </p>
 * <p><b>Note:</b> This class assumes that {@link Task} provides {@code complete()} and {@code uncomplete()} methods
 * to modify its completion status.</p>
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class CheckBoxTableCell extends TableCell<Task, Boolean> {

    private static final Logger log = LoggerFactory.getLogger(CheckBoxTableCell.class);

    private final CheckBox checkBox;

    /**
     * Creates a new {@link CheckBoxTableCell} instance.
     * <p>
     * The cell contains a {@link CheckBox} that reflects and modifies the completion state of the associated {@link Task}.
     * When toggled, the checkbox triggers an event notifying the system of the task's updated status.
     * </p>
     */
    public CheckBoxTableCell() {
        super();
        checkBox = new CheckBox();
    }

    @Override
    protected void updateItem(@NotNull Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) setGraphic(null);
        else {
            final Task task = getTableRow().getItem();
            if (task == null) {
                log.warn("Task attached to this cell is null. Clearing the graphic.");
                setGraphic(null);
                return;
            }
            checkBox.setSelected(task.isCompleted());
            checkBox.setOnAction(event -> {

                if (checkBox.isSelected()) {
                    task.complete();
                    DataManager.getInstance().notifySubscribers(new DataEvent.TaskCompleted(task));
                } else {
                    task.uncomplete();
                    DataManager.getInstance().notifySubscribers(new DataEvent.TaskUncompleted(task));
                }
            });

            setGraphic(checkBox);
        }
    }
}
