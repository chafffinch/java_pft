package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DeleteContactFromGroupTests extends  TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().groups().size() == 0) {
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test2")
                    .withFooter("test3"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstname("marina").withLastname("alieva")
                    .withAddress("msk").withEmail("m@mail.com").withHomePhone("977-302")
                    .withMobilePhone("977-303").withWorkPhone("977-304"));
        }
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        for(ContactData contact: contacts){
            if(contact.getGroups().size() == 0){
                app.contact().addingGroup(contact, groups.iterator().next());
                app.goTo().homePage();
            }
            break;
        }
    }
    @Test
    public void testRemoveFromGroup() {
        Contacts contacts = app.db().contacts();
        ContactData selectContact = getSelectContact(contacts);
        GroupData selectGroup = selectContact.getGroups().iterator().next();
        int beforeDeletingGroup = selectContact.getGroups().size();
        app.contact().removeContactFromGroup(selectGroup, selectContact);
        contacts = app.db().contacts();
        ContactData findContact = getFindContact(contacts, selectContact.getId());
        int afterDeletingGroup = findContact.getGroups().size();
        assertThat(afterDeletingGroup, equalTo(beforeDeletingGroup - 1));
    }
    public ContactData getSelectContact(Contacts contact) {
        return contact.stream().filter((c) -> c.getGroups().size() > 0).findFirst().get();
    }
    private ContactData getFindContact(Contacts contact, int contactId) {
        return contact.stream().filter((c) -> c.getId() == contactId).findFirst().get();
    }

}