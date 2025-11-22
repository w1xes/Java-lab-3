import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataReaderTest {

    @Test
    public void testReadPersonsValidFile() throws Exception {
        // create temp file with valid data
        Path tempFile = Files.createTempFile("test_persons", ".csv");
        Files.write(tempFile, "1,Alice,25\n2,Bob,30\n".getBytes());

        DataReader reader = new DataReader();
        List<Person> persons = reader.readPersons(tempFile.toString());

        assertEquals(2, persons.size());
        assertEquals("Alice", persons.get(0).getName());
        assertEquals(25, persons.get(0).getAge());
        assertEquals("Bob", persons.get(1).getName());
        assertEquals(30, persons.get(1).getAge());

        Files.delete(tempFile);
    }

    @Test
    public void testReadPersonsSkipsInvalidLines() throws Exception {
        Path tempFile = Files.createTempFile("test_persons", ".csv");
        Files.write(tempFile, 
            ("1,Alice,25\n" +
            "invalid,line\n" +
            "2,Bob,30\n" +
            "3,BadAge,999\n").getBytes());

        DataReader reader = new DataReader();
        List<Person> persons = reader.readPersons(tempFile.toString());

        // should skip invalid and bad-age lines, keep only 2 valid ones
        assertEquals(2, persons.size());
        assertEquals("Alice", persons.get(0).getName());
        assertEquals("Bob", persons.get(1).getName());

        Files.delete(tempFile);
    }

    @Test
    public void testReadPersonsEmptyFile() throws Exception {
        Path tempFile = Files.createTempFile("test_persons", ".csv");
        Files.write(tempFile, "".getBytes());

        DataReader reader = new DataReader();
        List<Person> persons = reader.readPersons(tempFile.toString());

        assertEquals(0, persons.size());

        Files.delete(tempFile);
    }

    @Test
    public void testReadPersonsCommentAndBlankLines() throws Exception {
        Path tempFile = Files.createTempFile("test_persons", ".csv");
        Files.write(tempFile, 
            ("# comment line\n" +
            "\n" +
            "1,Alice,25\n" +
            "   \n" +
            "2,Bob,30\n").getBytes());

        DataReader reader = new DataReader();
        List<Person> persons = reader.readPersons(tempFile.toString());

        // should skip comments and blank lines
        assertEquals(2, persons.size());

        Files.delete(tempFile);
    }

    @Test(expected = InvalidDataException.class)
    public void testReadPersonsFileNotFound() throws InvalidDataException {
        DataReader reader = new DataReader();
        reader.readPersons("nonexistent_file.csv");
    }

    @Test
    public void testReadPersonsWithWhitespace() throws Exception {
        Path tempFile = Files.createTempFile("test_persons", ".csv");
        Files.write(tempFile, "1,  John Doe  ,35\n2,   Jane   ,28\n".getBytes());

        DataReader reader = new DataReader();
        List<Person> persons = reader.readPersons(tempFile.toString());

        assertEquals(2, persons.size());
        assertEquals("John Doe", persons.get(0).getName());
        assertEquals("Jane", persons.get(1).getName());

        Files.delete(tempFile);
    }
}
