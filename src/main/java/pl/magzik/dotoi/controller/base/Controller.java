package pl.magzik.dotoi.controller.base;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Base controller class.
 * <p>
 * This class provides several core features, including:
 * </p>
 * <ul>
 *     <li>Bundle management for localization.</li>
 *     <li>Common utility methods for controllers. TODO</li>
 *     <li>Potential support for dependency injection. TODO</li>
 * </ul>
 *
 * <p>
 * Subclasses should extend this base controller to inherit shared functionality
 * and maintain consistency across the application.
 * </p>
 *
 * @author Maksymilian Strzelczak
 * @version 1.0
 * @since JavaFX 21
 */
public class Controller {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private ResourceBundle bundle;

    private Stage stage;

    /**
     * Attaches given bundle to this {@link Controller} instance.
     * @param bundle A {@link ResourceBundle} to be attached.
     * */
    public void setBundle(@NotNull ResourceBundle bundle) {
        this.bundle = bundle;
    }

    /** Checks whether {@link Controller#bundle} has been attached to this instance.
     * <p>
     * If {@link Controller#bundle} is {@code null} exception will be thrown.
     * @throws NullPointerException If there is no bundle attached to this instance.
     * */
    private void validateBundle() {
        if (bundle == null) {
            var ex = new NullPointerException("Bundle is not attached to the controller.");
            log.error("{}", ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Attaches given stage to this {@link Stage} instance.
     * @param stage A {@link Stage} to be attached.
     * */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Translates the provided string key into the corresponding value from the {@link ResourceBundle}.
     * <p>
     * If the key is not found in the bundle, it returns the key itself surrounded by double square brackets.
     * <p>
     * If there is no bundle attached to this {@link Controller} instance, exception will be thrown.
     *
     * @param key the key to translate
     * @return the translated string, or the key if not found
     */
    public @NotNull String translate(@NotNull String key) {
        validateBundle();
        if (bundle.containsKey(key)) return bundle.getString(key);
        log.warn("Missing translation for key: {}", key);
        return String.format("[[%s]]", key);
    }

    /**
     * Finds the key corresponding to the provided value in the {@link ResourceBundle}.
     * <p>
     * If no key is found for the value, it returns the value itself.
     * <p>
     * If there is no bundle attached to this {@link Controller} instance, exception will be thrown.
     *
     * @param value the value to find the corresponding key for
     * @return the key for the value, or the value itself if not found
     */
    public @NotNull String findKey(@NotNull String value) {
        validateBundle();
        Optional<String> result = bundle.keySet()
                                        .stream()
                                        .map(bundle::getString)
                                        .filter(v -> v.equals(value))
                                        .findAny();
        if (result.isPresent()) return result.get();
        log.warn("Missing key for a value: {}", value);
        return String.format("[[%s]]", value);
    }

    /**
     * Returns new scene loaded from resources based on given fxml path.
     * @param fxmlPath A {@link String}, path to new fxml document.
     * @return A new {@link Scene}.
     * */
    public Scene loadScene(@NotNull String fxmlPath) {
        validateBundle();
        try {
            URL fxmlUrl = getClass().getResource(fxmlPath);
            FXMLLoader loader = new FXMLLoader(fxmlUrl, bundle);
            Parent root = loader.load();

            Controller controller = loader.getController();
            controller.setBundle(bundle);
            controller.setStage(stage);

            return new Scene(root);
        } catch (IOException ex) {
            showErrorDialog("general.alert.error.scene-load-failed");
            Platform.exit();
        }
        return null;
    }

    /**
     * Displays an error dialog with the provided context text.
     * @param contextText the context text for the error dialog
     */
    public void showErrorDialog(String contextText) { ///< TODO: Move to another class?
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(translate("general.alert.error.title"));
        alert.setHeaderText(translate("general.alert.error.header"));
        alert.setContentText(translate(contextText));

        if (stage != null && stage.getScene() != null) {
            alert.initOwner(stage);
        }

        alert.showAndWait();
    }
}
