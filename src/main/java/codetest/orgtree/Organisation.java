package codetest.orgtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/** An organization contains zero or more members, and zero or more sub-organisations */
public class    Organisation extends AbstractNode {
    private final String name;
    private final Collection<Engagement> members;
    private final Collection<Organisation> childOrganisations;

    public Organisation(String name, Collection<Engagement> members, Collection<Organisation> childOrganisations) {
        this.name = name;
        this.members = members;
        this.childOrganisations = childOrganisations;
    }

    public String getName() {
        return name;
    }

    public Collection<Engagement> getMembers() {
        return members;
    }

    public Collection<Organisation> getChildOrganisations() {
        return childOrganisations;
    }

    public List<Person> getAllPersons() {
        List<Person> allPersons = new ArrayList<>();
        for (Engagement engagement : members) {
            Person person = engagement.getPerson();
            if (person != null) {
                allPersons.add(person);
            }
        }
        for (Organisation childOrg : childOrganisations) {
            allPersons.addAll(childOrg.getAllPersons());
        }
        return allPersons;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(members, that.members) &&
                Objects.equals(childOrganisations, that.childOrganisations);
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "name='" + name + '\'' +
                ", members=" + members +
                ", childOrganisations=" + childOrganisations +
                '}';
    }
}
