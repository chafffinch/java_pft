package ru.stqa.pft.mantis.model;

import com.jayway.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.IssueBugify;
import ru.stqa.pft.mantis.tests.TestBase;

import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class RestAssuredTests extends TestBase {

    @BeforeClass
    public void init() {
        RestAssured.authentication = RestAssured.basic("b31e382ca8445202e66b03aaf31508a3", "");
    }

    @BeforeMethod
    public void checkForIssue() {
        skipIfNotFixedBugify(462);
    }

    @Test
    public void testCreateIssue() {
        Set<IssueBugify> oldIssues = app.rest().getIssues();
        IssueBugify newIssue = new IssueBugify().withSubject("VA Test Issue").withDescription("VA New Test Issue");
        int issueId = app.rest().createIssue(newIssue);
        Set<IssueBugify> newIssues = app.rest().getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }
}