package ru.stqa.pft.rest.appmanager;/*
package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.model.stqa.pft.rest.UserData;


public class UserHelper extends HelperBase{

    public UserHelper (ApplicationManager app){
        super(app);
    }
    public void initChangeUserPassword(UserData user){
        app.webHelper().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
        goToManageUsers();
        selectUser(user.getId());
        initResetPassword();
    }
    private void initResetPassword() {
        click(By.xpath("//input[@value='Reset Password']"));
    }
    private void selectUser(int id) {
        click(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", id)));
    }
    private void goToManageUsers() {
        click(By.xpath("//a[contains(text(),'Manage')]"));
        click(By.xpath("//a[contains(text(),'Manage Users')]"));
    }
}*/
