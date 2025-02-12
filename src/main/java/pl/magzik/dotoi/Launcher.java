package pl.magzik.dotoi;

import pl.magzik.dotoi.base.PathResolver;

/**
 * A simple launcher class that wraps the main {@link javafx.application.Application} class.
 * <p>
 * This wrapper is necessary because the application is not modular and requires an explicit entry point.
 * More on this here: <a href="https://openjfx.io/openjfx-docs/">JavaFX Docs</a>
 * Additionally, this class sets system properties, such as the log path, before launching the main application.
 *
 * @since 0.1
 * @author Maksymilian Strzelczak
 * @version 1.0
 */
public class Launcher {
    public static void main(String[] args) {
        setSystemProperties();
        DotoiApplication.main(args);
    }

    /**
     * Sets system properties required for the application's operation, such as the log file path.
     * <p>
     * This method configures necessary system properties before launching the application,
     * ensuring proper logging and other environment settings.
     */
    private static void setSystemProperties() {
        System.setProperty("logPath", PathResolver.getInstance().getLogDirectory().toString());
        // TODO: More properties could appear here...
    }
}
