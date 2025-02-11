package pl.magzik.dotoi;

import pl.magzik.dotoi.base.PathResolver;

import java.nio.file.Path;

public class Launcher {
    public static void main(String[] args) {
        Path logPath = PathResolver.getInstance().getLogDirectory();
        System.setProperty("logPath", logPath.toString());

        DotoiApplication.main(args); ///< Will be switched for tray
    }
}
