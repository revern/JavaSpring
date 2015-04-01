package com.kzn.itis.db.repositories;

import com.kzn.itis.db.model.Order;

/**
 * Created by Алмаз on 01.04.2015.
 */
public interface OrderRepository {
    public void addOrder(Order order);
    public void update(int id, int customerid, int salerspersonalid, int totalamount);
    public void delete(int id);
    public void showAll();
    public long getCount();
}
