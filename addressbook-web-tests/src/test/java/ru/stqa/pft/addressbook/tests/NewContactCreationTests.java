package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class NewContactCreationTests extends TestBase {
    @Test
    public void testNewContactCreation() throws Exception {
        app.getNavigationHelper().gotoNewContactPage();
        app.getContactHelper().fillContactForm(new ContactData("test1", "test2", "test3", "test4", "test5"), true);
        app.getContactHelper().submitContactForm();
        app.getNavigationHelper().gotoHomePage();
    }

 }



