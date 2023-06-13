package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Browser;
import ru.stqa.pft.addressbook.appmanager.ContactHelper;
import ru.stqa.pft.addressbook.appmanager.NavigationHelper;

import java.time.Duration;

public class ApplicationManager {
    public WebDriver wd;
    private SessionHelper sessionHelper;
    private  NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private String browser;

    public ApplicationManager(String browser) {

        this.browser = browser;
    }

    public void init() {
        if (browser == Browser.FIREFOX.browserName()){
            wd = new FirefoxDriver();
        }
        else if (browser == Browser.CHROME.browserName()){
            wd = new ChromeDriver();
        }
        else if (browser == Browser.IE.browserName()){
            wd = new InternetExplorerDriver();
        }
        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        groupHelper = new GroupHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        sessionHelper = new SessionHelper(wd);
        sessionHelper.login("admin", "secret");
        contactHelper = new ContactHelper(wd);
    }


    public void stop() {
        wd.quit();
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }
    public ContactHelper contact() {return contactHelper; }
}