package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions () {
        if (app.contact().list().size() == 0) {
            Groups groups = app.db().groups();
            app.contact().create(new ContactData().withFirstname("marina").withLastname("m").withMiddlename("alieva")
                    .withMobilePhone("977-302").withHomePhone("977-303").withWorkPhone("977-304")
                    .withAddress("msk").inGroup(groups.iterator().next()));
        }
    }

    @Test(enabled = true)
    public void testContactDeletion() throws Exception {
        app.goTo().homePage();
        ensurePreconditions();
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app().contact().count(), equalTo(before.size() - 1));
        Contacts after = app.db().contacts();

        before.remove(deletedContact);
        assertThat(after, equalTo(before.withOut(deletedContact)));
        verifyContactListInUI(); //В конфигурации запуска -DverifyUI=true
    }



}