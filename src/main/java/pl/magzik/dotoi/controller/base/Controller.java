package pl.magzik.dotoi.controller.base;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.magzik.dotoi.manager.data.IDataSubscriber;

/**
 * Base controller class.
 * @author Maksymilian Strzelczak
 * @version 1.0
 * @since JavaFX 21
 */
public abstract class Controller implements IDataSubscriber {

    private static final Logger log = LoggerFactory.getLogger(Controller.class);

    private Stage stage;

    public void setStage(@NotNull Stage stage) {
        this.stage = stage;
    }

    protected Stage getStage() {
        return stage;
    }

}
