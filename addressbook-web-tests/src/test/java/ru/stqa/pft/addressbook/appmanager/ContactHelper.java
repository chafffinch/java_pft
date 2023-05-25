package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {

        super(wd);
    }

    public void gotoNewContactPage() {
        click(By.linkText("add new"));
    }
    public void returnToHomePage() {
        click(By.linkText("home page"));
    }
    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
    }
    public void selectContact() {
        click(By.name("selected[]"));
    }

    public void submitContactForm() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getName());
        type(By.name("lastname"), contactData.getFirstname());
        type(By.name("mobile"), contactData.getMobileTelephone());
        type(By.name("email"), contactData.getMail());
    }

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        }   else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }
    public void initContactModification() {
        click(By.name("edit"));
    }

    public void submitContactModification() {
        click(By.name("update"));

    }

    public void createContact(ContactData contact, boolean b) {
        gotoNewContactPage();
        fillContactForm(contact, b);
        submitContactForm();
        returnToHomePage();
    }
    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }
}