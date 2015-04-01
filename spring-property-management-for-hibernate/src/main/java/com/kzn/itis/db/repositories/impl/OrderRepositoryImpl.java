package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.Order;
import com.kzn.itis.db.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.*;

/**
 * Created by Алмаз on 30.03.2015.
 */
public class OrderRepositoryImpl implements OrderRepository {
    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);
    @Autowired
    private DatabaseConfiguration config;
    @Override
    public void addOrder(Order order){
        Connection con = null;
        try {
            con = DriverManager.getConnection(config.getDbUrl());
            Statement statement = con.createStatement();
            String sql;
            if (order.getId() == 0) {
                sql = "INSERT INTO Orders VALUES (DEFAULT ," + order.getCustomerid()
                        + "," + order.getSalerspersonalid() + "," + order.getTotalamount() + ")";
            }else{
                sql = "INSERT INTO Orders VALUES (" + order.getId() + "," + order.getCustomerid()
                        + "," + order.getSalerspersonalid() + "," + order.getTotalamount() + ")";
            }
            statement.executeUpdate(sql);
            logger.info("Order with customer id: " + order.getCustomerid() + " has been added");
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
    public void update(int id, int customerid, int salerspersonalid, int totalamount){
        Connection con = null;
        try {
            con = DriverManager.getConnection(config.getDbUrl());
            Statement statement = con.createStatement();
            String sql = "UPDATE Orders SET CustomerId = "  + customerid + ", SalersPersonalId = " +salerspersonalid + ", TotalAmount = " + totalamount +" WHERE Id = " + id;
            statement.executeUpdate(sql);
            logger.info("Order with id: " + id + " has been updated");
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
            String sql = "DELETE FROM Orders WHERE Id = " + id;
            statement.executeUpdate(sql);
            logger.info("Order with id: " + id + " has been deleted");
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
            String sql = "SELECT * FROM Orders";
            ResultSet res = statement.executeQuery(sql);
            while (res.next()) {
                logger.info(res.getInt(1) + " " + res.getInt(2) + " " + res.getInt(3) + " " + res.getInt(4));
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
            String sql = "SELECT * FROM Orders";
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
