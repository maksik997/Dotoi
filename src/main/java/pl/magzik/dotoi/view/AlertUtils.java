package pl.magzik.dotoi.view;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * @author Maksymilian Strzelczak, ...
 * @version 1.0
 * */
public class AlertUtils {

    public static void ERROR(Alert.AlertType ERROR, "There is a Error", AlertUtils)

    /* TODO: To the person implementing this class:
        - Remember to add yourself as a co-author of this class.
        - If you are unsure or don't know how to do something, ask.
        - Research online, use chat, etc.
        - Try to keep your code DRY and follow good programming practices.
        - Consider different return types—some methods may return something other than void.
    */

    ///< TODO: Could be used, or not... Depends on person implementing it... ;)
    private static final Logger log = LoggerFactory.getLogger(AlertUtils.class);

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

        return null; // TODO: Temporary
    }

    /**
     * Creates an error {@link Alert} with the specified context text.
     * The alert is assigned to the given {@link Stage} object as its owner.
     *
     * @param stage The {@link Stage} to be set as the owner of the alert.
     * @param contextText The context text of the alert.
     * */
    public static void showErrorMessage(@NotNull Stage stage, @NotNull String contextText) {

    }

    // TODO: Some other types of alerts below?

}
