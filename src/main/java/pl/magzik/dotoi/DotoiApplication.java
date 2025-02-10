package pl.magzik.dotoi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.controller.base.Controller;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class DotoiApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(DotoiApplication.class);

    private static final String APPLICATION_NAME = "general.title";

    public static void main(String[] args) {
        log.info("Initializing app...");
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        log.info("Initializing GUI...");
        ResourceBundle bundle = createResourceBundle();

        FXMLLoader loader = new FXMLLoader(DotoiApplication.class.getResource("/fxml/task-list-view.fxml"), bundle);
        Scene scene = new Scene(loader.load());
        Controller controller = loader.getController();
        controller.setStage(stage);
        controller.setBundle(bundle);

        setupStage(stage, scene, bundle);
        log.info("GUI initialized successfully.");
    }

    /**
     * Returns appropriate {@link ResourceBundle} based on the language setting.
     *
     * <p> // @param TODO
     * @return The loaded {@link ResourceBundle}.
     * */
    private ResourceBundle createResourceBundle(/* TODO: Model with settings goes here */) {
        Locale locale = Locale.getDefault(); ///< TODO: Will be switched with .forLanguageTag(...);
        log.debug("Locale set to {}.", locale);

        return ResourceBundle.getBundle("i18n.localization", locale);
    }

    /**
     * Sets up the {@link Stage} instance.
     *
     * @param stage A {@link Stage} to be configured.
     * @param scene A {@link Scene} to be attached to the given {@link Stage}.
     * @param bundle A {@link ResourceBundle} used in localization.
     * */
    private void setupStage(@NotNull Stage stage, Scene scene, @NotNull ResourceBundle bundle) {
        log.debug("Configuring stage...");
        if (bundle.containsKey(APPLICATION_NAME))
            stage.setTitle(bundle.getString(APPLICATION_NAME));

        stage.setScene(scene);
        stage.show();
    }
}