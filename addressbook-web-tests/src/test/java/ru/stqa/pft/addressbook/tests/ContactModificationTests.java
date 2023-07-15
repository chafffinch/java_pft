package ru.stqa.pft.addressbook.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

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
                    .withPrimaryEmail("m@test.ru").withSecondaryEmail("a@test.ru").withThirdEmail("ma@test.ru"));
        }
    }

    @Test
    public void testContactModification() {
        checkForContactAndGroupToExist();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contactData = new ContactData().withFirstName("Mmarina").withLastName("Malieva").withAddress("Mmsk")
                .withMobilePhone("8977-302").withHomePhone("8977-303").withWorkPhone("8977-304")
                .withPrimaryEmail("mm@test.ru").withSecondaryEmail("ma@test.ru").withThirdEmail("mma@test.ru")
                .withId(modifiedContact.getId());
        app.contact().modify(contactData);
        Contacts after = app.db().contacts();
        assertThat(after,
                equalTo(before.without(modifiedContact).withAdded(contactData)));
        verifyGroupListInUI();
    }
}