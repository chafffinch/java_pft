package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void checkForContactToExist(){
        if (app.db().contacts().size() == 0){
            app.goTo().groupPage();
            if (app.db().groups().size() == 0){
                app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
            }
            app.goTo().homePage();
            app.contact().initContactCreation();
            app.contact().createContactWithGroup(new ContactData().withFirstName("marina").withLastName("alieva").withAddress("msk")
                    .withMobilePhone("977-302").withHomePhone("977-303").withWorkPhone("977-304")
                    .withPrimaryEmail("m@test.ru").withSecondaryEmail("a@test.ru").withThirdEmail("ma@test.ru"));
        }
    }

    @Test
    public void testDeleteContact() {
        checkForContactToExist();
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().deleteContact(deletedContact);
        app.goTo().homePage();
        Contacts after = app.db().contacts();

        assertEquals(after.size(), before.size() - 1);
        assertThat(after, equalTo(before.without(deletedContact)));
        verifyContactListInUI();
    }
}