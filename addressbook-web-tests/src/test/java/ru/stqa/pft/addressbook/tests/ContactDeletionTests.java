package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase{

  @Test
   app.getNavigationHelper().gotoGroupPage();
    if (! app.getGroupHelper().isThereAGroup()) {
    app.getGroupHelper().createGroup(new GroupData("test1", null, null));
  }

  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().gotoHomePage();
    if(! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("test1", "test2", "test3", "test4", "test5"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteContact();
  }


}