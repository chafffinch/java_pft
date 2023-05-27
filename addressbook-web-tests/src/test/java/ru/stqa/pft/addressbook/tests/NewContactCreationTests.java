package ru.stqa.pft.addressbook.tests;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class NewContactCreationTests extends TestBase {
  @Test
  public void testNewContactCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    app.getNavigationHelper().gotoHomePage();
    int before = app.getContactHelper().getContactCount();

    app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "test4", "test5"), true);

    int after = app.getContactHelper().getContactCount();   //считаем количество контактов после создания
    Assert.assertEquals(after, before + 1);   }

}
