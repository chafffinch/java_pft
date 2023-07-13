
package ru.stqa.pft.mantis.model;

import javax.persistence.*;
import java.util.Objects;

import java.util.Objects;

@Entity
@Table(name="mantis_user_table")
public class UsersData {

    @Id
    private int id;
    private String username;
    private String realname;
    private String email;

    public int getId() {
        return id;
    }

    public UsersData withId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UsersData withUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRealname() {
        return realname;
    }

    public UsersData withRealname(String realname) {
        this.realname = realname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UsersData withEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "UsersData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", realname='" + realname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsersData usersData = (UsersData) o;
        return id == usersData.id && Objects.equals(username, usersData.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}
