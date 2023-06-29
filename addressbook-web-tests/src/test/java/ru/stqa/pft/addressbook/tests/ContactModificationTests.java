package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase{
    @Test

    public void testContactModification() {

        app.getNavigationHelper().gotoHome();
        if ( ! app.getContactHelper().isThereAContact()){
            app.getNavigationHelper().gotoContactPage();
            app.getContactHelper().addContact();
        }
        app.getContactHelper().editContact();
        app.getContactHelper().fillContactForm(new ContactData("firstname","lastname","address","email"));
        app.getContactHelper().submitContactModification();
        app.getGroupHelper().returnToHomePage();

    }


}