package ru.stqa.pft.mantis.appmanager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Browser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {

    private WebDriver wd;
    private String browser;
    private final Properties properties;
    private RegistrationHelper registrationHelper;
    private FtpHelper ftp;
    private MailHelper mailHelper;
    private JamesHelper jamesHelper;
    private DbHelper dbHelper;
    private UserHelper userHelper;
    private WebSessionHelper webSessionHelper;
    private SoapHelper soapHelper;

    public ApplicationManager(String browser){
        this.browser = browser;
        properties = new Properties();

    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/local.properties", target))));
        dbHelper = new DbHelper();
    }
    public void stop() {
        if (wd != null){
            wd.quit();
        }
    }
    public HttpSession newSession(){
        return new HttpSession(this);

    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }


    public WebDriver getDriver() {
        if (wd == null){
            if (browser.equals(Browser.CHROME.browserName())) {
                wd = new ChromeDriver();
            } else if (browser.equals(Browser.IE.browserName())) {
                wd = new InternetExplorerDriver();
            } else if (browser.equals(Browser.FIREFOX.browserName())) {
                wd = new FirefoxDriver(new FirefoxOptions().setBinary(properties.getProperty("web.pathToFirefox")));
            }
            wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            wd.get(properties.getProperty("web.baseUrl"));
//            webSessionHelper.login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
        }
        return wd;
    }
    public RegistrationHelper registration() {
        if( registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }
    public FtpHelper ftp(){
        if (ftp == null){
            ftp = new FtpHelper(this);
        }
        return  ftp;
    }
    public MailHelper mail(){
        if (mailHelper == null){
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }
    public JamesHelper james (){
        if (jamesHelper == null){
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }
    public DbHelper db() {
        return dbHelper;
    }
    public UserHelper user(){
        if(userHelper == null){
            userHelper = new UserHelper(this);
        }
        return userHelper;
    }
    public  WebSessionHelper webHelper() {
        if (webSessionHelper == null) {
            webSessionHelper = new WebSessionHelper(this);
        }
        return webSessionHelper;
    }
    public SoapHelper soap (){
        if (soapHelper == null){
            soapHelper = new SoapHelper(this);
        }
        return soapHelper;
    }

}