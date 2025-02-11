package pl.magzik.dotoi.manager;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.view.Window;

import java.util.HashSet;
import java.util.Set;

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

    public <T extends Window> void openWindow(@NotNull String title, @NotNull T window) {
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                stage.setTitle(TranslationManager.getInstance().translate(title));
                window.start(stage);
                stage.setOnCloseRequest(e -> windows.remove(stage));
                windows.add(stage);
            } catch (Exception ex) {
                log.error("Unexpected error: Window could not open: {}", ex.getMessage(), ex);
                // TODO: ERROR ALERT SHOULD BE SHOWN
                // TODO: TO DETERMINE, WHAT TO DO IN THAT CASE ?
            }
        });
    }

    public void closeAllWindows() {
        Platform.runLater(() -> {
            windows.forEach(Stage::close);
            windows.clear();
        });
    }
}
