package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.User;
import com.kzn.itis.db.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    @Autowired
    private DatabaseConfiguration config;
    @Override
    public void addUser(User event){
        Connection con = null;
        try {
            con = DriverManager.getConnection(config.getDbUrl());
            Statement statement = con.createStatement();
            String sql = "INSERT INTO Users VALUES (DEFAULT ,'" + event.getFirstname() + "', '" + event.getLastname()
                        + "', " + event.getAge() + ")";
            statement.executeUpdate(sql);
            logger.info("User " + event.getFirstname() + " has been added!");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(int id, String firstname, String secondname, int age){
        Connection con = null;
        try {
            con = DriverManager.getConnection(config.getDbUrl());
            Statement statement = con.createStatement();
            String sql = "UPDATE Users SET FirstName = '" + firstname + "', LastName = '" + secondname + "', Age = " + age + " WHERE Id = " + id;
            statement.executeUpdate(sql);
            logger.info("User with id: " + id + " has been updated");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void delete(int id){
        Connection con = null;
        try {
            con = DriverManager.getConnection(config.getDbUrl());
            Statement statement = con.createStatement();
            String sql = "DELETE FROM Users WHERE Id = " + id;
            statement.executeUpdate(sql);
            logger.info("User with id: " + id + " has been deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void showAll(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(config.getDbUrl());
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM Users";
            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                logger.info(res.getInt(1) + " " + res.getString(2) + " " +res.getString(3) +" "+ res.getInt(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public long getCount(){
        Connection con = null;
        int i = 0;
        try {
            con = DriverManager.getConnection(config.getDbUrl());
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM Users";
            ResultSet res = statement.executeQuery(sql);
            while(res.next()) {
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
}
