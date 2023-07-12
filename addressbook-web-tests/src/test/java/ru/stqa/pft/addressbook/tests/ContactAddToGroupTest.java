package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertEquals;

public class ContactAddToGroupTest extends TestBase{

    @BeforeMethod
    public void checkForContactToExist(){
        if (app.db().contacts().size() == 0){
            app.goTo().homePage();
            app.contact().initContactCreation();
            app.contact().createContactWithGroup(new ContactData().withFirstName("marina").withLastName("alieva").withAddress("msk")
                    .withMobilePhone("977-302").withHomePhone("977-303").withWorkPhone("977-304")
                    .withPrimaryEmail("m@mail.ru").withSecondaryEmail("a@mail.ru").withThirdEmail("ma@mail.ru"));
        }
    }

    @Test
    public void testAddContactInGroup() {
        app.goTo().homePage();
        ContactData contact = app.db().contacts().iterator().next();
        if (checkForNotUsedGroups().size() == 0) {
            Groups groupsBeforeCreation = app.db().groups();
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
            Groups groupsAfterCreation = app.db().groups();
            List<GroupData> newGroup = new ArrayList<>(groupsAfterCreation);
            newGroup.removeAll(groupsBeforeCreation);
            app.goTo().homePage();
            int groupToUse = newGroup.iterator().next().getId();
            app.contact().addToGroup(contact.getId(), groupToUse);

            assertEquals(0, checkForNotUsedGroups().size());
        } else {
            Groups groupsBeforeAdditions = contact.getGroups();
            int groupToUse = checkForNotUsedGroups().iterator().next().getId();
            app.contact().addToGroup(contact.getId(), groupToUse);
            ContactData freshDataContact = app.db().contacts().iterator().next();
            Groups groupsAfterAdditions = freshDataContact.getGroups();

            assertEquals(groupsBeforeAdditions.size() + 1, groupsAfterAdditions.size());
        }
    }

    public List<GroupData> checkForNotUsedGroups() {
        ContactData contact = app.db().contacts().iterator().next();
        Groups groupsInContact = contact.getGroups();
        Groups allGroups = app.db().groups();
        List<GroupData> difference = new ArrayList<>(allGroups);
        difference.removeAll(groupsInContact);
        return difference;
    }
}