package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactMailAndAddressTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withName("marina").withFirstname("alieva").withMobileTelephone("977-302")
                    .withHomePhone("977-303").withGroup("test1").withAddress("msk").withMail("m@mail.ru").withMail2("a@mail.com")
                    .withMail3("fjjdf@mail.ru"), true);
        }
    }

    @Test
    public void testContactTelephone() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllMail(), equalTo(mergeMail(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getAllPhones(), contact.getWorkPhone()).stream()
                .filter(s -> !s.equals("")).map(ContactMailAndAddressTests::cleaned).collect(Collectors.joining("\n"));

    }

    private String mergeMail(ContactData contact) {
        return Arrays.asList(contact.getMail(), contact.getMail2(), contact.getMail3())
                .stream().filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("/n"));
    }

    public static String cleanedAe(String address) {
        return address.replaceAll("\\s", "");
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}