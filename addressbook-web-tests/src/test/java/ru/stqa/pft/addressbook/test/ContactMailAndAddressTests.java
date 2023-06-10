package ru.stqa.pft.addressbook.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactMailAndAddressTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withName("marina").withFirstname("alieva").withMobileTelephone("02-3365")
                    .withHomePhone("02-3366").withGroup("test5").withAddress("balashiha").withMail("furrycun11@gmail.ru").withMail2("white.panda2004@yandex.ru")
                    .withMail3("ppppp@mail.ru"), true);
        }
    }

    @Test
    public void testContactTelephone() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFoEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllMail(), equalTo(mergeMail(contactInfoFoEditForm)));
        assertThat(contact.getAddress(), equalTo(megaAddress(contactInfoFoEditForm)));
    }

    private String mergeMail(ContactData contact) {
        return Arrays.asList(contact.getMail(), contact.getMail2(), contact.getMail3())
                .stream().filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("\n"));
    }
    private String megaAddress(ContactData contact) {
        return Arrays.asList(contact.getAddress())
                .stream().filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("\n"));
    }
}