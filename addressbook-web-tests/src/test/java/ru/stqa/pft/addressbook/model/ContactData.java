package ru.stqa.pft.addressbook.model;
import java.util.Objects;


public class ContactData {
    private int id = Integer.MAX_VALUE;;
    private String name;
    private String firstname;
    private String mobileTelephone;
    private String mail;
    private String group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ContactData(String name, String firstname, String mobileTelephone, String mail, String group) {
        this.id = Integer.MAX_VALUE;
        this.name = name;
        this.firstname = firstname;
        this.mobileTelephone = mobileTelephone;
        this.mail = mail;
        this.group = group;
    }


    public ContactData(int id, String name, String firstname, String mobileTelephone, String mail, String group) {
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.mobileTelephone = mobileTelephone;
        this.mail = mail;
        this.group = group;
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

    public String getGroup() { return group; }

    @Override
    public String toString() {
        return "ContactData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(firstname, that.firstname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, firstname);
    }
}