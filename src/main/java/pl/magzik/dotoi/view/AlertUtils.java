package pl.magzik.dotoi.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


/**
 * Utility class that provides an easy way to handle alerts.
 * This class supports displaying different types of alerts:
 * <ul>
 *     <li>Error alert {@link Alert.AlertType#ERROR} - An alert displaying an error message.</li>
 *     <li>Confirmation alert {@link Alert.AlertType#CONFIRMATION} - An alert asking the user for confirmation.</li>
 *     <li>Information alert {@link Alert.AlertType#INFORMATION} - An alert displaying an informational message.</li>
 *     <li>Warning alert {@link Alert.AlertType#WARNING} - An alert displaying a warning message.</li>
 * </ul>
 * All types of alerts block the main thread of the application and wait for user interaction.
 *
 * @since 0.1
 * @author Maksymilian Strzelczak, Jaglak
 * @version 1.0
 * */
public class AlertUtils {

    private static final Logger log = LoggerFactory.getLogger(AlertUtils.class);


    /**
     * Displays a confirmation alert and returns the user's response.
     *
     * @param stage       The owner {@link Stage}.
     * @param contextText The confirmation message.
     * @return {@code true} if the user clicks OK, otherwise {@code false}.
     */
    public static boolean showConfirmationAlert(@NotNull Stage stage, @NotNull String contextText) {
        log.info("Displaying confirmation alert: {}", contextText);
        Alert alert = createAlert(stage, Alert.AlertType.CONFIRMATION, "Confirmation", "Please Confirm", contextText);
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Displays an information alert.
     *
     * @param stage       The owner {@link Stage}.
     * @param contextText The informational message.
     */
    public static void showInformationAlert(@NotNull Stage stage, @NotNull String contextText) {
        log.info("Displaying information alert: {}", contextText);
        Alert alert = createAlert(stage, Alert.AlertType.INFORMATION, "Information", "Notice", contextText);
        alert.showAndWait();
    }

    /**
     * Displays a warning alert.
     *
     * @param stage       The owner {@link Stage}.
     * @param contextText The warning message.
     */
    public static void showWarningAlert(@NotNull Stage stage, @NotNull String contextText) {
        log.warn("Displaying warning alert: {}", contextText);
        Alert alert = createAlert(stage, Alert.AlertType.WARNING, "Warning", "Caution", contextText);
        alert.showAndWait();
    }
    public static void showErrorAlert(@NotNull Stage stage, @NotNull String contextText) {
        log.error("Displaying error alert: {}", contextText);
        Alert alert = createAlert(stage, Alert.AlertType.ERROR, "Error", "Error Occurred", contextText);
        alert.showAndWait();
    }

    /**
     * Creates an {@link Alert} with the specified type, title, header text, and context text.
     * The alert is assigned to the given {@link Stage} object as its owner.
     *
     * @param owner The {@link Stage} to be set as the owner of the alert.
     * @param type The {@link Alert.AlertType} of the alert.
     * @param title The title of the alert.
     * @param headerText The header text of the alert.
     * @param contextText The context text of the alert.
     * @return A new {@link Alert}.
     * */
    private static @NotNull Alert createAlert(
        @NotNull Stage owner,
        @NotNull Alert.AlertType type,
        @NotNull String title,
        @NotNull String headerText,
        @NotNull String contextText
    ) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contextText);
        alert.initOwner(owner);
        return alert;

    }


}
