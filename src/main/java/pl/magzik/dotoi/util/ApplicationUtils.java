package pl.magzik.dotoi.util;

import dorkbox.desktop.Desktop;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.TranslationManager;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A utility class providing various application-related support methods.
 *
 * <p>This class offers two primary functionalities:</p>
 * <ul>
 *     <li>Opening applications using the {@link Desktop} class via {@link #openApplication(File)}.</li>
 *     <li>Selecting an application file using a file chooser dialog via {@link #chooseApplication(Stage)}.</li>
 * </ul>
 *
 * <p>The class includes OS-specific handling for application execution and file selection.</p>
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class ApplicationUtils {

    private static final Logger log = LoggerFactory.getLogger(ApplicationUtils.class);

    /**
     * Opens a file chooser dialog to allow the user to select an application file.
     * The dialog applies OS-specific file filters to show only executable files.
     *
     * @param owner The parent stage of the file chooser dialog.
     * @return A {@link File} object representing the selected application, or {@code null} if the user cancels the selection.
     */
    public static @Nullable File chooseApplication(@NotNull Stage owner) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(TranslationManager.getInstance().translate("task-editor.applications.chooser.title"));
        setupExtensionFilters(fileChooser);
        return fileChooser.showOpenDialog(owner);
    }

    private static void setupExtensionFilters(@NotNull FileChooser fileChooser) {
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                TranslationManager.getInstance().translate("task-editor.applications.chooser.filter"),
                switch (getOperatingSystem()) {
                    case "windows" -> List.of("*.exe", "*.bat");
                    case "mac" -> List.of("*.app");
                    default -> List.of("*");
                }
        ));
    }

    /**
     * Opens the specified application file.
     *
     * <p>This method verifies that the file exists and is executable before attempting to open it.
     * If the file does not meet these criteria, a warning is logged, and the method returns {@code false}.</p>
     *
     * @param applicationFile The application file to be opened.
     * @return {@code true} if the application was successfully opened, {@code false} otherwise.
     * @throws IOException If an I/O error occurs while attempting to open the application.
     */
    public static boolean openApplication(@NotNull File applicationFile) throws IOException {
        if (!applicationFile.exists() || !applicationFile.canExecute()) {
            log.warn("Cannot open the app: {}. File doesn't exist or additional permissions required.", applicationFile);
            return false;
        }

        return switch (getOperatingSystem()) {
            case "windows", "linux", "mac" -> {
                Desktop.open(applicationFile);
                yield true;
            }
            default -> {
                log.warn("Unsupported operating system");
                yield false;
            }
        };
    }

    private static @NotNull String getOperatingSystem() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) return "windows";
        if (os.contains("mac")) return "mac";
        if (os.contains("nix") || os.contains("nux")) return "linux";
        return "other";
    }
}
