package ru.stqa.pft.addressbook.tests;


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
        list.add(new Object[]{new ContactData().withName("marina1").withFirstname("alieva2")
                .withMobileTelephone("977-302").withGroup("test1")});
        list.add(new Object[]{new ContactData().withName("marina2").withFirstname("alieva2")
                .withMobileTelephone("977-303").withGroup("test1")});
        list.add(new Object[]{new ContactData().withName("marina3").withFirstname("alieva3")
                .withMobileTelephone("977-304").withGroup("test1")});
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