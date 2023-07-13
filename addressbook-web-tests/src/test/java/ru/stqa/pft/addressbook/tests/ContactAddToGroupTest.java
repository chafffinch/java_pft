package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.testng.AssertJUnit.assertTrue;

public class ContactAddToGroupTest extends TestBase {

    File photo = new File("src/test/resources/AvatarPhoto.jpg");

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0 | app.db().groups().size() == 0 | app.db().verifyContactNotInGroup().size() != 0) { //данные из базы (.db().)
            app.createGroupIfNot();
            ContactData contact = new ContactData();
            app.contact().createWithoutGroup(contact.withFirstName("marina").withLastName("alieva").withAddress("msk")
                    .withEmail("m@mail.ru").withEmail2("a@mail.ru").withEmail3("ma@mail.ru")
                    .withHomePhone("977-303").withMobilePhone("977-302").withWorkPhone("977-304"));
            app.contact().gotoHomePage();
        }
    }

    @Test
    public void testAddContactInGroup() {
        ContactData before = app.db().contactWithoutGroup();
        Groups groups = app.db().groups();
        GroupData group = groups.iterator().next();
        app.contact().gotoHomePage();
        app.contact().selectContactWithoutGroup(before);
        app.contact().selectGroup(group);
        app.contact().addContactToGroup();
        ContactData after = app.db().contactById(before.getId());
        assertTrue(after.getGroups().contains(group));
        verifyContactListInUI();
    }
}