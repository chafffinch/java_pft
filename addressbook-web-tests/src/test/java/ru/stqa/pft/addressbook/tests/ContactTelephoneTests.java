package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactTelephoneTests extends TestBase {
    @Test
    public void testContactTelephones() {
        app.goTo().home();
        ContactData contact = app.contact().allContacts().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getNewAdress(), equalTo(contactInfoFromEditForm.getNewAdress()));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
    }


    private String mergePhones(ContactData contact) {
        return   Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()).stream()
                .filter(s -> ! s.equals("")).map(ContactPhoneTests::cleaned).collect(Collectors.joining("\n"));

    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s -> !s.equals("")))
                .map(ContactPhoneTests::cleanedAe)
                .collect(Collectors.joining("\n"));
    }
    public static String cleanedAe(String address) {
        return address.replaceAll("\\s", "");
    }

    public static String cleaned(String phone) {
        return  phone.replaceAll("\\s","").replaceAll("[-()]","");
    }
}