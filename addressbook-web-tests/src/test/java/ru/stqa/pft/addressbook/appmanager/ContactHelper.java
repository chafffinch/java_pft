package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        wd.switchTo().alert().accept();
        wd.findElement(By.cssSelector("div.msgbox"));
    }


    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[id='" + id +"']")).click();
    }

    public void submitContactForm() {
        click(By.xpath("(//input[@name='submit'])[2]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getName());
        type(By.name("lastname"), contactData.getFirstname());
        type(By.name("mobile"), contactData.getMobileTelephone());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getMail());
        type(By.name("email2"), contactData.getMail2());
        type(By.name("email3"), contactData.getMail3());
        type(By.name("address"), contactData.getAddress());


        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else{
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }


    public void initContactModificationById(int id) {
        wd.findElement(By.cssSelector("a[href='edit.php?id=" + id + "']")).click();
        // WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        // WebElement row = checkbox.findElement(By.xpath("./../../"));
        // List<WebElement> cells = row.findElements(By.tagName("td"));
        // cells.get(7).findElement(By.tagName("a")).click();

        // wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void submitContactModification() {
        click(By.name("update"));

    }

    public void create(ContactData contact, boolean b) {
        gotoNewContactPage();
        fillContactForm(contact, b);
        submitContactForm();
        contactCash = null;
        returnToHomePage();
    }

    public void homePage() {
        click(By.linkText("home"));
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, false);
        submitContactModification();
        contactCash = null;
        homePage();
    }


    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        contactCash = null;
        homePage();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCash = null;

    public Contacts all() {
        if(contactCash != null) {
            return new Contacts(contactCash);
        }
        contactCash = new Contacts();
        List<WebElement> elements = wd.findElements(By.xpath("//tr[@name='entry']"));
        for (WebElement element : elements) {
            String name = element.findElement(By.xpath(".//td[3]")).getText();
            String firstname = element.findElement(By.xpath(".//td[2]")).getText();
            String allPhones = element.findElement(By.xpath(".//td[6]")).getText();
            String allMail = element.findElement(By.xpath(".//td[5]")).getText();
            String address = element.findElement(By.xpath(".//td[4]")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
            contactCash.add(new ContactData().withId(id).withName(name).withFirstname(firstname).withAllPhones(allPhones)
                    .withAllMail(allMail).withAddress(address));
        }
        return new Contacts(contactCash);
    }

    //public Set<ContactData> all() {
    // Set<ContactData> contacts = new HashSet<ContactData>();
    //List<WebElement> rows = wd.findElements(By.name("entry"));
    //for (WebElement row : rows) {
    //  List<WebElement> cells = row.findElements(By.tagName("td"));
    //  int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
    //  String name = cells.get(1).getText();
    //  String firstname = cells.get(2).getText();
    //  String[] phones =  cells.get(5).getText().split("\n");
    //  contacts.add(new ContactData().withId(id).withName(name).withFirstname(firstname).withHomePhone(phones[0])
    //          .withMobileTelephone(phones[1]).withWorkPhone(phones[2]));

    //  }
    // }


    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String name = wd.findElement(By.name("firstname")).getAttribute("value");
        String firstname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        String mail = wd.findElement(By.name("email")).getAttribute("value");
        String mail2 = wd.findElement(By.name("email2")).getAttribute("value");
        String mail3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withName(name).withFirstname(firstname).
                withHomePhone(home).withMobileTelephone(mobile).withWorkPhone(work).withMail(mail).withAddress(address)
                .withMail2(mail2).withMail3(mail3);
    }
}