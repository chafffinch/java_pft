package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {

        super(wd);
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

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getName());
        type(By.name("lastname"), contactData.getFirstname());
        type(By.name("mobile"), contactData.getMobileTelephone());
        type(By.name("email"), contactData.getMail());
    }

    public void initContactModification() {
        click(By.name("edit"));
    }

    public void submitContactModification() {
        click(By.name("update"));

    }
}