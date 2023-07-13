package ru.stqa.pft.mantis.tests;

import com.google.common.collect.ImmutableList;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UsersData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangePasswordTests extends TestBase {

    private SessionFactory sessionFactory;

    @BeforeClass
    public void setUp() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, MessagingException {
        List<UsersData> reversedUsers = ImmutableList.copyOf(app.db().users()).reverse();
        UsersData user = reversedUsers.iterator().next();
        String password = String.valueOf(System.currentTimeMillis());
        app.uiHelper().loginAsAdmin();
        String email = app.uiHelper().dropPasswordForUser(user.getUsername());
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String resetLink = findResetLink(mailMessages, email);
        app.uiHelper().setNewPassword(resetLink, password);
        assertTrue(app.newSession().login(user.getUsername(), password));

    }

    private String findResetLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}

