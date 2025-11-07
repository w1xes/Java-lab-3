import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = LoggerUtil.getLogger(Main.class);

    public static void main(String[] args) {
        String file = "data.csv";
        DataReader dr = new DataReader();
        try {
            logger.info("Starting data read from: " + file);
            List<Person> list = dr.readPersons(file);
            logger.info("Successfully created " + list.size() + " Person objects.");
            System.out.println("Loaded persons:");
            for (Person p : list) {
                System.out.println(p);
            }
        } catch (InvalidDataException e) {
            logger.severe("Failed to read persons: " + e.getMessage());
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error: " + e.getMessage());
            e.printStackTrace(System.err);
        } finally {
            logger.info("Main finished execution (finally).\n");
        }

        TestRunner.runAll();
    }
}
