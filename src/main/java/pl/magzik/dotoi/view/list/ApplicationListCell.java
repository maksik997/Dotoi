package pl.magzik.dotoi.view.list;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.util.ApplicationUtils;

import java.io.File;
import java.io.IOException;

/**
 * A custom implementation of {@link ListCell} for displaying application entries in a list.
 * Each cell contains a {@link Label} showing the application's name and a {@link Button}
 * that allows users to launch the application.
 *
 * <p>The button uses a play icon ({@code "fas-play"}) and, when clicked, attempts to open
 * the associated application file using {@link ApplicationUtils#openApplication(File)}.</p>
 *
 * <h2>Behavior:</h2>
 * <ul>
 *     <li>If the cell is empty, its graphic is cleared.</li>
 *     <li>If a valid {@link File} is provided, the label displays the application's name
 *         (without the file extension), and the button allows launching the application.</li>
 *     <li>If the application fails to open, an error is logged.</li>
 * </ul>
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class ApplicationListCell extends ListCell<File> {

    private static final Logger log = LoggerFactory.getLogger(ApplicationListCell.class);

    private final Label label;

    private final Button button;

    private final VBox box;

    public ApplicationListCell() {
        super();

        this.label = new Label();
        this.button = new Button();
        this.button.setFocusTraversable(false);
        this.button.setGraphic(new FontIcon("fas-play"));
        this.box = new VBox(button, label);
    }

    @Override
    protected void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) setGraphic(null);
        else {
            if (item == null) {
                log.warn("File attached to this cell is null. Clearing the graphic.");
                setGraphic(null);
                return;
            }

            String filename = item.getName();
            int idx = filename.lastIndexOf(".");
            label.setText(idx > 0 ? filename.substring(0, idx) : filename);

            button.setOnAction(event -> {
                try {
                    ApplicationUtils.openApplication(item);
                } catch (IOException e) {
                    log.error("Couldn't open application {}, because of {}", item, e.getMessage(), e);
                }
            });

            setGraphic(box);
        }
    }
}
