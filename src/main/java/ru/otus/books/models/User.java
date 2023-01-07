package ru.otus.books.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "USERS")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    long id;
    @Column(name = "USERNAME", nullable = false, unique = true)
    String userName;
    @Column(name = "PASSWORD")
    String passWord;
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    List<Role> roles;

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public List<Role> getRoles() {
        return roles;
    }
}
