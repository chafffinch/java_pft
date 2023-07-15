package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader
                (System.getProperty("file.contactXML", "./src/test/resources/contact.xml")))) {
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.allowTypesByWildcard(new String[]{
            });
            xstream.processAnnotations(ContactData.class);
            List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader
                (System.getProperty("file.contactJSON", "./src/test/resources/contact.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
            }.getType());
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @BeforeMethod
    public void checkForGroupToExist(){
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
        }
    }

    @Test(dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contactData) {
        app.goTo().homePage();
        Contacts before = app.db().contacts();
        app.contact().initContactCreation();
        app.contact().createContactWithGroup(contactData);
        app.contact().checkerForContactExists(contactData);
        Contacts after = app.db().contacts();

        assertThat(after.size(), equalTo(before.size() + 1));
        assertThat(after, equalTo(before.withAdded(contactData.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
        verifyContactListInUI();
    }
}