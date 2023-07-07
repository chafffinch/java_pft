package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import java.io.File;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class ContactAddToGroupTest extends TestBase {

    @BeforeClass
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }

        if (app.db().contacts().size() == 0) {
            File photo = new File("src/test/resources/AvatarPhoto.png");
            app.goTo().homePage();
            app.contact().create(new ContactData().withName("marina")
                    .withFirstname("alieva").withPhoto(photo), true);
        }
    }


    @Test
    public void testContactAddToGroup() {
        ContactData contactBefore = app.db().contacts().iterator().next();
        GroupData groupBefore = app.db().groups().iterator().next();
        app.goTo().homePage();
        app.contact().addToGroup(contactBefore, groupBefore);
        app.goTo().homePage();
        app.contact().showAllContact();

        ContactData contactAfter = app.db().contacts().iterator().next();
        GroupData groupAfter= app.db().groups().iterator().next();

        assertThat(contactAfter.getGroups(), hasItem(groupBefore));
        assertThat(groupAfter.getContacts(), hasItem(contactBefore));
    }
}



