package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewContactCreationTests extends TestBase {
    @BeforeMethod
    public void ensurePrecondition() {
        app.goTo().GroupPage();
        if (app.db().groups().size() == 0) {
            app.group().create(new GroupData().withName("test2").withFooter("test1").withHeader("test3"));
        }
    }


    @DataProvider //XML
    public Iterator<Object[]> validContactsFromXml() throws IOException {
        try(BufferedReader reader = new BufferedReader( new FileReader(new File("src/test/resources/contact.xml")))){
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xStream = new XStream();
            xStream.processAnnotations(ContactData.class);
            List<ContactData> contacts = (List<ContactData>) xStream.fromXML(xml);
            return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider // JSON
    public Iterator<Object[]> validContactsFromJson() throws IOException {
        try(BufferedReader reader = new BufferedReader( new FileReader(new File("src/test/resources/contact.json")))){
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json,new TypeToken<List<ContactData>>(){}.getType());
            return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validContacts() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        File photo = new File("src/test/resources/AvatarPhoto.jpg");
        try(BufferedReader reader = new BufferedReader( new FileReader(new File("src/test/resources/contacts.csv")))){
            String line = reader.readLine();
            while (line != null) {
                String[] split = line.split(";");
                list.add(new Object[]{new ContactData().withFirstname(split[0]).withLastname(split[1])
                        .withAddress(split[2]).withEmail(split[3])
                        .withEmail2(split[4])
                        .withEmail3(split[5]).withHomePhone(split[6])
                        .withMobilePhone(split[7]).withWorkPhone(split[8]).withPhoto(photo)});
                line = reader.readLine();
            }
            return list.iterator();
        }
    }

    @Test (dataProvider = "validContactsFromJson")
    public void testContactCreation(ContactData contact)  {
        app.goTo().homePage();
        Contacts before = app.db().contacts();
        app.contact().create(contact);
        app.goTo().homePage();
        assertThat(app.contact().count(),equalTo(before.size() + 1));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream()
                .mapToInt((g) -> g.getId()).max().getAsInt()))));
        verifyContactListInUI();
    }




    @Test (enabled = false)
    public void testBadContactCreation() throws Exception {
        app.goTo().homePage();
        Contacts before = app.contact().all();
        ContactData contact = new ContactData().withFirstname("test2'").withLastname("testov")
                .withAddress("SAINT-P").withEmail("null@null.com").withEmail2("null@gmail.com")
                .withEmail3("null@yandex.ru").withHomePhone("111")
                .withMobilePhone("222").withWorkPhone("333");

        app.contact().create(contact);
        app.goTo().homePage();
        assertThat(app.contact().count(),equalTo(before.size()));
        Contacts after = app.contact().all();
        assertThat(after, equalTo(before));
    }
}