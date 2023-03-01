package com.jalian.hw7.dao;

import com.jalian.hw7.projectUtillity.MySqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BaseDaoImpl {
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;
    protected PreparedStatement preparedStatement;
    protected String query;
    protected int id;

    public BaseDaoImpl() {
        connection = MySqlConnection.getConnection();
    }
}