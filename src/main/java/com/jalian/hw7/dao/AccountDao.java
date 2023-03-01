package com.jalian.hw7.dao;

import com.jalian.hw7.dao.entities.Accont;

import java.util.HashSet;

public interface AccountDao extends Dao<Accont, Integer> {
    public HashSet<Accont> getAllAuthorAccount(String yser);
}