package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactTelephoneTests extends TestBase {

    @Test
    public void testContactTelephone() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFoEditForm = app.contact().infoFromEditForm(contact);
    }
}