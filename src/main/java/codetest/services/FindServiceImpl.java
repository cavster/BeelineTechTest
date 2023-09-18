package codetest.services;

import codetest.orgtree.Engagement;
import codetest.orgtree.Enterprise;
import codetest.orgtree.Organisation;
import codetest.orgtree.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.*;

public class FindServiceImpl implements FindService{
    @Override
    public Optional<Person> findPersonByName(Enterprise enterprise, String name) {
        for (Person person : enterprise.getAllPersons()) {
            if (person.getName().equalsIgnoreCase(name)) {
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Organisation> findOrganisationsEngagingPerson(Enterprise enterprise, Person person) {
        List<Organisation> engagingOrganisations = new ArrayList<>();
        findOrganisationsEngagingPersonRecursive(enterprise.getOrganisations(), person, engagingOrganisations);
        return engagingOrganisations;
    }

    private void findOrganisationsEngagingPersonRecursive(Collection<Organisation> organisations, Person person, List<Organisation> result) {
        for (Organisation org : organisations) {
            for (Engagement engagement : org.getMembers()) {
             if (engagement.getPerson() != null && engagement.getPerson().getName().equals(person.getName())) {
                    System.out.println("code got here "  +engagement.getPerson());
                    result.add(org);
                    break; // Once we find the person in this organization, no need to check other engagements
                }
            }
            findOrganisationsEngagingPersonRecursive(org.getChildOrganisations(), person, result); // Recursive call for child organizations
        }
    }

    @Override
    public Collection<Person> findPeopleWithMultipleEngagements(Enterprise enterprise) {
        Map<Person, Integer> engagementCountMap = new HashMap<>();
        List<Person> multipleEngagedPeople = new ArrayList<>();

        // Count engagements for each person
        for (Person person : enterprise.getAllPersons()) {
            engagementCountMap.put(person, engagementCountMap.getOrDefault(person, 0) + 1);
        }

        // Find people with multiple engagements
        for (Map.Entry<Person, Integer> entry : engagementCountMap.entrySet()) {
            if (entry.getValue() > 1) {
                multipleEngagedPeople.add(entry.getKey());
            }
        }

        return multipleEngagedPeople;
    }
}
