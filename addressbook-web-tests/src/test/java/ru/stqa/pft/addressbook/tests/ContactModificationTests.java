package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertEquals;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkForContactAndGroupToExist(){
        if (app.db().contacts().size() == 0){
            app.goTo().groupPage();
            if (app.db().groups().size() == 0){
                app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
            }
            app.goTo().homePage();
            app.contact().initContactCreation();
            app.contact().createContactWithGroup(new ContactData().withFirstName("marina").withLastName("alieva").withAddress("msk")
                    .withMobilePhone("977-302").withHomePhone("977-303").withWorkPhone("977-304")
                    .withPrimaryEmail("m@mail.ru").withSecondaryEmail("a@mail.ru").withThirdEmail("ma@mail.ru"));
        }
    }

    @Test
    public void testContactModification() {
        checkForContactAndGroupToExist();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contactData = new ContactData().withFirstName("marina").withLastName("alieva").withAddress("msk")
                .withMobilePhone("977-302").withHomePhone("977-303").withWorkPhone("977-304")
                .withPrimaryEmail("m@mail.ru").withSecondaryEmail("a@mail.ru").withThirdEmail("ma@mail.ru")
                .withId(modifiedContact.getId());
        app.contact().modify(contactData);
        Contacts after = app.db().contacts();

        assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contactData)));
        verifyContactListInUI();
    }
}