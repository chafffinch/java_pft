package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDeletionTests extends TestBase {
    File photo = new File("src/test/resources/AvatarPhoto.jpg");

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) { //данные из базы (.db().)
            app.createGroupIfNot();
            app.contact().createContact(new ContactData().withFirstName("marina").withLastName("alieva").withAddress("msk")
                    .withEmail("m@mail.ru").withEmail2("a@mail.ru").withEmail3("ma@mail.ru")
                    .withHomePhone("977-303").withMobilePhone("977-302").withWorkPhone("977-304").withPhoto(photo));
            app.GoTo().HomePage();
        }
    }

    @Test(enabled = true)
    public void testContactDeletionTests() throws Exception {
        Contacts before = app.db().contacts();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.GoTo().HomePage();
        assertThat(app.contact().count(), equalTo(before.size() - 1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(deletedContact)));
    }
}