package com.jalian.hw7.service;

import com.jalian.hw7.dao.*;
import com.jalian.hw7.dao.entities.*;

import java.util.HashSet;
import java.util.LinkedList;

public class Service {
    private static Service service = new Service();
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private Dao<Author, String> authorDaoDB = daoFactory.getDao("authorDao");
    private ArticleDao<LinkedList<Article>, String> articleDao = (ArticleDao<LinkedList<Article>, String>) daoFactory.getDao("articleDao");
    private Dao categoryDao = daoFactory.getDao("categoryDao");
    private Dao tagDaoDB = daoFactory.getDao("tagDao");
    private AccountDao accountDao = (AccountDao) daoFactory.getDao("accountDao");

    private Service() {

    }

    public static Service getInstance() {
        return service;
    }

    //In baraye down cast fayede dare??????????????????????????
    public NormalAuthor authenticate(Author author) {
        String password = author.getPassword();
        if (author.getUsername().equals("") || author.getUsername() == null || author.getPassword().equals("") || author.getPassword() == null) {
            return null;
        }
        author = (Author) authorDaoDB.get(author.getUsername());
        if (author == null) {
            return null;
        }
        if (!author.getPassword().equals(password)) {
            return null;
        }
        return (NormalAuthor) author;
    }

    public Author getAuthor(Author author) {
        //behem khata dad chon method string migereft
        /*return (Author) authorDaoDB.get(author);*/
        return (Author) authorDaoDB.get(author.getUsername());
    }

    public String seeArticlesByAnAuthor(Author author, String type) {
        if (author == null) {
            return null;
        }
        LinkedList<Article> articles = new LinkedList<>();
        articles = articleDao.getAllArticleFromAnAuthorByUsername(author.getUsername(), type);
        if (articles == null) {
            return null;
        }
        if (type.equals("all")) {
            author.setArticles(articles);
        }
        StringBuilder information = new StringBuilder();
        for (Article article : articles) {
            information.append(article.toString());
            information.append("\n");
        }
        return information.toString();
    }

    public int addAccount(Accont accont) {
        if (accont == null) {
            return 0;
        }
        return accountDao.add(accont);
    }

    public void updateAuthorState(Author author) {
        if (author != null) {
            authorDaoDB.update(author);
        }
    }

    public String getAllCategories() {
        StringBuilder string = new StringBuilder();
        LinkedList<Category> categories = categoryDao.getAll();
        for (Category category : categories) {
            string.append(category.toString()).append("\n");
        }
        return string.toString();
    }

    public String getAllTags() {
        LinkedList<Tag> tags = tagDaoDB.getAll();
        StringBuilder string = new StringBuilder();
        for (Tag tag : tags) {
            string.append(tag.toString()).append("\n");
        }
        return string.toString();
    }

    public int addACategory(Category category) {
        int id = 0;
        if (category.getDescription() == null || category.getTitle() == null || category == null) {
            return 0;
        }
        if (category.getDescription().equals("") || category.getTitle().equals("")) {
            return 0;
        }
        id = categoryDao.add(category);
        if (id == 0) {
            return 0;
        }
        return id;
    }

    public int addATag(Tag tag) {
        if (tag.getTitle() == null || tag.getTitle().equals("")) {
            return 0;
        }
        return tagDaoDB.add(tag);
    }

    public Category getCategory(Category category) {
        category = (Category) categoryDao.get(category.getId());
        return category;
    }

    public Tag getTag(Tag tag) {
        tag = (Tag) tagDaoDB.get(tag.getId());
        return tag;
    }

    public int addAnArticle(Article article) {
        //after checking************************************
        return articleDao.add(article);
    }

    public Article getArticle(int id) {
        return articleDao.get(id);
    }

    public void exit() {
        authorDaoDB.close();
        ((Closeable) articleDao).close();
        ((Closeable) tagDaoDB).close();
        ((Closeable) categoryDao).close();
        ((Closeable) accountDao).close();
    }

    public Accont getAccount(Accont accont) {
        if (accont.getId() == 0) {
            return null;
        }
        //inja age khod account ham moshkel nadare darhali ke bayad int begire, chera????????????????????????????????????
        accont = (Accont) accountDao.get(accont.getId());
        return accont;
    }

    // midunam esmesh moshkel dare :))))))))))))
    public boolean changeAccountAmount(Accont accont, double amount) {
        if (accont.getAmount() < amount) {
            return false;
        }
        accont.setAmount(accont.getAmount() - amount);
        // tu vorudi in tavabe asan chek nemikone chi dare migire?????????????????????????????????????????????????????
        return accountDao.update(accont);
    }

    public boolean updateArticle(Article article) {
        return articleDao.update(article);
    }

    public boolean chargeAccount(Accont accont, double amount) {
        if (amount < 0) {
            return false;
        }
        accont.setAmount(accont.getAmount() + amount);
        return accountDao.update(accont);
    }

    public int addAuthor(Author author) {
        if (author.getPassword().equals("") || author.getPassword() == null) {
            return 0;
        }
        if (author.getName().equals("") || author.getName() == null) {
            return 0;
        }
        if (author.getUsername().equals("") || author.getUsername() == null) {
            return 0;
        }
        if (author.getBirthDate() == null) {
            return 0;
        }
        return (authorDaoDB.add(author));
    }
}