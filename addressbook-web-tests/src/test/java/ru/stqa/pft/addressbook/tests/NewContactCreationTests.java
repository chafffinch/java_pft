package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;


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
    public Iterator<Object[]> validContactsXml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.processAnnotations(ContactData.class); // обработка аннотации в классе ContactData
            List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
            return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validContactsJson() throws IOException {   // итератор массивов объектов
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
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
    @Test (dataProvider = "validContactsJson")
    public void testContactCreation(ContactData contact) throws Exception {

        Contacts before = app.contact().allContacts();
        app.goTo().contact();
        // File photo = new File("src/test/resources/AvatarPhoto.jpg");
        //ContactData contact = new ContactData().withFirstname("marina").withLastname("alieva").withPhoto(photo);
        app.contact().createContact(contact);
        Assert.assertEquals(app.contact().getContactCount(), before.size() + 1);
        Contacts after = app.contact().allContacts();

        assertThat(after, equalTo(before.withAdded(contact
                .withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));

    }


//    @Test
//    public void testCurrentDir() {
//        File currentDir = new File(".");
//        System.out.println(currentDir.getAbsoluteFile());
//       File photo = new File("src/test/resources/AvatarPhoto.jpg");
//        System.out.println(photo.getAbsolutePath());
//        System.out.println(photo.exists());
//    }
    // File photo = new File(currentDir.getAbsolutePath() + "/src/test/resources/Screen.png");

    @Test (enabled = false)

    public void testBadContactCreation() throws Exception {

        Contacts before = app.contact().allContacts();
        app.goTo().contact();
        ContactData contact = new ContactData().withFirstname("marin'").withLastname("");
        app.contact().createContact(contact);
        Assert.assertEquals(app.contact().getContactCount(), before.size());
        Contacts after = app.contact().allContacts();

        assertThat(after, equalTo(before));

    }

}