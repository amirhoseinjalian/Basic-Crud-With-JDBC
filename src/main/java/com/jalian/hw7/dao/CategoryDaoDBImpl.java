package com.jalian.hw7.dao;

import com.jalian.hw7.dao.entities.Category;

import java.util.LinkedList;

public class CategoryDaoDBImpl extends BaseDaoImpl implements Dao<Category, Integer>, Closeable {
    private static CategoryDaoDBImpl categoryDaoDB = new CategoryDaoDBImpl();

    private CategoryDaoDBImpl() {
        super();
    }

    //singleton
    public static CategoryDaoDBImpl getInstance() {
        return categoryDaoDB;
    }

    @Override
    public Category get(Integer id) {
        Category category = null;
        query = "select * from category where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String title = resultSet.getString("title");
            String description = resultSet.getString("description");
            category = new Category(id, description, title);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return category;
        }
        return category;
    }

    @Override
    public boolean delete(Category category) {
        System.out.println("Sorry, We Can't Delete This Category, Because Many Articles Are Using That!!!");
        return true;
    }

    @Override
    public int add(Category category) {
        int id = 0;
        query = "insert into category (description, title) values (?, ?);";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category.getDescription());
            preparedStatement.setString(2, category.getTitle());
            preparedStatement.executeUpdate();
            query = "SELECT id FROM category ORDER BY id DESC LIMIT 1;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getInt("id");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return id;
        }
        return id;
    }

    @Override
    public LinkedList<Category> getAll() {
        LinkedList<Category> categories = new LinkedList<>();
        query = "select * from category;";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                categories.add(new Category(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getString("title")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return categories;
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
    public boolean update(Category category) {
        System.out.println("sorry, we don't have this service");
        return false;
    }

}