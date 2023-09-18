package codetest.orgtree;

public class Person extends AbstractNode {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        // Perform a case-insensitive comparison after trimming whitespace
        return name != null ? name.trim().equalsIgnoreCase(person.name.trim()) : person.name == null;
    }
}
