package com.jalian.hw7.dao;

import com.jalian.hw7.dao.entities.Article;

public interface ArticleDao<F, H> extends Dao<Article, Integer> {
    //majbur be ezafe kardan type shodim
    public F getAllArticleFromAnAuthorByUsername(H h, String type);
}