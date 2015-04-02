package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.Order;
import com.kzn.itis.db.model.User;
import com.kzn.itis.db.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * Created by Алмаз on 30.03.2015.
 */
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);
    @Autowired
    private DatabaseConfiguration config;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void addOrder(Order order) {
        jdbcTemplate.update("INSERT INTO Orders VALUES (DEFAULT,?,?,?)",
                order.getCustomerid(),
                order.getSalerspersonalid(),
                order.getTotalamount()
        );
        logger.info("Order with customer id: " + order.getCustomerid() + " has been added");
    }

    @Override
    public void update(int id, int customerid, int salerspersonalid, int totalamount){
        jdbcTemplate.update("UPDATE Orders SET CustomerId = ?, SalersPersonalId = ?, TotalAmount = ? WHERE Id = ?",
                customerid,
                salerspersonalid,
                totalamount,
                id);
        logger.info("Order with id: " + id + " has been updated");
    }
    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Orders WHERE Id = ?",
                id);
        logger.info("Order with id: " + id + " has been deleted");

    }

    @Override
    public void showAll(){
        List<Order> orders = this.jdbcTemplate.query(
                "SELECT Id, CustomerId, SalersPersonalId, TotalAmount FROM Orders",
                new RowMapper<Order>() {
                    @Override
                    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
                        Order order = new Order();
                        order.setId(resultSet.getInt("Id"));
                        order.setCustomerid(resultSet.getInt("CustomerId"));
                        order.setSalerspersonalid(resultSet.getInt("SalersPersonalId"));
                        order.setTotalamount(resultSet.getInt("TotalAmount"));
                        return order;
                    }
                }
        );
        for(int i=0;i<orders.size();i++){
            logger.info(orders.get(i).toString());
        }
    }
    @Override
    public long getCount(){
        return jdbcTemplate.queryForList("SELECT * FROM Orders").size();
    }
}
