package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class WebSessionHelper extends HelperBase{

    public WebSessionHelper (ApplicationManager app){
        super(app);
    }

    public void login(String username, String password) {
        type(By.name("username"), username);
        type(By.name("password"), password);
        click(By.xpath("//input[@value='Login']"));
    }
    public void logout() {
        click(By.linkText("Logout"));
    }
}
