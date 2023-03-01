package com.jalian.hw7.dao;

import com.jalian.hw7.dao.entities.Tag;

import java.util.LinkedList;

public class TagDaoDBImpl extends BaseDaoImpl implements Dao<Tag, Integer>, Closeable {
    private static TagDaoDBImpl tagDaoDB = new TagDaoDBImpl();

    private TagDaoDBImpl() {
        super();
    }

    //singleton
    public static TagDaoDBImpl getInstance() {
        return tagDaoDB;
    }

    @Override
    public Tag get(Integer id) {
        Tag tag = null;
        query = "select title from tag where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            tag = new Tag(id, resultSet.getString("title"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return tag;
    }

    @Override
    public int add(Tag tag) {
        int id = 0;
        query = "insert into tag (title) values (?);";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tag.getTitle());
            preparedStatement.executeUpdate();
            query = "SELECT id FROM tag ORDER BY id DESC LIMIT 1;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getInt("id");
            tag.setId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return id;
    }

    @Override
    public boolean delete(Tag tag) {
        System.out.println("Sorry, We Can't Delete This Tag, Because Many Articles Are Using That!!!");
        return true;
    }

    @Override
    public LinkedList<Tag> getAll() {
        LinkedList<Tag> tags = new LinkedList<>();
        query = "select * from tag;";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                tags.add(new Tag(resultSet.getInt("id"), resultSet.getString("title")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return tags;
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

    @Override
    public boolean update(Tag tag) {
        System.out.println("sorry, we don't have this service");
        return false;
    }
}