import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataReader {
    private static final Logger logger = LoggerUtil.getLogger(DataReader.class);

    public List<Person> readPersons(String path) throws InvalidDataException {
        List<Person> result = new ArrayList<>();
        BufferedReader br = null;
        try {
            logger.info("Opening file: " + path);
            br = new BufferedReader(new FileReader(path));
            String line;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line.trim();
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split(",");
                try {
                    if (parts.length != 3) {
                        logger.log(Level.WARNING, "Skipping malformed line " + lineNo + ": " + line);
                        continue;
                    }
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    Person p = new Person(id, name, age);
                    result.add(p);
                } catch (NumberFormatException | InvalidDataException e) {
                    logger.log(Level.WARNING, "Skipping invalid line " + lineNo + ": " + e.getMessage());
                    continue;
                }
            }
            logger.info("Finished reading file: " + path + ", loaded persons: " + result.size());
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File not found: " + path + " - " + e.getMessage(), e);
            throw new InvalidDataException("File not found: " + path, e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "I/O error when reading file: " + path + " - " + e.getMessage(), e);
            throw new InvalidDataException("I/O error: " + e.getMessage(), e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                    logger.fine("Closed file: " + path);
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Failed to close reader: " + e.getMessage(), e);
                }
            }
        }
        return result;
    }
}
