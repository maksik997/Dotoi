package pl.magzik.dotoi.manager;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.view.Window;

import java.util.HashSet;
import java.util.Set;

/**
 * Manager class responsible for handling windows during the application's life cycle.
 * This class provides the following features:
 * <ul>
 *     <li>An easy way to open new JavaFX windows.</li>
 *     <li>An easy way to close all windows.</li>
 * </ul>
 * An instance of this class is stored as a Singleton using the InstanceHolder pattern.
 * To obtain the instance, use the {@link WindowManager#getInstance()} method.
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class WindowManager {

    private static final Logger log = LoggerFactory.getLogger(WindowManager.class);

    private static final class InstanceHolder {
        private final static WindowManager instance = new WindowManager();
    }

    public static WindowManager getInstance() {
        return InstanceHolder.instance;
    }

    private final Set<Stage> windows;

    private WindowManager() {
        log.info("Initializing window manager...");
        this.windows = new HashSet<>();
    }

    public void openWindow(@NotNull String title, @NotNull Window window) {
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                stage.setTitle(TranslationManager.getInstance().translate(title));
                window.start(stage);
                stage.setOnCloseRequest(e -> {
                    windows.remove(stage);
                    try {
                        window.stop();
                    } catch (Exception ex) {
                        log.error("Couldn't destroy window {}, because: {}.\n Potential memory-leak.", window, ex.getMessage());
                    }
                });
                windows.add(stage);
            } catch (Exception ex) {
                log.error("Unexpected error: Window could not open: {}", ex.getMessage(), ex);
                // TODO: ERROR ALERT SHOULD BE SHOWN.
                // TODO: TO DETERMINE, WHAT TO DO IN THAT CASE ?
            }
        });
    }

    public void closeAllWindows() {
        log.info("Closing the app...");
        Platform.runLater(() -> {
            windows.forEach(Stage::close);
            windows.clear();
        });
        Platform.exit(); ///< TODO: Determine whether this should be executed here.
    }
}
