package ru.otus.books.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLES")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private long id;
    @Column(name = "ROLE_NAME")
    private String name;
    @Column(name = "USER_ID")
    private long user;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getUser() {
        return user;
    }
}
