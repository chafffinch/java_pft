package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

import static java.lang.String.format;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getPrimaryEmail());
        type(By.name("email2"), contactData.getSecondaryEmail());
        type(By.name("email3"), contactData.getThirdEmail());
        attach(By.name("photo"), contactData.getPhoto());
        type(By.name("photo"), contactData.getPhotoPath());

        if(creation){
            new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void fillContactFormWithoutGroup(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getPrimaryEmail());
        type(By.name("email2"), contactData.getSecondaryEmail());
        type(By.name("email3"), contactData.getThirdEmail());
        attach(By.name("photo"), contactData.getPhoto());
        type(By.name("photo"), contactData.getPhotoPath());
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void checkerForContactExists(ContactData contactData) {
        isDisplayed(By.xpath(format("//tbody/tr/td[text()='%s']", contactData.getLastName())));
        isDisplayed(By.xpath(format("//tbody/tr/td[text()='%s']", contactData.getFirstName())));
        isDisplayed(By.xpath(format("//tbody/tr/td[text()='%s']", contactData.getAddress())));
        isDisplayed(By.xpath(format("//tbody/tr/td[text()='%s']", contactData.getMobilePhone())));
        isDisplayed(By.xpath(format("//tbody//tr//a[text()='%s']", contactData.getPrimaryEmail())));
    }

    public void initContactModificationById(int id) {
        wd.findElement(By.xpath("//input[@value='" + id + "']/../../td[8]")).click();
    }

    public void updateContactModification() {
        click(By.name("update"));
    }

    public void deleteContactBtn() {
        click(By.xpath("//*[@value='Delete']"));
    }

    public void deleteContact(ContactData deletedContact) {
        initContactModificationById(deletedContact.getId());
        deleteContactBtn();
    }

    public void createContactWithGroup(ContactData contactData) {
        fillContactForm(contactData, true);
        submitContactCreation();
        returnToHomePage();
    }

    public void createContactWithoutGroup(ContactData contactData) {
        fillContactFormWithoutGroup(contactData);
        submitContactCreation();
        returnToHomePage();
    }

    public void modify(ContactData contactData) {
        initContactModificationById(contactData.getId());
        fillContactForm(contactData, false);
        updateContactModification();
        returnToHomePage();
    }

    public boolean isThereAnyContact() {
        return isElementPresent(By.xpath("(//img[last()][@title='Edit'])[1]"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath("//*[@name='entry']"));
        for (WebElement element : elements) {
            String lastName = element.findElement(By.xpath("td[2]")).getText();
            String firstName = element.findElement(By.xpath("td[3]")).getText();
            String address = element.findElement(By.xpath("td[4]")).getText();
            String allPhones = element.findElement(By.xpath("td[6]")).getText();
            String allEmails = element.findElement(By.xpath("td[5]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData().withFirstName(firstName).withLastName(lastName).withId(id)
                    .withAddress(address).withAllPhones(allPhones).withAllEmails(allEmails));
        }
        return contacts;
    }

    public ContactData fullnamePhonesEmailsFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname)
                .withAddress(address).withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
                .withPrimaryEmail(email).withSecondaryEmail(email2).withThirdEmail(email3);
    }

    public void activateContactCheckbox(int id) {
        click(By.xpath(format("//input[@type='checkbox']/../input[@id='%d']", id)));
    }

    public void chooseTheGroupInAddDropDownById(int groupId){
        new Select(wd.findElement(By.name("to_group"))).selectByValue(format("%d", groupId));
    }

    public void chooseTheGroupInGroupDropDownById(int groupId){
        new Select(wd.findElement(By.name("group"))).selectByValue(format("%d", groupId));
    }

    public void addToGroupButton() {
        click(By.xpath("//input[@type='submit']"));
    }

    public void deleteFromGroupButton() {
        click(By.name("remove"));
    }

    public void addToGroup(int contactId, int groupId) {
        activateContactCheckbox(contactId);
        chooseTheGroupInAddDropDownById(groupId);
        addToGroupButton();
    }

    public void deleteFromGroup(int groupId, int contactId) {
        chooseTheGroupInGroupDropDownById(groupId);
        activateContactCheckbox(contactId);
        deleteFromGroupButton();
    }
}