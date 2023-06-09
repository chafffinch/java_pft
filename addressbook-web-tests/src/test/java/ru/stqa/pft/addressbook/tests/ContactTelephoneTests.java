package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactTelephoneTests extends TestBase {

    @Test
    public void testContactTelephone() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFoEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getHomePhone(), equalTo(cleaned(contactInfoFoEditForm.getHomePhone())));
        assertThat(contact.getMobileTelephone(), equalTo(cleaned(contactInfoFoEditForm.getMobileTelephone())));
        assertThat(contact.getWorkPhone(), equalTo(cleaned(contactInfoFoEditForm.getWorkPhone())));

    }

    public String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}