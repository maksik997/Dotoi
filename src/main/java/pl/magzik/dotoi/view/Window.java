package pl.magzik.dotoi.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.TranslationManager;

import java.net.URL;

public class Window extends Application {

    private static final Logger log = LoggerFactory.getLogger(Window.class);

    private final String fxml;

    public Window(@NotNull String fxml) {
        this.fxml = fxml;
    }

    @Override
    public void start(@NotNull Stage stage) throws Exception {
        log.info("Initializing window...");
        URL fxmlURL = getClass().getResource(fxml);
        FXMLLoader loader = new FXMLLoader(fxmlURL, TranslationManager.getInstance().getBundle());
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
        log.info("Window initialized.");
    }
}
