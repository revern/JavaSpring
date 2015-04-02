package com.kzn.itis.db.repositories.impl;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.User;
import com.kzn.itis.db.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);
    @Autowired
    private DatabaseConfiguration config;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void addUser(User user) {
        jdbcTemplate.update("INSERT INTO Users VALUES (DEFAULT,?,?,?)",
                user.getFirstname(),
                user.getLastname(),
                user.getAge()
        );
        logger.info("User " + user.getFirstname() + " has been added!");
    }

    @Override
    public void update(int id, String firstname, String lastname, int age){
        jdbcTemplate.update("UPDATE Users SET FirstName = ?, LastName = ?, Age = ? WHERE Id = ?",
                firstname,
                lastname,
                age,
                id);
        logger.info("User with id: " + id + " has been updated");
    }
    @Override
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Users WHERE Id = ?",
                id);
        logger.info("User with id: " + id + " has been deleted");

    }

    @Override
    public void showAll(){
        List<User> users = this.jdbcTemplate.query(
                "SELECT Id, FirstName, LastName, Age FROM Users",
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet resultSet, int i) throws SQLException {
                        User user = new User();
                        user.setId(resultSet.getInt("Id"));
                        user.setFirstname(resultSet.getString("FirstName"));
                        user.setLastname(resultSet.getString("LastName"));
                        user.setAge(resultSet.getInt("Age"));
                        return user;
                    }
                }
        );
        for(int i=0;i<users.size();i++){
            logger.info(users.get(i).toString());
        }
    }
    @Override
    public long getCount(){
        return jdbcTemplate.queryForList("SELECT * FROM Users").size();
    }
}
