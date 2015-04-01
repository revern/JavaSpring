package com.kzn.itis;

import com.kzn.itis.db.config.DatabaseConfiguration;
import com.kzn.itis.db.model.Order;
import com.kzn.itis.db.model.User;
import com.kzn.itis.db.repositories.OrderRepository;
import com.kzn.itis.db.repositories.UserRepository;
import com.kzn.itis.db.repositories.impl.OrderRepositoryImpl;
import com.kzn.itis.db.repositories.impl.UserRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.sql.*;

/**
 *
 */
public class SpringExampleProperties {

    private static final Logger logger = LoggerFactory.getLogger(SpringExampleProperties.class);

    @Autowired
    private DatabaseConfiguration config;

    public DatabaseConfiguration getConfig() {
        return config;
    }

    public void setConfig(DatabaseConfiguration config) {
        this.config = config;
    }

    public void run(AbstractApplicationContext context) {
        logger.info("Welcome to Example Application");
        logger.info("url=" + config.getDbUrl());
        logger.info("username=" + config.getDbUser());
        Connection conUsers = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conUsers = DriverManager.getConnection(config.getDbUrl());
            Statement stmt2 = conUsers.createStatement();
            String sql2 = "CREATE TABLE Users (" + "id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + " FirstName VARCHAR(256),"
                    + " LastName VARCHAR(256), Age INTEGER, PRIMARY KEY (id))";
            try{
                stmt2.executeUpdate(sql2);
            }catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    logger.info("Table has been created before");
                } else {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conUsers.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Connection conOrders = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            conOrders = DriverManager.getConnection(config.getDbUrl());
            Statement stmt = conOrders.createStatement();
            String sql = "CREATE TABLE Orders (" + "Id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + " CustomerId INTEGER not NULL,"
                    + "SalersPersonalId INTEGER not NULL,"
                    + "TotalAmount INTEGER not NULL," + " PRIMARY KEY (Id))";
            try{
                stmt.executeUpdate(sql);
            }catch (SQLException e) {
                if (e.getSQLState().equals("X0Y32")) {
                    logger.info("Table has been created before");
                } else {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conUsers.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        UserRepositoryImpl userRepositoryImpl =(UserRepositoryImpl)context.getBean("userRepositoryBean");
        workflowUser(userRepositoryImpl);
        OrderRepositoryImpl orderRepositoryImpl =(OrderRepositoryImpl)context.getBean("orderRepositoryBean");
        workflowOrder(orderRepositoryImpl);

    }
    public void workflowUser(UserRepository userRepository){
        //task 3.5.1
        logger.info("User Total Amount = " + String.valueOf(userRepository.getCount()));
        //task 3.5.2
        userRepository.showAll();
        //task 3.5.3
        User user1=new User();
        user1.setFirstname("Almaz");
        user1.setLastname("Iskhakov");
        user1.setAge(19);
        userRepository.addUser(user1);

        User user2=new User();
        user2.setFirstname("Revern");
        user2.setLastname("21");
        user2.setAge(47);
        userRepository.addUser(user2);
        //task 3.5.4
        logger.info("User Total Amount = " + String.valueOf(userRepository.getCount()));
        //task 3.5.5
        userRepository.delete(2);
        //task 3.5.6
        userRepository.update(1, "Updated", "Person", 777);
        //task.3.5.7
        userRepository.showAll();
        //task 3.5.8
        logger.info("User Total Amount = " + String.valueOf(userRepository.getCount()));
    }
    public void workflowOrder(OrderRepository orderRepository){
        //task 3.5.1
        logger.info("Order Total Amount = " + String.valueOf(orderRepository.getCount()));
        //task 3.5.2
        orderRepository.showAll();
        //task 3.5.3
        Order order1 = new Order();
        order1.setCustomerid(1);
        order1.setSalerspersonalid(1);
        order1.setTotalamount(1);
        orderRepository.addOrder(order1);

        Order order2 = new Order();
        order2.setCustomerid(2);
        order2.setSalerspersonalid(2);
        order2.setTotalamount(2);
        orderRepository.addOrder(order2);
        //task 3.5.4
        logger.info("Order Total Amount = " + String.valueOf(orderRepository.getCount()));
        //task 3.5.5
        orderRepository.delete(2);
        //task 3.5.6
        orderRepository.update(1, 777, 111, 777);
        //task.3.5.7
        orderRepository.showAll();
        //task 3.5.8
        logger.info("Order Total Amount = " + String.valueOf(orderRepository.getCount()));
    }

    public static void main(String... args) throws SQLException {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        SpringExampleProperties main = (SpringExampleProperties)context.getBean("exampleApp");
        main.run(context);
    }
}
