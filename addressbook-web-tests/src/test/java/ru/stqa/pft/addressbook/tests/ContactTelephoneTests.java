package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactTelephoneTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withName("test1").withFirstname("test2").withMobileTelephone("test3")
                    .withHomePhone("test4"), true);
        }
    }

    @Test
    public void testContactTelephone() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFoEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFoEditForm)));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobileTelephone(), contact.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactTelephoneTests::cleaned)
                .collect(Collectors.joining("\n"));

        public static String cleaned (String phone){

            return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
        }
    }
}