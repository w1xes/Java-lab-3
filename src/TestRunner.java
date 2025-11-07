import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestRunner {
    private static final Logger logger = LoggerUtil.getLogger(TestRunner.class);

    public static void runAll() {
        logger.info("Starting tests...");
        int passed = 0;
        int failed = 0;
        try {
            testPersonCreationValid();
            logger.info("testPersonCreationValid passed");
            passed++;
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "testPersonCreationValid failed: " + t.getMessage(), t);
            failed++;
        }

        try {
            testPersonCreationInvalid();
            logger.info("testPersonCreationInvalid passed");
            passed++;
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "testPersonCreationInvalid caught expected exception: " + t.getMessage());
            passed++;
        }

        try {
            testDataReaderParsing();
            logger.info("testDataReaderParsing passed");
            passed++;
        } catch (Throwable t) {
            logger.log(Level.SEVERE, "testDataReaderParsing failed: " + t.getMessage(), t);
            failed++;
        }

        logger.info("Tests finished. Passed: " + passed + ", Failed: " + failed);
        System.out.println("Tests finished. Passed: " + passed + ", Failed: " + failed);
    }

    private static void testPersonCreationValid() throws InvalidDataException {
        Person p = new Person(1, "Alice", 30);
        if (!"Alice".equals(p.getName()) || p.getAge() != 30 || p.getId() != 1) {
            throw new AssertionError("Person fields not matching");
        }
    }

    private static void testPersonCreationInvalid() throws InvalidDataException {
        boolean thrown = false;
        try {
            new Person(-1, "", -5);
        } catch (InvalidDataException e) {
            logger.fine("Received expected InvalidDataException: " + e.getMessage());
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected InvalidDataException was not thrown");
        }
    }

    private static void testDataReaderParsing() throws InvalidDataException {
        DataReader dr = new DataReader();
        List<Person> list = dr.readPersons("data.csv");
        if (list.isEmpty()) {
            throw new AssertionError("DataReader returned empty list; expected at least one record");
        }
    }
}
