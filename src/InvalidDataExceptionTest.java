import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;

public class InvalidDataExceptionTest {

    @Test
    public void testExceptionWithMessage() {
        InvalidDataException ex = new InvalidDataException("Test message");
        assertEquals("Test message", ex.getMessage());
    }

    @Test
    public void testExceptionWithMessageAndCause() {
        Throwable cause = new RuntimeException("root cause");
        InvalidDataException ex = new InvalidDataException("Test message", cause);
        assertEquals("Test message", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testExceptionWithCause() {
        Throwable cause = new IOException("File error");
        InvalidDataException ex = new InvalidDataException(cause);
        assertEquals(cause, ex.getCause());
    }

    @Test
    public void testExceptionNoArgs() {
        InvalidDataException ex = new InvalidDataException();
        assertNotNull(ex);
    }

    @Test
    public void testExceptionThrowable() throws InvalidDataException {
        try {
            throw new InvalidDataException("Custom error");
        } catch (InvalidDataException e) {
            assertEquals("Custom error", e.getMessage());
        }
    }

    @Test
    public void testExceptionIsCheckedException() {
        InvalidDataException ex = new InvalidDataException();
        assertTrue(ex instanceof Exception);
    }
}
