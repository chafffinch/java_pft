package ru.stqa.pft.addressbook.tests;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class NewContactCreationTests extends TestBase {

  @Test
  public void testNewContactCreation() throws Exception {
    app.gotoAddNewPage();
    app.fillContactForm(new ContactData("test1", "test2", "test3"));
    app.submitContactCreation();
    app.returnToHomePage();
  }

}
