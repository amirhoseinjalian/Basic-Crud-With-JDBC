package com.jalian.hw7.dao;
//baraye down casting bayad return  type method dao impl az jens bache bashe vali age chand ta bache dashtim chi??????????????????????????????????????

import com.jalian.hw7.dao.entities.Author;
import com.jalian.hw7.dao.entities.NormalAuthor;
import com.jalian.hw7.dao.entities.State;

import java.sql.Date;
import java.util.LinkedList;

public class AuthorDaoDBImpl extends BaseDaoImpl implements Dao<Author, String>, Closeable {
    private static AuthorDaoDBImpl authorDaoDB = new AuthorDaoDBImpl();

    private AuthorDaoDBImpl() {
        super();
    }

    //singleton
    public static AuthorDaoDBImpl getInstance() {
        return authorDaoDB;
    }

    @Override
    public Author get(String username) {
        Author author;
        query = "select * from author where username = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String nationalCode = resultSet.getString("nationalCode");
            String password = resultSet.getString("password");
            Date birthDate = resultSet.getDate("birthDate");
            int id = resultSet.getInt("id");
            author = new NormalAuthor(name, nationalCode, password, username, birthDate);
            author.setId(id);
            author.setType(resultSet.getString("type"));
            author.setState(State.valueOf(resultSet.getString("state")));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return author;
    }

    @Override
    public int add(Author author) {
        int id = 0;
        query = "insert into author (name, username, password, nationalCode, birthDate, type, state) values (?, ?, ?, ?, ?, ?, ?);";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, author.getName());
            preparedStatement.setString(2, author.getUsername());
            preparedStatement.setString(3, author.getPassword());
            preparedStatement.setString(4, author.getNationalCode());
            preparedStatement.setString(5, author.getBirthDate().toString());
            preparedStatement.setString(6, author.getType());
            preparedStatement.setString(7, author.getState().name());
            preparedStatement.executeUpdate();
            query = "select id from author where username = ?;";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, author.getUsername());
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return id;
        }
        return id;
    }

    @Override
    public boolean delete(Author author) {
        return false;
    }

    @Override
    public LinkedList<Author> getAll() {
        Author author = null;
        LinkedList<Author> authors = new LinkedList<>();
        query = "select * from author;";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                author = new Author(resultSet.getString("name`"), resultSet.getString("nationalCode"),
                        resultSet.getString("password"), resultSet.getString("username"), resultSet.getDate("birthDate"));
                author.setType(resultSet.getString("type"));
                author.setState(State.valueOf(resultSet.getString("state")));
                authors.add(author);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return authors;
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
    public boolean update(Author author) {
        //baraye update baghie field ha che konim???????????????????????????????????????????????????
        query = "update author set state = ? where username = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, author.getState().name());
            preparedStatement.setString(2, author.getUsername());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}