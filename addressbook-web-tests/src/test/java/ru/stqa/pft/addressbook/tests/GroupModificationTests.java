package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase{

    @BeforeMethod
    public void ensurePrecondition() {
        if (app.db().groups().size() == 0){
            app.goTo().GroupPage();
            app.group().create(new GroupData().withName("Test1"));
        }
    }
    @Test
    public void testGroupModification(){
        Groups before =app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().withId((modifiedGroup.getId())).withName("Test1")
                .withHeader("Test2").withFooter("Test3");
        app.goTo().GroupPage();
        app.group().modify(group);
        assertThat(app.group().Count(),equalTo(before.size()));
        Groups after =app.db().groups();
        assertThat(after, CoreMatchers.equalTo(before.without(modifiedGroup).withAdded(group)));
        verifyGroupListInUI();
    }
}