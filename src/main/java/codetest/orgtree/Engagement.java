package codetest.orgtree;

/** An engagement is a contracted job fulfilled by a person
 * A person may have more than one current engagement */
import java.util.Objects;

public class Engagement extends AbstractNode {
    private final String title;
    private final Person person;

    public Engagement(String title, Person person) {
        this.person = person;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public String toString() {
        return "Engagement{" +
                "title='" + title + '\'' +
                ", person=" + person +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Engagement that = (Engagement) o;

        return Objects.equals(title, that.title) &&
                Objects.equals(person, that.person);
    }
}
