import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil {
    private static final String LOG_FILE = "app.log";

    static {
        Logger root = Logger.getLogger("");
        for (Handler h : root.getHandlers()) {
            h.setLevel(Level.ALL);
        }

        try {
            Path p = Path.of(LOG_FILE);
            if (!Files.exists(p)) {
                Files.createFile(p);
            }
            FileHandler fh = new FileHandler(LOG_FILE, true);
            fh.setFormatter(new SimpleFormatter());
            fh.setLevel(Level.ALL);
            root.addHandler(fh);
            root.setLevel(Level.ALL);
        } catch (IOException e) {
            root.log(Level.SEVERE, "Failed to create log file handler: " + e.getMessage(), e);
        }
    }

    public static Logger getLogger(Class<?> cls) {
        return Logger.getLogger(cls.getName());
    }
}
