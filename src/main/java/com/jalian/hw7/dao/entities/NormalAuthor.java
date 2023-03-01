package com.jalian.hw7.dao.entities;

import java.sql.Date;

public class NormalAuthor extends Author {
    Accont accont;

    public NormalAuthor(String username, String password) {
        super(username, password);
        this.setType("normal");
    }

    public NormalAuthor(String name, String nationalCode, String password, String username, Date birthDate) {
        super(name, nationalCode, password, username, birthDate);
        this.setType("normal");
    }

    public NormalAuthor(String username) {
        super(username);
    }

    public Accont getAccont() {
        return accont;
    }

    public void setAccont(Accont accont) {
        if (accont.getAmount() <= 0) {
            throw new IllegalArgumentException("illegal amount");
        }
        this.accont = accont;
    }
}