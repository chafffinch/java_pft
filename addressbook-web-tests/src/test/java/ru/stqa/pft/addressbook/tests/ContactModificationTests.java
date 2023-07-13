package ru.stqa.pft.addressbook.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.ContactData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {
    @BeforeMethod
    public void ensurePreconditions(){
        if (app.db().contacts().size() == 0) {
            app.createGroupIfNot();
            app.contact().createContact(new ContactData().withFirstName("marina").withLastName("alieva").withAddress("msk")
                    .withEmail("m@mail.ru").withEmail2("a@mail.ru").withEmail3("ma@mail.ru")
                    .withHomePhone("977-303").withMobilePhone("977-302").withWorkPhone("977-304"));
            app.GoTo().HomePage();
        }
    }

    @Test(enabled = true)
    public void testContactModificationTests() throws Exception {
        Contacts before =  app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("marina").withLastName("alieva").withAddress("msk")
                .withEmail("m@mail.ru").withEmail2("a@mail.ru").withEmail3("ma@mail.ru")
                .withHomePhone("977-303").withMobilePhone("977-302").withWorkPhone("977-304");
        app.contact().modify(contact);
        app.GoTo().HomePage();
        assertThat(app.contact().count(), Matchers.equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyGroupListInUI();
    }
}