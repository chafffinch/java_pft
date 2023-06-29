package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends GroupHelper {

    private boolean creation;

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("email"), contactData.getEmail());

//        if (creation) {
//            new Select(wd.findElement(By.name("selected[]"))).selectByVisibleText(contactData.getFirstname());
//        } else {
//            Assert.assertFalse(isElementPresent(By.linkText("add new")));
//        }
    }
    public void selectContact () {
        click(By.name("selected[]"));
    }

    public void deleteSelectContact () {
        click(By.xpath("//div[2]/input"));
    }

    public void editContact () {
        click(By.xpath("//table[@id='maintable']/tbody/tr[2]/td[8]/a/img"));
    }

    public void submitContactModification () {
        click(By.xpath("//input[22]"));
    }


    public void createContact(ContactData contact) {

        fillContactForm(contact);
        submitContactCreation();
        returnToHomePage();
    }

    public boolean isThereAContact() {
        return  isElementPresent(By.name("selected[]"));
    }


    public void addContact() {

        fillContactForm(new ContactData("marina", "alieva", "msk", "m@gmail.com"));
        submitContactCreation();
        returnToHomePage();
    }
}