package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactAddToGroupTest extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();
        if (app.db().contacts().size() == 0){
            app.goTo().GroupPage();
            if (app.db().groups().size() == 0){
                app.group().create(new GroupData().withName("test2").withFooter("test1")
                        .withHeader("test3"));
                app.goTo().homePage();
            }
            app.contact().create(new ContactData().withFirstname("marina").withLastname("alieva")
                    .withAddress("msk").withEmail("m@mail.com").withHomePhone("977-302")
                    .withMobilePhone("977-303").withWorkPhone("977-304"));
        }
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        int contactFullSize = 0;
        for(ContactData contact: contacts){
            if (contact.getGroups().size() == groups.size()) {
                contactFullSize++;
            }
            if (contactFullSize == contacts.size()) {
                app.goTo().GroupPage();
                app.group().create(new GroupData().withName("test1").withHeader("test2")
                        .withFooter("test3"));
                app.goTo().homePage();
            }
        }
    }
    @Test
    public void testAddContactToGroup() {
        Groups groups = app.db().groups();
        Contacts contacts = app.db().contacts();
        ContactData selectContact = getSelectContact(contacts, groups.size());
        GroupData selectGroup = getSelectGroup(groups,selectContact);
        int beforeAddingGroup = selectContact.getGroups().size();
        app.contact().addingGroup(selectContact, selectGroup);
        contacts = app.db().contacts();
        ContactData findContact = getFindContact(contacts, selectContact.getId());
        int afterAddingGroup = findContact.getGroups().size();
        assertThat(afterAddingGroup, equalTo(beforeAddingGroup + 1));
    }
    public ContactData getSelectContact (Contacts contact, int groupsSize){
        return contact.stream().filter((c) -> c.getGroups().size() != groupsSize).findFirst().get();
    }
    public  GroupData getSelectGroup (Groups groupsAll,ContactData contact){
        groupsAll.removeAll(contact.getGroups());
        return groupsAll.iterator().next();
    }
    private ContactData getFindContact(Contacts contact, int contactId) {
        return contact.stream().filter((c) -> c.getId() == contactId).findFirst().get();
    }
}