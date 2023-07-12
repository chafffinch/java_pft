package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "addressbook")
public class ContactData {
    @Id
    private int id = Integer.MAX_VALUE;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String address;
    @Expose
    @Column(name = "mobile", columnDefinition = "text")
    private String mobilePhone;
    @Column(name = "work", columnDefinition = "text")
    private String workPhone;
    @Column(name = "home", columnDefinition = "text")
    private String homePhone;
    @Transient
    private String allPhones;
    @Expose
    @Column(name = "email")
    private String primaryEmail;
    @Column(name = "email2")
    private String secondaryEmail;
    @Column(name = "email3")
    private String thirdEmail;
    @Transient
    private String allEmails;
    @Transient
    private File photo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupData> groups = new HashSet<>();
    @Expose
    @Column(name = "photo", columnDefinition = "mediumtext")
    private String photoPath = new File(System.getProperty("file.photo", "src/test/resources/1.jpg")).getAbsolutePath();
    @Column(columnDefinition = "datetime")
    private String deprecated;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }
    public String getWorkPhone() {
        return workPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public String getPrimaryEmail() {
        return primaryEmail;
    }
    public String getSecondaryEmail() {
        return secondaryEmail;
    }
    public String getThirdEmail() {
        return thirdEmail;
    }
    public String getAllEmails() {
        return allEmails;
    }

    public int getId() {
        return id;
    }

    public File getPhoto() {
        return photo;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public Groups getGroups() {
        return new Groups(groups);
    }

    public ContactData withId(int id) {
        this.id = id;
        return this;
    }

    public ContactData withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public ContactData withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public ContactData withAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData withMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }
    public ContactData withWorkPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public ContactData withHomePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public ContactData withAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;
    }

    public ContactData withPrimaryEmail(String primaryEmail) {
        this.primaryEmail = primaryEmail;
        return this;
    }

    public ContactData withSecondaryEmail(String secondaryEmail) {
        this.secondaryEmail = secondaryEmail;
        return this;
    }

    public ContactData withThirdEmail(String thirdEmail) {
        this.thirdEmail = thirdEmail;
        return this;
    }

    public ContactData withAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public ContactData withPhoto(File photo) {
        this.photo = photo;
        return this;
    }

    public ContactData withPhotoPath() {
        this.photoPath = photoPath;
        return this;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", workPhone='" + workPhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", primaryEmail='" + primaryEmail + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData that = (ContactData) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address) && Objects.equals(mobilePhone, that.mobilePhone) && Objects.equals(workPhone, that.workPhone) && Objects.equals(homePhone, that.homePhone) && Objects.equals(primaryEmail, that.primaryEmail) && Objects.equals(secondaryEmail, that.secondaryEmail) && Objects.equals(thirdEmail, that.thirdEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, mobilePhone, workPhone, homePhone, primaryEmail, secondaryEmail, thirdEmail);
    }
}