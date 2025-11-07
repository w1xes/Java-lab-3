import java.util.logging.Logger;

public class Person {
    private static final Logger logger = LoggerUtil.getLogger(Person.class);

    private final int id;
    private final String name;
    private final int age;

    public Person(int id, String name, int age) throws InvalidDataException {
        if (id <= 0) {
            throw new InvalidDataException("ID must be positive, got: " + id);
        }
        if (name == null || name.isBlank()) {
            throw new InvalidDataException("Name cannot be empty");
        }
        if (age < 0 || age > 150) {
            throw new InvalidDataException("Age must be between 0 and 150, got: " + age);
        }
        this.id = id;
        this.name = name.trim();
        this.age = age;
        logger.info("Created Person: " + this);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}
