package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

@Test
public class ContactDeletionTests extends TestBase{

  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(
              new ContactData("test1", "test2", "test3", "test4", "test5"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteContact();
  }

}