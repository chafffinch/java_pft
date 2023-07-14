package ru.stqa.pft.mantis.tests;
import org.openqa.selenium.remote.BrowserType;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config/config_inc.php", "config/config_inc.php.backup");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
        app.stop();
        app.ftp().restore("config/config_inc.php.backup", "config/config_inc.php");
    }

    boolean isIssueOpenMantis(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        String status = app.soap().getIssueResolutionName(BigInteger.valueOf(issueId));
        return status.equals("open") || status.equals("reopened");
    }

    public void skipIfNotFixedMantis(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        if (isIssueOpenMantis(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    boolean isIssueOpenBugify(int issueId) {
        String status = app.rest().getSpecificIssueState(issueId);
        return status.equals("Open") || status.equals("In Progress");
    }

    public void skipIfNotFixedBugify(int issueId) {
        if (isIssueOpenBugify(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }
}

