package ru.stqa.pft.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String email;
    private String group;

    public ContactData(String firstname, String lastname, String address, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;

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

    public String getGroup() {
        return group;
    }

}