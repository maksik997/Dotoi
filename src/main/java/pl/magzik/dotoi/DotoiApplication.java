package pl.magzik.dotoi;

import dorkbox.systemTray.MenuItem;
import dorkbox.systemTray.Separator;
import dorkbox.systemTray.SystemTray;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.TranslationManager;
import pl.magzik.dotoi.manager.WindowManager;
import pl.magzik.dotoi.repository.TaskRepository;
import pl.magzik.dotoi.service.TaskSchedulerService;
import pl.magzik.dotoi.service.TaskService;
import pl.magzik.dotoi.view.TaskListWindow;
import pl.magzik.dotoi.view.TaskWindow;

import java.net.URL;
import java.util.Locale;

/**
 * The main entry point of the application.
 * This class handles essential configuration, including initializing the JavaFX Toolkit, setting up the system tray, and more.
 * <p>
 * Unlike a typical JavaFX application, this class does not open a window; instead, it relies on the system tray for interaction.
 * It initializes necessary models, services, and various communication configurations.
 * Additionally, it disables the implicit application exit, delegating the responsibility of closing the app to the system tray.
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class DotoiApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(DotoiApplication.class);

    private final TaskService taskService;
    private final TaskSchedulerService taskSchedulerService;

    public static void main(String[] args) {
        log.info("Initializing the application...");
        Platform.setImplicitExit(false);
        launch();
    }

    public DotoiApplication() {
        log.info("Creating the task model...");
        this.taskService = new TaskService(new TaskRepository());
        this.taskSchedulerService = new TaskSchedulerService();
    }

    @Override
    public void start(Stage stage) {
        TranslationManager.getInstance().setLocale(Locale.getDefault()); ///< TODO: Will be switched with .forLanguageTag(...) or equivalent for model with those settings.

        log.info("Creating system tray...");
        setupSystemTray();
    }

    private void setupSystemTray() {
        /*
        * Main reason of separated thread usage is: macOS
        * TODO: Maybe moved to separated class.
        * */
        new Thread(() -> {
            SystemTray tray = SystemTray.get();
            URL iconUrl = getClass().getResource("/images/dotoi-icon.png");
            if (iconUrl != null) tray.setImage(iconUrl);
            else log.error("Couldn't load tray icon.");

            tray.getMenu().add(new MenuItem(
                TranslationManager.getInstance().translate("tray.task-list"),
                evt -> WindowManager.getInstance().openWindow("general.title", new TaskListWindow())
            ));
            tray.getMenu().add(new MenuItem(
                TranslationManager.getInstance().translate("tray.exit"),
                evt -> {
                    WindowManager.getInstance().closeAllWindows();
                    taskSchedulerService.shutdown();
                    tray.shutdown();
                    System.exit(0);
                }
            ));
            tray.getMenu().add(new MenuItem(
                TranslationManager.getInstance().translate("tray.settings"),
                evt -> log.debug("Open settings window.")
            ));

            // TODO: Maybe temporary.
            tray.getMenu().add(new Separator());

            tray.getMenu().add(new MenuItem(
                    TranslationManager.getInstance().translate("tray.task"),
                    evt -> WindowManager.getInstance().openWindow("tray.task", new TaskWindow())
            ));
            log.debug("System tray has been successfully initialized.");
        }).start();
    }
}