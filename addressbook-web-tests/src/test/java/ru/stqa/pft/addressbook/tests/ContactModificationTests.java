package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData().withName("marina").withFirstname("alieva"), true);
        }
    }

    @Test
    public void testContactModificationTests(){
        List<ContactData> before = app.contact().list();
        int index = before.size() -1;
        ContactData contact = new ContactData().withId(before.get(index).getId())
                .withName("marina").withFirstname("alieva").withMobileTelephone("+79773023365").withMail("furrycun11@gmail.com");
        app.contact().modify(index, contact);
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size() );

        before.remove (index);
        before.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }


}



