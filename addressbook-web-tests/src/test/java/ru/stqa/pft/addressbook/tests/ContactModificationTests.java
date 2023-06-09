package ru.stqa.pft.addressbook.tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import org.testng.annotations.BeforeMethod;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withName("test1").withFirstname("test2"), true);
        }
    }

    @Test
    public void testContactModification() {
        Set<ContactData> before = app.contact().all();
        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId())
                .withname("test1").withFirstname("test2").withMobileTelephone("test3").withMail("test4");
        app.contact().modify(contact);
        Set<ContactData> after = app.contact().all()
                ;
        Assert.assertEquals(after.size(), before.size() );

        before.remove (modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}





