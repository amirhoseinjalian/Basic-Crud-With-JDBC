package com.jalian.hw7.dao;

import com.jalian.hw7.dao.entities.Article;
import com.jalian.hw7.dao.entities.Category;
import com.jalian.hw7.dao.entities.Tag;

import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedList;

public class ArticleDaoDBImpl extends BaseDaoImpl implements ArticleDao<LinkedList<Article>, String>, Closeable {
    private static ArticleDaoDBImpl articleDaoDB = new ArticleDaoDBImpl();
    Dao<Tag, Integer> tagDao = TagDaoDBImpl.getInstance();
    private Dao categoryDao = CategoryDaoDBImpl.getInstance();

    private ArticleDaoDBImpl() {
        super();
    }

    //singleton
    public static ArticleDaoDBImpl getInstance() {
        return articleDaoDB;
    }

    @Override
    public LinkedList<Article> getAllArticleFromAnAuthorByUsername(String username, String type) {
        boolean b = true;
        LinkedList<Integer> ids = new LinkedList<>();
        //select article.*, tag.id, tag.title from article, tag inner join articletag on tag.id = articletag.tagId where articletag.articleId = article.id and article.userUsername = 'aj';
        LinkedList<Article> articles = new LinkedList<>();
        HashSet<Tag> tags = null;
        if (type.equals("all")) {
            query = "select article.*, tag.id as tagId, tag.title as tagTitle from article, tag inner join articletag on tag.id = articletag.tagId where articletag.articleId = article.id and article.authorUsername = ?;";
        } else {
            query = "select article.*, tag.id as tagId, tag.title as tagTitle from article, tag inner join articletag on tag.id = articletag.tagId where articletag.articleId = article.id and article.authorUsername = ? and article.type = ?;";
        }
        Article article = null;
        Category category = null;
        int id = 0;
        String content = "";
        String brief = "";
        String title = "";
        Date creatDate = null;
        Date lastUpdateDate = null;
        Date publishDate = null;
        String isPublished = "";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            if (!type.equals("all")) {
                preparedStatement.setString(2, type);
            }
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
                if (!ids.contains(id)) {
                    tags = new HashSet<>();
                    content = resultSet.getString("content");
                    brief = resultSet.getString("brief");
                    title = resultSet.getString("title");
                    isPublished = resultSet.getString("isPublished");
                    category = (Category) categoryDao.get(resultSet.getInt("categoryId"));// casting inja vajeb bud ya man eshtebah kardam?
                    creatDate = resultSet.getDate("createDate");
                    lastUpdateDate = resultSet.getDate("lastUpdate");
                    publishDate = resultSet.getDate("publishDate");
                    article = new Article(id, content, brief, title, creatDate, lastUpdateDate);
                    article.setPrice(resultSet.getDouble("price"));
                    article.setType(resultSet.getString("type"));
                    article.setCategories(category);
                    article.setLastUpdate(lastUpdateDate);
                    article.setPublishDate(publishDate);
                    article.setTags(tags);
                    articles.add(article);
                    ids.add(id);
                }
                if (ids.contains(id)) {
                    tags.add(new Tag(resultSet.getInt("tagId"), resultSet.getString("tagTitle")));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return articles;
    }

    @Override
    public int add(Article article) {
        int id = 0;
        query = "insert into article (authorId, title, brief, content, createDate, isPublished, lastUpdate, authorUsername, categoryId, price, type) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, article.getUserId());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getBrief());
            preparedStatement.setString(4, article.getContent());
            preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
            preparedStatement.setString(6, "no");
            preparedStatement.setDate(7, new Date(System.currentTimeMillis()));
            preparedStatement.setString(8, article.getUserUsername());
            preparedStatement.setInt(9, article.getCategories().getId());
            preparedStatement.setDouble(10, article.getPrice());
            preparedStatement.setString(11, article.getType());
            preparedStatement.executeUpdate();
            query = "SELECT id FROM article ORDER BY articleId DESC LIMIT 1;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getInt("articleId");
            article.setId(id);
            for (Tag tag : article.getTags()) {
                query = "insert into articletag (articleId, tagId) value (?, ?);";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, article.getId());
                preparedStatement.setInt(2, tag.getId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return id;
    }

    @Override
    public boolean delete(Article article) {
        return true;
    }

    @Override
    public LinkedList<Article> getAll() {
        System.out.println("Sorry, This Method Don't Have Any Implemention!!!!!");
        return null;
    }

    @Override
    public Article get(Integer id) {
        Article article = null;
        query = "select * from article where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            article = new Article(id, resultSet.getString("content"), resultSet.getString("brief"), resultSet.getString("title"), resultSet.getDate("createDate"), resultSet.getDate("lastUpdate"));
            article.setType(resultSet.getString("type"));
            article.setPrice(resultSet.getDouble("price"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return article;
    }

    @Override
    public boolean update(Article article) {
        query = "update article set content = ?, brief = ?, title = ?, isPublished = ?, lastUpdate = curdate() where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, article.getContent());
            preparedStatement.setString(2, article.getBrief());
            preparedStatement.setString(3, article.getTitle());
            preparedStatement.setString(4, article.getIsPublished());
            preparedStatement.setInt(5, article.getId());
            preparedStatement.executeUpdate();
            if (article.getIsPublished().equals("yes")) {
                query = "update article set publishDate = curdate() where id = ?;";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, article.getId());
                preparedStatement.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void close() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}