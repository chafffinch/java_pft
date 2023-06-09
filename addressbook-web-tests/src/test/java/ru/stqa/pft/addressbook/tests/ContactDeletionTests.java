package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class ContactDeletionTests extends TestBase{

  @BeforeMethod
  public void ensurePrecondition() {
    app.goTo().homePage();
    if (! app.contact().isThereAContact()) {
      app.contact().create(new ContactData().withName("test1").withFirstname("test2"), true);
    }
  }

  @Test
  public void testContactDeletion() throws Exception {
    Set<ContactData> before = app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    Set<ContactData> after = app.contact().all();
    Assert.assertEquals(after.size(), before.size() -1);

    before.remove(deletedContact);
    Assert.assertEquals(before, after);
  }

}