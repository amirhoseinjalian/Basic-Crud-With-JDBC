package com.jalian.hw7.dao;

import com.jalian.hw7.dao.entities.*;

import java.util.LinkedList;

public class DaoFactory {
    private static DaoFactory daoFactory = new DaoFactory();
    //age refrence ha az jens pedar bashe, method close tu servic call nemishe??????????????????????????????????????????
    private Dao<Author, String> authorDaoDB = AuthorDaoDBImpl.getInstance();
    private ArticleDao<LinkedList<Article>, String> articleDao = ArticleDaoDBImpl.getInstance();
    private Dao<Category, Integer> categoryDao = CategoryDaoDBImpl.getInstance();
    private Dao<Tag, Integer> tagDaoDB = TagDaoDBImpl.getInstance();
    private Dao<Accont, Integer> accountDao = AccountDaoDBImpl.getInstance();

    private DaoFactory() {

    }

    public static DaoFactory getInstance() {
        return daoFactory;
    }

    public Dao getDao(String daoName) {
        if (daoName.equals("tagDao")) {
            return tagDaoDB;
        }
        if (daoName.equals("categoryDao")) {
            return categoryDao;
        }
        if (daoName.equals("articleDao")) {
            return articleDao;
        }
        if (daoName.equals("authorDao")) {
            return authorDaoDB;
        }
        if (daoName.equals("accountDao")) {
            return accountDao;
        }
        return null;
    }
}