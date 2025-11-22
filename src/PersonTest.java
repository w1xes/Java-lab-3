import org.junit.Test;
import static org.junit.Assert.*;

public class PersonTest {

    @Test
    public void testPersonCreationValid() throws InvalidDataException {
        Person p = new Person(1, "John Doe", 30);
        assertEquals(1, p.getId());
        assertEquals("John Doe", p.getName());
        assertEquals(30, p.getAge());
    }

    @Test
    public void testPersonNameTrimmed() throws InvalidDataException {
        Person p = new Person(1, "  Alice  ", 25);
        assertEquals("Alice", p.getName());
    }

    @Test(expected = InvalidDataException.class)
    public void testPersonNegativeId() throws InvalidDataException {
        new Person(-1, "Bob", 40);
    }

    @Test(expected = InvalidDataException.class)
    public void testPersonZeroId() throws InvalidDataException {
        new Person(0, "Charlie", 35);
    }

    @Test(expected = InvalidDataException.class)
    public void testPersonNullName() throws InvalidDataException {
        new Person(1, null, 28);
    }

    @Test(expected = InvalidDataException.class)
    public void testPersonEmptyName() throws InvalidDataException {
        new Person(1, "", 28);
    }

    @Test(expected = InvalidDataException.class)
    public void testPersonBlankName() throws InvalidDataException {
        new Person(1, "   ", 28);
    }

    @Test(expected = InvalidDataException.class)
    public void testPersonNegativeAge() throws InvalidDataException {
        new Person(1, "David", -1);
    }

    @Test(expected = InvalidDataException.class)
    public void testPersonAgeTooHigh() throws InvalidDataException {
        new Person(1, "Eve", 151);
    }

    @Test
    public void testPersonBoundaryAges() throws InvalidDataException {
        Person p1 = new Person(1, "Frank", 0);
        assertEquals(0, p1.getAge());

        Person p2 = new Person(2, "Grace", 150);
        assertEquals(150, p2.getAge());
    }

    @Test
    public void testPersonToString() throws InvalidDataException {
        Person p = new Person(42, "Henry", 33);
        String expected = "Person{id=42, name='Henry', age=33}";
        assertEquals(expected, p.toString());
    }
}
