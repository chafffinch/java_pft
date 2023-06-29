package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;



public class NewContactCreationTests extends TestBase {


    @Test
    public void testContactCreation() throws Exception {

        app.getNavigationHelper().gotoContactPage();
        app.getContactHelper().createContact(new ContactData("marina", "alieva", "msk", "m@gmail.com"));

    }


}