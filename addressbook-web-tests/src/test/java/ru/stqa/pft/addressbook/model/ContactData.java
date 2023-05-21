package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String name;
    private final String firstname;
    private final String mobileTelephone;
    private final String mail;

    public ContactData(String name, String firstname, String mobileTelephone, String mail) {
        this.name = name;
        this.firstname = firstname;
        this.mobileTelephone = mobileTelephone;
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMobileTelephone() {
        return mobileTelephone;
    }

    public String getMail() {
        return mail;
    }
}