package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.ContactData;
import ru.stqa.pft.mantis.model.Contacts;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void StartMailServer() {
        app.mail().start();
    }

    @Test
    public void testPassword() throws IOException, MessagingException {


        app.getDriver().findElement(By.id("username")).sendKeys("administrator");
        app.getDriver().findElement(By.cssSelector("input[value='Login']")).click();
        app.getDriver().findElement(By.id("password")).sendKeys("root");
        app.getDriver().findElement(By.cssSelector("input[value='Login']")).click();


        List<WebElement> elements = app.getDriver().findElements(By.id("sidebar"));
        for (WebElement element : elements) {
            element.findElement(By.xpath(".//ul//li[6]")).click();
        }


        app.getDriver().findElement(By.linkText("manage_users_link")).click();

        long now = System.currentTimeMillis();


        Set<ContactData> contacts = app.db().contacts();
        ContactData contact = contacts.iterator().next();


        app.getDriver().findElement(By.linkText(contact.getUsername())).click();


        app.getDriver().findElement(By.cssSelector("input[value='Reset Password']")).click();


        String email = contact.getEmail();
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = findConfirmationLink(mailMessages, email);


        String password = "password";
        app.registration().finish(confirmationLink, password);


        assertTrue(app.newSession().login(contact.getUsername(), password));


    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void StopMailServer() {
        app.mail().stop();
    }
}

