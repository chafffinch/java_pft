package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroup extends TestBase{

    private Contacts contacts;
    private Groups groups;

    @BeforeClass
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }

        if (app.db().contacts().size() == 0) {
            File photo = new File("src/test/resources/Ferma.png");
            app.goTo().homePage();
            app.contact().create(new ContactData().withName("ivan")
                    .withFirstname("Gorelkin").withPhoto(photo), true);
        }
        ContactData contact = app.db().contacts().iterator().next();
        if (contact.getGroups().size() == 0) {
            GroupData group = app.db().groups().iterator().next();
            app.goTo().homePage();
            app.contact().addToGroup(contact, group);
        }
    }

    @Test
    public void testContactRemoveFromGroup() {
        contacts = app.db().contacts();
        groups = app.db().groups();
        ContactData contactRemove = contacts.iterator().next();
        GroupData groupRemove = groups.iterator().next();
        app.goTo().homePage();
        app.contact().contactGroupPage(contactRemove);
        app.contact().removeFromGroup(contactRemove);

        app.db().cycleByGroups(groupRemove);
        app.db().cycleByContacts(contactRemove);

        assertThat(contactRemove.getGroups(), not(contactRemove));
        assertThat(groupRemove.getContacts(), not(groupRemove));
    }
}