package codetest.orgtree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** The root node - represents an enterprise / business / endeavour of some kind*/
public class Enterprise extends AbstractNode {

    private Collection<Organisation> organisations;

    public Enterprise(Collection<Organisation> organisations) {
        this.organisations = organisations;
    }

    public Collection<Organisation> getOrganisations() {
        return organisations;
    }



    // Additional method to retrieve all persons within the enterprise
    public List<Person> getAllPersons() {
        List<Person> allPersons = new ArrayList<>();
        for (Organisation org : organisations) {
            allPersons.addAll(org.getAllPersons());
        }
        return allPersons;
    }
    @Override
    public String toString() {
        return "Enterprise{" +
                "organisationList=" + organisations +
                '}';
    }
}
