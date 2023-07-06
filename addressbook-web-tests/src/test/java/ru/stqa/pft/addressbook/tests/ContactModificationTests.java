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
        if (app.db().contacts().size() == 0) { //данные из базы (.db().)
            app.createGroupIfNot();
            app.contact().createContact(new ContactData().withFirstName("000").withLastName("000").withAddress("000")
                    .withEmail("000").withEmail2("000").withEmail3("000")
                    .withHomePhone("000").withMobilePhone("000").withWorkPhone("000"));
            app.GoTo().HomePage();
        }
    }

    @Test(enabled = true)
    public void testContactModificationTests() throws Exception {
        Contacts before =  app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId()).withFirstName("000").withLastName("000").withAddress("000")
                .withEmail("000").withEmail2("000").withEmail3("000")
                .withHomePhone("000").withMobilePhone("000").withWorkPhone("000");
        app.contact().modify(contact);
        app.GoTo().HomePage();
        assertThat(app.contact().count(), Matchers.equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyGroupListInUI();
    }
}