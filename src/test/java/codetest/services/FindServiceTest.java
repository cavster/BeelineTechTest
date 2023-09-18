package codetest.services;

import codetest.orgtree.Engagement;
import codetest.orgtree.Enterprise;
import codetest.orgtree.Organisation;
import codetest.orgtree.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindServiceTest {
    Person alice = new Person("Alice Arnold");
    Person bob = new Person("Bob Burns");
    Person james = new Person("James Jones");
    Person pierre = new Person("Pierre Martin");
    Person aliyah = new Person("Aliyah Williams");
    Person sue = new Person("Sue Smith");
   Organisation whole =   new Organisation("Executive",
           asList(
                   new Engagement("CEO", alice),
                   new Engagement("CTO", bob)
           ),
           asList(
                   new Organisation("Sales",
                           asList (
                                   new Engagement("Account Manager", james),
                                   new Engagement("Sales Assistant", sue)
                           ),
                           emptyList()
                   ),
                   new Organisation("Engineering",
                           asList (
                                   new Engagement("Software Engineer", aliyah),
                                   new Engagement("Operations", pierre),
                                   new Engagement("QA", sue)
                           ),
                           emptyList()
                   )
           )
   );




    Enterprise sme = new Enterprise(
            asList(
                    whole
            )
    );


    FindService findService;

    @BeforeEach
    public void setUp() {
        findService = new FindServiceImpl();
    }

    @Nested
    class FindPersonByName {

        @Nested
        class GivenAnEmptyEnterprise {
            Enterprise emptyEnterprise = new Enterprise(emptyList());

            @Test
            public void findsNoPeople() {
                assertTrue(findService.findPersonByName(emptyEnterprise, "any name").isEmpty());
            }
        }

        @Nested
        class GivenAPopulatedEnterprise {


            @Test
            public void exactMatchSuccess() {
                assertEquals(alice, findService.findPersonByName(sme, "Alice Arnold").get());
                assertEquals(sue, findService.findPersonByName(sme, "Sue Smith").get());
            }

            @Test
            public void caseInsensitiveMatchSuccess() {
                assertEquals(pierre, findService.findPersonByName(sme, "pierre martin").get());
            }

            @Test
            public void unknownMatchFailure() {
                assertTrue(findService.findPersonByName(sme, "Morticia Addams").isEmpty());
            }

            @Test
            public void partialMatchFailure() {
                assertTrue(findService.findPersonByName(sme, "alice").isEmpty());
            }
        }
    }

    @Nested
    class FindOrganisationsEngagingPerson {

        @Test
        public void findsEngagingOrganisations() {


            // Find organisations where James Jones is engagedz
            Collection<Organisation> engagingOrgs = findService.findOrganisationsEngagingPerson(sme, james);

            assertEquals(1, engagingOrgs.size());

            // Create the expected engaging organization
            Organisation expectedOrg = new Organisation("Sales",
                    asList(new Engagement("Account Manager", james),
                            new Engagement("Sales Assistant", sue)),
                    emptyList());

            assertTrue(engagingOrgs.contains(expectedOrg));
        }
        @Test
        public void findsEngagingOrganisationsNonChild() {


            // Find organisations where James Jones is engagedz
            Collection<Organisation> engagingOrgs = findService.findOrganisationsEngagingPerson(sme, alice);

            assertEquals(1, engagingOrgs.size());

            // Create the expected engaging organization
            Organisation expectedOrg = whole;


            assertTrue(engagingOrgs.contains(expectedOrg));
        }
        @Test
        public void noEngagingOrganisations() {
            Person john = new Person("John Doe");

            // Find organisations where John Doe is engaged
            Collection<Organisation> engagingOrgs = findService.findOrganisationsEngagingPerson(sme, john);

            assertTrue(engagingOrgs.isEmpty());
        }
    }
    @Nested
    class FindPeopleWithMultipleEngagements {

        @Test
        public void findsPeopleWithMultipleEngagements() {
            // Find people with multiple engagements
            Collection<Person> multipleEngagedPeople = findService.findPeopleWithMultipleEngagements(sme);

            assertEquals(1, multipleEngagedPeople.size());

            assertTrue(multipleEngagedPeople.contains(sue));
        }

        @Test
        public void noPeopleWithMultipleEngagements() {
            // Create an enterprise with no multiple-engaged people
            Enterprise noMultipleEngagementEnterprise = new Enterprise(
                    asList(
                            new Organisation("No Engagement Org",
                                    asList(
                                            new Engagement("Position A", alice)
                                    ),
                                    emptyList()
                            )
                    )
            );

            // Find people with multiple engagements
            Collection<Person> multipleEngagedPeople = findService.findPeopleWithMultipleEngagements(noMultipleEngagementEnterprise);

            assertTrue(multipleEngagedPeople.isEmpty());
        }

        @Test
        public void noPersonsInEnterprise() {
            // Create an enterprise with no persons
            Enterprise noPersonEnterprise = new Enterprise(emptyList());

            // Find people with multiple engagements
            Collection<Person> multipleEngagedPeople = findService.findPeopleWithMultipleEngagements(noPersonEnterprise);

            assertTrue(multipleEngagedPeople.isEmpty());
        }
    }


}