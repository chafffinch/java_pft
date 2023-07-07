package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions () {
        if (app.contact().list().size() == 0) {
            Groups groups = app.db().groups();
            app.contact().create(new ContactData().withFirstname("marina").withLastname("m").withMiddlename("alieva")
                    .withMobilePhone("977-302").withEmail("m@mail.ru").withAddress("msk").inGroup(groups.iterator().next()));
        }
    }

    @Test(enabled = true)
    public void testContactModification() {
        app.goTo().homePage();
        ensurePreconditions();
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withFirstname("marina").withLastname("m").withMiddlename("alieva")
                .withMobilePhone("977-302").withEmail("m@mail.ru").withAddress("msk").withId(modifiedContact.getId());
        app.contact().modify(contact);
        assertThat(app().contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();

        MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.withOut(modifiedContact).withAdded(contact)));
        verifyContactListInUI(); //В конфигурации запуска -DverifyUI=true
    }


}