package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class ContactHelper extends HelperBase{

    public ContactHelper (WebDriver wd) {
        super(wd);
    }

    public void submitContactCreation() {
        wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
    }

    public void fillContactForm(ContactData contactData,  boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());


        if (creation){
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void fillContactFormWithoutGroup(ContactData contactData) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("address"), contactData.getAddress());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email3"), contactData.getEmail3());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("work"), contactData.getWorkPhone());
    }

    public void createWithoutGroup(ContactData contact) {
        gotoAddContact();
        fillContactFormWithoutGroup(contact);
        submitContactCreation();
    }

    public void gotoAddContact() {
        wd.findElement(By.linkText("add new")).click();
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='"+id +"']")).click();
    }

    public void deleteSelectedContact() {
        click(By.xpath("//input[@value='Delete']"));
    }

    public void assertDeleteContact() {
        wd.switchTo().alert().accept();
    }

    public void editContactById(int id) {
        wd.findElement(By.cssSelector("img[title=\"Edit\"]")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void returnToContactPage() {
        click(By.linkText("home"));
    }
    public void createContact(ContactData contact)  {
        gotoAddContact();
        fillContactForm(contact, true);
        submitContactCreation();
        returnToContactPage();
    }
    public boolean chooseGroup() {
        click(By.xpath("/html/body/div/div[4]/form/select[5]"));
        if (isElementPresent(By.xpath("/html/body/div/div[4]/form/select[5]/option[2]"))) {
            click(By.xpath("/html/body/div/div[4]/form/select[5]/option[2]"));
            return true;
        } else {
            return false;
        }
    }
    //
    public void gotoHomePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        }
        click(By.linkText("home"));
    }
    public void selectGroup(Contacts contactData) {
        if (contactData.iterator().next().getGroups().size() > 1) {
            Assert.assertTrue(contactData.iterator().next().getGroups().size() == 1);
            new Select(wd.findElement(By.name("group"))).selectByVisibleText(contactData.iterator().next().getGroups().iterator().next().getName());
        }
    }

    public void selectGroup(GroupData group) {
        wd.findElement(By.xpath(String.format("//select[@name='to_group']/option[@value='%s']", group.getId()))).click();
    }
    public void selectContact(ContactData contact) {
        click(By.xpath(String.format("//input[@type='checkbox']", contact.getId())));
    }

    public void selectContactWithoutGroup(ContactData contact) {
        new Select(wd.findElement(By.name("group"))).selectByVisibleText("[none]");
        click(By.xpath(String.format("//input[@type='checkbox']", contact.getId())));
    }

    public void removeContactFromGroup() {
        click(By.name("remove"));
        gotoHomePage();
    }

    public void getGroupData(GroupData groupData) {
        click(By.xpath(String.format("//select[@name='group']/option[text() = '%s']", groupData.getName())));;
    }

    public void addContactToGroup() {
        click(By.name("add"));
        gotoHomePage();
    }
    //

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
        assertDeleteContact();
    }

    public void modify(ContactData contact) {
        editContactById(0);
        fillContactForm(contact, false);
        submitContactModification();
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");

        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withEmail(email).withEmail2(email2).withEmail3(email3);
    }

    private void initContactModificationById (int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }

    public int count() { /*Метод показывает кол-во selected[] в списке*/
        return wd.findElements(By.name("selected[]")).size();
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String name = cells.get(1).getText();
            String lastname =  cells.get(2).getText();
            String allPhones = cells.get(5).getText();
            String allEmails = cells.get(4).getText();
            contacts.add(new ContactData().withId(id).withFirstName(name).withLastName(lastname).withAllPhones(allPhones).withAllEmails(allEmails));
        }
        return contacts;
    }
}