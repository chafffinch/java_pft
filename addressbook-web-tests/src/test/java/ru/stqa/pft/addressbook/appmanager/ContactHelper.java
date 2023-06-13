package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends GroupHelper {

    private boolean creation;
    private WebElement element;

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
        attach(By.name("photo"), contactData.getPhoto());
    }

    public void selectContact(int index) {
        wd.findElements(By.name("selected[]")).get(index).click();
    }

    public void selectContactById(int id) {
        element = wd.findElement(By.cssSelector("input[value='" + id + "']"));
        element.isSelected();
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void deleteSelectContact() {
        click(By.xpath("//div[2]/input"));
    }


    public void editContact(int id) {
        element = wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']"));
        element.isSelected();
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void submitContactModification() {
        click(By.xpath("//input[22]"));
    }


    public void createContact(ContactData contact) {

        fillContactForm(contact);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();
    }

    public void modifyContacts(ContactData contact) {

        editContact(contact.getId());
        fillContactForm(contact);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(int index) {
        selectContact(index);
        deleteSelectContact();
        wd.switchTo().alert().accept();
    }


    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectContact();
        wd.switchTo().alert().accept();
        contactCache = null;
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }


    public void add() {

        fillContactForm(new ContactData().withFirstname("marina").withLastname("alieva").withAddress("moscow"));
        submitContactCreation();
        returnToHomePage();
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> getContactList() {
        List<ContactData> contacts = new ArrayList<ContactData>();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name = 'entry']"));
        for (WebElement element : elements) {
            List<WebElement> rowElements = element.findElements(By.tagName("td"));
            String name = rowElements.get(2).getText();
            String lastname = rowElements.get(1).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            ContactData contact = new ContactData().withId(id).withFirstname(name).withLastname(lastname);
            contacts.add(contact);
        }
        return contacts;
    }

    private Contacts contactCache = null;

    public Contacts allContacts() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name = 'entry']"));
        for (WebElement element : elements) {
            List<WebElement> rowElements = element.findElements(By.tagName("td"));
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            String name = rowElements.get(2).getText();
            String lastname = rowElements.get(1).getText();
            String allPhones = rowElements.get(5).getText();
            String allEmails = rowElements.get(4).getText();
            ContactData contact = new ContactData().withId(id).withFirstname(name).withLastname(lastname)
                    .withAllPhones(allPhones).withAllEmails(allEmails);
            contactCache.add(contact);
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String telhome = wd.findElement(By.name("home")).getAttribute("value");
        String mobilPhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");

        wd.navigate().back();

        return new ContactData().withId(contact.getId()).withFirstname(firstname)
                .withLastname(lastname).withEmail(email).withNewAdress(address).withMobilePhone(mobilPhone).withHomePhone(telhome).
                withWorkPhone(workPhone).withEmail2(email2).withEmail3(email3);
    }

    public void initContactModificationById(int id) {

        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }

}