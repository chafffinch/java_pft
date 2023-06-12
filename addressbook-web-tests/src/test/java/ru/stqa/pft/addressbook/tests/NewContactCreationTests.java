package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class NewContactCreationTests extends TestBase {
    @Test
    public void testNewContactCreation() throws Exception {
        app.getContactHelper().createContact(new ContactData("marina", "alieva", "+79773023365", "furrycun11@gmail.com", "test1"), true);
}

}



