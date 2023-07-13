/*
package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDataCompareTests extends TestBase {

    @BeforeMethod
    public void checkForContactAndGroupToExist(){
        if (app.db().contacts().size() == 0){
            if (app.db().groups().size() == 0){
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("test1").withHeader("test2").withFooter("test3"));
            }
            app.goTo().homePage();
            app.contact().initContactCreation();
            app.contact().createContactWithGroup(new ContactData().withFirstName("marina").withLastName("alieva").withAddress("msk")
                    .withMobilePhone("977-302").withHomePhone("977-303").withWorkPhone("977-304")
                    .withPrimaryEmail("m@mail.ru").withSecondaryEmail("a@mail.ru").withThirdEmail("ma@mail.ru"));
        }
    }

    @Test
    public void testContactDataCompare() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().fullnamePhonesEmailsFromEditForm(contact);
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
        assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));
        verifyContactListInUI();
    }

    private String mergePhones(ContactData contact) {
        return Stream.of(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone()).filter((s) -> ! s.equals(""))
                .map(ContactDataCompareTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    private String mergeEmails(ContactData contact) {
        return Stream.of(contact.getPrimaryEmail(), contact.getSecondaryEmail(), contact.getThirdEmail()).filter((s) -> ! s.equals(""))
                .collect(Collectors.joining("\n"));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

}*/
