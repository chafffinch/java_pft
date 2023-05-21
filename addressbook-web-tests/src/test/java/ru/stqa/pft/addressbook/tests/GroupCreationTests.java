package ru.stqa.pft.addressbook;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.tests.TestBase;

public class GroupCreationTests extends TestBase {


  @Test
  public void testGroupCreation() throws Exception {
    gotoGroupPage();
    initGroupCreation();
    fillGroupForm(new GroupData("test1", "test2", "test3"));
    submitGroupCreation();
    returnToGroupPage();

  }

}