package pl.magzik.dotoi.base;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;

/**
 * A utility class for resolving and managing application directories, including configuration, logs, and data directories.
 * <p>
 * This class provides methods to retrieve paths for important application directories such as:
 * <ul>
 *     <li>Configuration directory</li>
 *     <li>Log directory</li>
 *     <li>Data directory</li>
 * </ul>
 * It automatically determines the correct directory paths based on the user's operating system (Windows, macOS, or Linux).
 * </p>
 * <p>
 * The directories are created if they do not already exist, and are stored for later use by the application.
 * </p>
 */
public class PathResolver {

    private static class InstanceHolder {
        private static final PathResolver INSTANCE = new PathResolver();
    }

    /**
     * Returns the singleton instance of the {@link PathResolver} class.
     *
     * @return the singleton {@link PathResolver} instance
     */
    public static PathResolver getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final String APPLICATION_NAME = "Dotoi";

    private static final String LOG_DIRECTORY = "log";
    private static final String WIN_PATH = "AppData/Roaming/" + APPLICATION_NAME,
                                MAC_PATH = "Library/Application Support/" + APPLICATION_NAME,
                                LINUX_PATH = ".config/" + APPLICATION_NAME;

    private final Path logDirectory;

    /**
     * Private constructor that initializes the application directories.
     * It detects the user's home directory and operating system, then creates the necessary directories.
     *
     * @throws IllegalStateException if the user home directory is not available
     */
    private PathResolver() {
        String userHome = System.getProperty("user.home");
        if (userHome == null) throw new IllegalStateException("User home directory is not available.");

        String operatingSystem = System.getProperty("os.name").toLowerCase();
        Path applicationPath = getApplicationPath(userHome, operatingSystem);

        this.logDirectory = createDirectories(applicationPath, LOG_DIRECTORY);
    }

    /**
     * Returns the path to the log directory.
     *
     * @return the path to the log directory
     */
    public Path getLogDirectory() {
        return logDirectory;
    }

    /**
     * Determines the application path based on the user's home directory and operating system.
     *
     * @param userHome the user's home directory path
     * @param operatingSystem the name of the user's operating system
     * @return the determined application path
     */
    private @NotNull Path getApplicationPath(@NotNull String userHome, @NotNull String operatingSystem) {
        if (operatingSystem.contains("win")) return Paths.get(userHome, WIN_PATH);
        else if (operatingSystem.contains("mac")) return Paths.get(userHome, MAC_PATH);
        else return Paths.get(userHome, LINUX_PATH);
    }

    /**
     * Creates the specified directory and its parent directories if they do not exist.
     *
     * @param base the base path where the directory will be created
     * @param folder the name of the folder to create
     * @return the path to the created directory
     * @throws DirectoryCreationException if an error occurs while creating the directory
     */
    private @NotNull Path createDirectories(@NotNull Path base, @NotNull String folder) {
        Path folderPath = base.resolve(folder);
        try {
            Files.createDirectories(folderPath);
        } catch (IOException e) {
            throw new DirectoryCreationException("Failed to create directory: " + folderPath, e);
        }
        return folderPath;
    }

    /**
     * Exception thrown when there is a failure in creating a directory.
     *
     * @see Files#createDirectories(Path, FileAttribute[])
     */
    public static class DirectoryCreationException extends RuntimeException {
        public DirectoryCreationException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
