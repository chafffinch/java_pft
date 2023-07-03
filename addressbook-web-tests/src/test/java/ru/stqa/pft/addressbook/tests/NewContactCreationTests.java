package ru.stqa.pft.addressbook.tests;


import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class NewContactCreationTests extends TestBase {


    @Test// (enabled = false)
    public void testContactCreation() throws Exception {

        Contacts before = app.contact().allContacts();
        app.goTo().contact();
        ContactData contact = new ContactData().withFirstname("marina").withLastname("alieva");
        app.contact().createContact(contact);
        Assert.assertEquals(app.contact().getContactCount(),before.size()+1);
        Contacts after = app.contact().allContacts();

        assertThat(after, equalTo(before.withAdded(contact
                .withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    }


    @Test

    public void testBadContactCreation() throws Exception {

        Contacts before = app.contact().allContacts();
        app.goTo().contact();
        ContactData contact = new ContactData().withFirstname("marina '").withLastname("");
        app.contact().createContact(contact);
        Assert.assertEquals(app.contact().getContactCount(),before.size());
        Contacts after = app.contact().allContacts();

        assertThat(after, equalTo(before));

    }

}