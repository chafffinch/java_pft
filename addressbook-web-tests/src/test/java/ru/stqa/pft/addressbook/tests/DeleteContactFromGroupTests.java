package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

    public class DeleteContactFromGroupTests extends TestBase {

        @BeforeMethod
        public void doPreconditions(){
            if (app.db().contacts().size() == 0){
                app.goTo().homePage();
                app.contact().initContactCreation();
                app.contact().createContactWithoutGroup(new ContactData().withFirstName("marina").withLastName("alieva").withAddress("msk")
                        .withMobilePhone("977-302").withHomePhone("977-303").withWorkPhone("977-304")
                        .withPrimaryEmail("m@test.ru").withSecondaryEmail("a@test.ru").withThirdEmail("ma@test.ru"));
            }

            ContactData contact = app.db().contacts().iterator().next();
            if (contact.getGroups().size() == 0) {
                Groups groupsBeforeCreation = app.db().groups();
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
                app.goTo().homePage();
                Groups groupsAfterCreation = app.db().groups();
                List<GroupData> newGroup = new ArrayList<>(groupsAfterCreation);
                newGroup.removeAll(groupsBeforeCreation);
                int groupToUse = newGroup.iterator().next().getId();
                app.contact().addToGroup(contact.getId(), groupToUse);
            }
        }

        @Test
        public void testDeleteContactFromGroup() {
            app.goTo().homePage();
            ContactData contact = app.db().contacts().iterator().next();
            Groups groupsBeforeDeletion = contact.getGroups();
            int groupId = contact.getGroups().iterator().next().getId();
            app.contact().deleteFromGroup(groupId, contact.getId());
            ContactData freshDataContact = app.db().contacts().iterator().next();
            Groups groupsAfterDeletion = freshDataContact.getGroups();

            assertEquals(groupsBeforeDeletion.size() - 1, groupsAfterDeletion.size());
        }
    }