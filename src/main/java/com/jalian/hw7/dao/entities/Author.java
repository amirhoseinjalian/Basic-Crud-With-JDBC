package com.jalian.hw7.dao.entities;

import java.sql.Date;
import java.util.LinkedList;

public class Author extends BaseEntity {
    private LinkedList<Article> articles;
    private String name;
    private String nationalCode;
    private String password;
    private String type;
    private State state;
    private String username;
    private Date birthDate;

    public Author(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public Author(String name, String nationalCode, String password, String username, Date birthDate) {
        super();
        this.name = name;
        this.nationalCode = nationalCode;
        this.password = password;
        this.username = username;
        this.birthDate = birthDate;
    }

    public Author(String username) {
        super();
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public LinkedList<Article> getArticles() {
        return articles;
    }

    public void setArticles(LinkedList<Article> articles) {
        this.articles = articles;
    }
}