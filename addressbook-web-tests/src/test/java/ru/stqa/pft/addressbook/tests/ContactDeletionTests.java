package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;


@Test
public class ContactDeletionTests extends TestBase{


    public void testContactDeletion() {
        app.getNavigationHelper().gotoHome();
        if ( ! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoContactPage();
            app.getContactHelper().addContact();
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectContact();
        app.wd.switchTo().alert().accept();
        app.getNavigationHelper().gotoContactPage();

    }


}