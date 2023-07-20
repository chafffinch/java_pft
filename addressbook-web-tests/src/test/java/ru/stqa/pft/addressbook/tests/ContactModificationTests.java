package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();
        if (app.db().contacts().size() == 0) {
            app.goTo().GroupPage();
            if (app.db().groups().size() == 0) {
                app.group().create(new GroupData().withName("test"));
                app.goTo().homePage();
            }
            app.contact().create(new ContactData().withFirstname("test2").withLastname("test3")
                    .withAddress("msk").withEmail("m@mail.com").withHomePhone("977-302")
                    .withMobilePhone("977-303").withWorkPhone("977-304"));
            app.goTo().homePage();
        }
    }
    @Test
    public void testUserModification() throws Exception {
        Contacts before = app.db().contacts();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId())
                .withFirstname("marinaM").withLastname("alievaM").withAddress("mskM")
                .withEmail("mm@mail.com").withEmail2("ma@mail.com")
                .withEmail3("mmm@mail.com").withHomePhone("8977-302").withMobilePhone("8977-303")
                .withWorkPhone("8977-304").withHomePhone("8977-305");

        app.contact().modify(contact);
        assertThat(app.contact().count(),equalTo(before.size()));
        Contacts after = app.db().contacts();

        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }
}