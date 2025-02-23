package pl.magzik.dotoi.view.table;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.model.Task;

/**
 * A custom {@link TableCell} implementation designed for displaying task details.
 * <p>
 * This class is responsible for rendering task information within a table cell. It utilizes
 * a {@link VBox} layout to organize three labels:
 * <ul>
 *     <li><b>Date:</b> Displays the task creation date.</li>
 *     <li><b>Title:</b> Displays the task title in bold.</li>
 *     <li><b>Description:</b> Displays the task description with a smaller font.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This cell template relies on the {@link Task} class to extract and display relevant data.
 * </p>
 *
 * @see Task
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class TaskTableCell extends TableCell<Task, String> {

    private static final Logger log = LoggerFactory.getLogger(TaskTableCell.class);

    private final Label date, title, description;

    private final VBox box;

    public TaskTableCell() {
        super();
        this.date = new Label();
        date.setStyle("-fx-font-size: 10px;");
        this.title = new Label();
        title.setStyle("-fx-font-weight: bold");
        this.description = new Label();
        description.setStyle("-fx-font-size: 10px;");
        this.box = new VBox(date, title, description);
    }

    @Override
    protected void updateItem(@NotNull String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) setGraphic(null);
        else {
            final Task task = getTableRow().getItem();
            if (task == null) {
                log.warn("Task attached to this cell is null. Clearing the graphic.");
                setGraphic(null);
                return;
            }
            date.setText(task.getCreatedAt().toLocalDate().toString());
            title.setText(task.getTitle());
            description.setText(task.getDescription());
            setGraphic(box);
        }
    }
}
