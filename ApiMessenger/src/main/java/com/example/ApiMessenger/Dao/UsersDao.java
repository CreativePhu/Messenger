package com.example.ApiMessenger.Dao;

import com.example.ApiMessenger.Model.Friends;
import com.example.ApiMessenger.Model.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UsersDao implements UserDaoInf{
    private EntityManager entityManager;
    @Autowired
    public UsersDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Users> getUsers() {
        String jdql = "select s from Users s";
        TypedQuery<Users> query = this.entityManager.createQuery(jdql, Users.class);
        return query.getResultList();
    }

    @Override
    public Users getUserByUsername(String username) {
        String jdql = "select s from Users s where s.username =: username";
        TypedQuery<Users> query = this.entityManager.createQuery(jdql, Users.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void saveUser(Users user) {
        this.entityManager.persist(user);
    }

    @Override
    public void updateUser(Users user) {
        this.entityManager.merge(user);
        this.entityManager.flush();
    }

    @Override
    public void deleteUserByUsername(String username) {
        String jdql = "select s from Users s where s.username =: username";
        TypedQuery<Users> query = this.entityManager.createQuery(jdql, Users.class);
        query.setParameter("username", username);
        Users user = query.getSingleResult();
        this.entityManager.remove(user);
    }
    public List<Friends> getFriendsByUsername(String username){
        String jdql = "SELECT NEW com.example.ApiMessenger.Model.Friends(f.name, f.image, f.username) FROM Users u JOIN u.friends f WHERE u.username = :username";
        TypedQuery<Friends> query = this.entityManager.createQuery(jdql, Friends.class);
        query.setParameter("username", username);
        List<Friends> friends =  query.getResultList();
        return friends;
    }

    public List<Friends> getUserByName(String name, String username){
        String jdql = "SELECT NEW com.example.ApiMessenger.Model.Friends(u.name, u.image, u.username) FROM Users u WHERE LOWER(u.name) LIKE LOWER(:name) AND LOWER(u.username) <> LOWER(:username)";
        TypedQuery<Friends> query = this.entityManager.createQuery(jdql, Friends.class);
        query.setParameter("name", "%" + name.toLowerCase() + "%");
        query.setParameter("username", username.toLowerCase());
        List<Friends> friends =  query.getResultList();
        return friends;
    }
}
