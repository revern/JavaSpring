package com.kzn.itis.db.repositories;

import com.kzn.itis.db.model.User;

public interface UserRepository {

    public void addUser(User user);
    public void update(int id, String firstname, String secondname, int age);
    public void delete(int id);
    public void showAll();
    public long getCount();
}
