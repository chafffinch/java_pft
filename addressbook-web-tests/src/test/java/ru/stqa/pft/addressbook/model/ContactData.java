package ru.stqa.pft.addressbook.model;

public class ContactData {
    private int id;
    private  String firstname;
    private  String lastname;
    private  String address;
    private  String email;




    public int getId() {
        return id;
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public ContactData withLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData withEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }



    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        return lastname != null ? lastname.equals(that.lastname) : that.lastname == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        return result;
    }
    private String newAddress;
    public ContactData withNewAddress(String newAddress) {

        this.newAddress = newAddress;
        return this;}

    private String home;
    public ContactData withHomePhone(String home) {
        this.home = home;
        return this;
    }

    private String mobilePhone;
    public ContactData withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;

    }
    private String workPhone;
    public ContactData withWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public String allPhones;
    public String email2;
    public ContactData withEmail2(String email2) {
        this.email2 = email2;
        return this;
    }
    public String email3;

    public String getEmail2() {
        return email2;
    }



    public String getEmail3() {
        return email3;
    }



    public ContactData withEmail3(String email3) {
        this.email3 = email3;
        return this;
    }

    public String allEmails;
    public String getAllEmails() {
        return allEmails;
    }
    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }
    public String allAddress;
    public String getNewAddress() {
        return allAddress;
    }

    public void setNewAddress(String newAddress) {
        this.newAddress = newAddress;
    }
}