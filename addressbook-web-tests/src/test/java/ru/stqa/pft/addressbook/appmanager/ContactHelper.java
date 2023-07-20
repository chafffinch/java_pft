package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase {

    ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData userData, boolean creation) {
        type(By.name("firstname"), userData.getFirstname());
        type(By.name("lastname"), userData.getLastname());
        type(By.name("address"), userData.getAddress());
        type(By.name("email"), userData.getEmail());
        type(By.name("email2"), userData.getEmail2());
        type(By.name("email3"), userData.getEmail3());
        type(By.name("home"), userData.getHomePhone());
        type(By.name("mobile"), userData.getMobilePhone());
        type(By.name("work"), userData.getWorkPhone());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByIndex(1);
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }


    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectById(int id) {
        wd.findElement((By.cssSelector("input[value = '" + id + "']"))).click();
    }

    public void modifyById(int id) {
        wd.findElement((By.cssSelector("a[href='edit.php?id=" + id + "']"))).click();
    }

    public void delete() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void update() {
        click(By.xpath("//div[@id='content']/form/input[22]"));
    }

    public void returnToHomePage() {
        click(By.linkText("home"));
    }


    public void create(ContactData contact) {
        initContactCreation();
        fillContactForm(contact, true);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        modifyById(contact.getId());
        fillContactForm(contact, false);
        update();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectById(contact.getId());
        delete();
        wd.switchTo().alert().accept();
        contactCache = null;
        returnToHomePage();
    }


    public boolean isThereAContact() {
        return isElementPresent(By.xpath("//td/input"));
    }

    public boolean isThereAGroup() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements((By.name("selected[]"))).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath("//*[@name='entry']"));
        for (WebElement element : elements) {
            String lastname = element.findElement(By.xpath("td[2]")).getText();
            String firstname = element.findElement(By.xpath("td[3]")).getText();
            String address = element.findElement(By.xpath("td[4]")).getText();
            String allEmails = element.findElement(By.xpath("td[5]")).getText();
            String allPhones = element.findElement(By.xpath("td[6]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname)
                    .withAddress(address).withAllPhones(allPhones).withAllEmails(allEmails));
        }
        return contactCache;
    }
    public ContactData infoFromEditForm(ContactData contact) {
        modifyById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstname)
                .withLastname(lastname).withAddress(address).withHomePhone(home).withMobilePhone(mobile)
                .withWorkPhone(work).withEmail(email).withEmail2(email2).withEmail3(email3);
    }

    public void addingGroup(ContactData contact, GroupData group) {
        selectById(contact.getId());
        selectGroupForContact(group.getId());
        confirmAddToGroup();
        contactCache = null;
    }
    private void selectGroupForContact(int id) {
        click(By.name("to_group"));
        wd.findElement(By.name("to_group")).findElement(By.cssSelector(String.format("option[value='%s']", id))).click();
    }

    private void confirmAddToGroup() {
        click(By.name("add"));
    }
    public void removeContactFromGroup(GroupData group, ContactData contact) {
        selectGroup(group.getId());
        selectById(contact.getId());
        removeGroup();
        contactCache = null;
        returnToHomePage();
    }

    private void selectGroup(int id) {
        click(By.name("group"));
        wd.findElement(By.name("group")).findElement(By.cssSelector(String.format("option[value='%s']", id))).click();
    }

    private void removeGroup() {
        click(By.name("remove"));
    }
}