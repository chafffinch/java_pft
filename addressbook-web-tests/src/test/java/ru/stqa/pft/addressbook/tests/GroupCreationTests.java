package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {
    @DataProvider
    public Iterator<Object[]> validGroupsFromXml() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("./src/test/resources/groups.xml")))) {
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.allowTypesByWildcard(new String[]{
            });
            xstream.processAnnotations(GroupData.class);
            List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
            return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @DataProvider
    public Iterator<Object[]> validGroupsFromJson() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("./src/test/resources/groups.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {
            }.getType());
            return groups.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
        }
    }

    @Test(dataProvider = "validGroupsFromJson")
    public void testGroupCreation(GroupData groupData) {
        app.goTo().groupPage();
        Groups before = app.db().groups();
        app.group().create(groupData);
        app.wd.findElement(By.xpath(format("//span[text()='%s']", groupData.getName()))).isDisplayed();

        assertThat(app.group().count(), equalTo(before.size() + 1));

        Groups after = app.db().groups();

        assertThat(after,
                equalTo(before.withAdded(groupData.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

        verifyGroupListInUI();
    }

    @Test
    public void testBadGroupCreation() {
        app.goTo().groupPage();
        Groups before = app.db().groups();
        GroupData groupData = new GroupData().withName("test2'");
        app.group().create(groupData);

        assertThat(app.group().count(), equalTo(before.size()));

        Groups after = app.db().groups();

        assertThat(after, equalTo(before));
        verifyGroupListInUI();
    }
}