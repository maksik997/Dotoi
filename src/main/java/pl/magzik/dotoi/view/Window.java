package pl.magzik.dotoi.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.TranslationManager;
import pl.magzik.dotoi.manager.data.DataManager;
import pl.magzik.dotoi.manager.data.IDataSubscriber;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Base class for configuring a new JavaFX window.
 * <p>
 * This class also loads the FXML file specified in the constructor.
 * If the given FXML file is not found in the application resources, an exception will be thrown.
 * <p>
 * Please note that this class is intended to be extended, so it is not meant to be used directly.
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class Window extends Application {

    private static final Logger log = LoggerFactory.getLogger(Window.class);

    private final String fxml;

    protected IDataSubscriber controller;

    public Window(@NotNull String fxml) {
        this.fxml = fxml;
    }

    @Override
    public void start(@NotNull Stage stage) throws IOException {
        log.info("Initializing window...");
        URL fxmlURL = getClass().getResource(fxml);
        if (fxmlURL == null) {
            log.error("Couldn't load the fxml file: {}. File not found.", fxml);
            throw new FileNotFoundException("Fxml file not found: " + fxml);
        }
        FXMLLoader loader = new FXMLLoader(fxmlURL, TranslationManager.getInstance().getBundle());
        Scene scene = new Scene(loader.load());
        if (loader.getController() instanceof IDataSubscriber subscriber) {
            controller = subscriber;
        }
        stage.setScene(scene);
        stage.show();
        log.info("Window initialized.");
    }

    @Override
    public void stop() {
        log.info("Closing the window.");
        if (controller != null) DataManager.getInstance().unsubscribe(controller);
    }
}
