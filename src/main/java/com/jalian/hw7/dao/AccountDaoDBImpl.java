package com.jalian.hw7.dao;

import com.jalian.hw7.dao.entities.Accont;

import java.util.HashSet;
import java.util.LinkedList;

public class AccountDaoDBImpl extends BaseDaoImpl implements AccountDao, Closeable {
    private static AccountDaoDBImpl accountDaoDB = new AccountDaoDBImpl();

    private AccountDaoDBImpl() {
        super();
    }

    public static AccountDaoDBImpl getInstance() {
        return accountDaoDB;
    }

    @Override
    public int add(Accont accont) {
        query = "insert into account (amount, authorUsername) values (?, ?)";
        int id = 0;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, accont.getAmount());
            preparedStatement.setString(2, accont.getAuthorUsername());
            preparedStatement.executeUpdate();
            query = "SELECT id FROM account ORDER BY id DESC LIMIT 1;";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            resultSet.next();
            id = resultSet.getInt("id");
            accont.setId(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return id;
    }

    @Override
    public boolean delete(Accont accont) {
        System.out.println("sorry we don't still support this service!!!!");
        return false;
    }

    @Override
    public Accont get(Integer id) {
        Accont accont = null;
        query = "select * from account where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            accont = new Accont(id, resultSet.getDouble("amount"), resultSet.getString("authorUsername"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return accont;
    }

    @Override
    public LinkedList<Accont> getAll() {
        LinkedList<Accont> acconts = new LinkedList<>();
        query = "select * from account;";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                acconts.add(new Accont(resultSet.getInt("id"), resultSet.getDouble("amount"), resultSet.getString("authorUsername")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return acconts;
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
    public boolean update(Accont accont) {
        query = "update account set amount = ? where id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, accont.getAmount());
            preparedStatement.setInt(2, accont.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public HashSet<Accont> getAllAuthorAccount(String authorUsername) {
        HashSet<Accont> acconts = null;
        query = "select * from account where authorUsername = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, authorUsername);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                acconts.add(new Accont(resultSet.getInt("id"), resultSet.getDouble("amount"), authorUsername));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return acconts;
    }
}