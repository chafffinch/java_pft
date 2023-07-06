package ru.stqa.pft.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "mantis_user_table")
public class ContactData {

    @Id
    private int id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String email;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public ContactData withUsername(String username) {
        this.username = username;
        return this;
    }

    public ContactData withPassword(String password) {
        this.password = password;
        return this;
    }

    public ContactData withEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactData contact = (ContactData) o;
        return id == contact.id &&
                Objects.equals(username, contact.username) &&
                Objects.equals(password, contact.password) &&
                Objects.equals(email, contact.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, email);
    }
}

