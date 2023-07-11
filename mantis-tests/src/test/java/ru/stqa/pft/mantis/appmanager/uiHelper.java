package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

import java.io.IOException;

import static java.lang.String.format;

public class uiHelper extends HelperBase {

    public uiHelper(ApplicationManager app) throws IOException {
        super(app);
        wd = app.getDriver();
    }

    public void loginAsAdmin() {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), app.getProperty("web.adminLogin"));
        click(By.cssSelector("input[type='submit']"));
        type(By.name("password"), app.getProperty("web.adminPassword"));
        click(By.cssSelector("input[type='submit']"));
    }

    public String dropPasswordForUser(String username) {
        click(By.xpath("(//span[@class='menu-text'])[7]"));
        click(By.xpath("(//li[@class])[9]"));
        click(By.xpath(format("//a[text()='%s']" , username)));
        String email = wd.findElement(By.xpath("//input[@id='email-field']")).getAttribute("value");
        click(By.xpath("(//input[@type='submit'])[2]"));
        return email;
    }

    public void setNewPassword(String resetLink, String password) {
        wd.get(resetLink);
        type(By.id("password"), password);
        type(By.id("password-confirm"), password);
        click(By.cssSelector("button[type='submit']"));
    }
}