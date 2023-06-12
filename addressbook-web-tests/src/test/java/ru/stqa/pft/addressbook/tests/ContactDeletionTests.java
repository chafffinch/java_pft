package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{
  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    if(! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("marina", "alieva", "+79773023365", "furrycun11@gmail.com", "test1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteContact();
  }

}