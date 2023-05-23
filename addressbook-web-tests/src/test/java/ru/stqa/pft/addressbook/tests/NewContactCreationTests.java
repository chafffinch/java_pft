package ru.stqa.pft.addressbook.tests;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class NewContactCreationTests extends TestBase {

  @Test
  public void testNewContactCreation() throws Exception {
    app.getNavigationHelper().gotoNewContactPage();
    app.getContactHelper().fillContactForm(new ContactData("marina", "alieva", "+79773023365", "furrycun11@gmail.com"));
    app.getContactHelper().submitContactForm();
    app.getNavigationHelper().gotoHomePage();
  }

}
