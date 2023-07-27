package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase{

    @BeforeMethod
    public void startMailServer(){
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, MessagingException {
        String password = "password";
        Users users = app.db().users();
        UserData selectUser = getSelectUser(users);
        app.user().initChangeUserPassword(selectUser);
        String userEmail = selectUser.getEmail();
        List<MailMessage> mailMessages = app.mail().waitForEmail(1, 10000);
        String changePasswordLink = findChangePasswordLink(mailMessages, userEmail);
        app.registration().finish(changePasswordLink, password);
        HttpSession session = app.newSession();
        assertTrue(session.login(selectUser.getUsername(), password));
        assertTrue(session.isLoggedInAs(selectUser.getUsername()));
    }

    private UserData getSelectUser(Users users) {
        return users.stream().filter((u) -> u.getId() > 1).findFirst().get();
    }
    private String findChangePasswordLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer(){
        app.mail().stop();
    }
}
