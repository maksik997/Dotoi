/* TODO:
    Add an icon handling.
*/

package pl.magzik.dotoi.view.table;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.TranslationManager;
import pl.magzik.dotoi.model.Task;

import java.util.function.BiConsumer;

/**
 * A custom {@link TableCell} implementation that contains an interactive {@link Button}.
 * <p>
 * This class allows embedding a button within a table cell and associating it with a custom action
 * provided as a {@link BiConsumer}. When the button is clicked, the provided action is executed
 * with the {@link ActionEvent} and the corresponding {@link Task} as parameters.
 * </p>
 *
 * <h3>Usage</h3>
 * <pre>
 * TableColumn<Task, String> column = new TableColumn<>("Action");
 * column.setCellFactory(col -> new ButtonTableCell("delete.icon", (event, task) -> {
 *     System.out.println("Deleting task: " + task.getTitle());
 * }));
 * </pre>
 *
 * <p><b>Note:</b> The button label is currently set using the {@link TranslationManager} but may be
 * updated in the future to use an icon literal.</p>
 *
 * @see Task
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class ButtonTableCell extends TableCell<Task, String> {

    private static final Logger log = LoggerFactory.getLogger(ButtonTableCell.class);

    private final Button button;

    private final BiConsumer<ActionEvent, Task> action;

    /**
     * Creates a new instance of {@link ButtonTableCell} with a button that executes the specified action when clicked.
     * <p>
     * The provided {@code action} is executed whenever the button is clicked, receiving both the {@link ActionEvent}
     * and the associated {@link Task} from the table row.
     * </p>
     * <p><b>Note:</b> The button label currently relies on {@link TranslationManager}, but this may be changed
     * to directly use an icon literal in the future.</p>
     *
     * @param iconLiteral A string representing the button label, which is translated using {@link TranslationManager}.
     * @param action A {@link BiConsumer} that defines the action performed when the button is clicked.
     *               It receives the {@link ActionEvent} and the associated {@link Task}.
     */
    public ButtonTableCell(@NotNull String iconLiteral, @NotNull BiConsumer<ActionEvent, Task> action) {
        super();
        button = new Button(TranslationManager.getInstance().translate(iconLiteral)); ///< TODO: Will be changed for icon literal.
        this.action = action;
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
            button.setOnAction(e -> action.accept(e, task));
            setGraphic(button);
        }
    }
}
