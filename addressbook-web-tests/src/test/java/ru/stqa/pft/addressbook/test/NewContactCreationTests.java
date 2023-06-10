package ru.stqa.pft.addressbook.test;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContacts() {
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/AvatarPhoto.png");
        list.add(new Object[]{new ContactData().withName("test1.1").withFirstname("test2.1")
                .withMobileTelephone("test3.1").withGroup("test5")});
        list.add(new Object[]{new ContactData().withName("test1.2").withFirstname("test2.2")
                .withMobileTelephone("test3.2").withGroup("test5")});
        list.add(new Object[]{new ContactData().withName("test1.3").withFirstname("test2.3")
                .withMobileTelephone("test3.3").withGroup("test5")});
        return list.iterator();
    }

    @Test (dataProvider = "validContacts")
    public void testNewContactCreation(ContactData contact) throws Exception {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        app.contact().create(contact, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
    }
}

