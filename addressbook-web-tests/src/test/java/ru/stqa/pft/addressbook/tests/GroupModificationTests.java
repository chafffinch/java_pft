package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.Groups;
import ru.stqa.pft.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions(){
        if (app.group().all().size() == 0){
            app.GoTo().GroupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }
    @Test
    public void testGroupModification() throws Exception {
        Groups before =  app.db().groups();
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData().
                withId(modifiedGroup.getId()).withName("test1").withHeader("test2").withFooter("test3");
        app.GoTo().GroupPage();
        app.group().modify(group);
        assertThat(app.group().Count(), equalTo(before.size())); //не нужная проверка (выполняется дольше)
        Groups after =  app.db().groups();
        assertThat(after, CoreMatchers.equalTo(before.without(modifiedGroup).withAdded(group)));
        verifyGroupListInUI();
    }

}