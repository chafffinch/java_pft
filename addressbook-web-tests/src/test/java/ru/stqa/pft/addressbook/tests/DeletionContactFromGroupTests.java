package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;

import static org.testng.AssertJUnit.assertEquals;

public class DeletionContactFromGroupTests extends TestBase {

    File photo = new File("src/test/resources/AvatarPhoto.jpg");

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().verifyContactInGroup().size() == 0) {
            app.createGroupIfNot();
            app.contact().createContact(new ContactData().withFirstName("000").withLastName("000").withAddress("000")
                    .withEmail("000").withEmail2("000").withEmail3("000")
                    .withHomePhone("000").withMobilePhone("000").withWorkPhone("000"));
            app.contact().gotoHomePage();
        }
    }

    @Test
    public void testDeletionContactFromGroup() throws Exception {
        ContactData before = app.db().contactWithGroup();
        GroupData group = before.getGroups().iterator().next();
        Groups groupsBeforeDeletion = before.getGroups();
        app.contact().gotoHomePage();
        app.contact().getGroupData(group);
        app.contact().selectContact(before);
        app.contact().removeContactFromGroup();
        ContactData freshDataContact = app.db().contacts().iterator().next();
        Groups groupsAfterDeletion = freshDataContact.getGroups();
        assertEquals(groupsBeforeDeletion.size() - 1, groupsAfterDeletion.size());
        verifyContactListInUI();
    }
}