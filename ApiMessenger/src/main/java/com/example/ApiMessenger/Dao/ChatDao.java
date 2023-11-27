package com.example.ApiMessenger.Dao;

import com.example.ApiMessenger.Model.Chats;
import com.example.ApiMessenger.Model.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatDao implements ChaoDaoInf{
    private EntityManager entityManager;
    private UsersDao usersDao;

    @Autowired
    public ChatDao(EntityManager entityManager, UsersDao usersDao) {
        this.entityManager = entityManager;
        this.usersDao = usersDao;
    }

    @Override
    public Chats getChatById(int id) {
        String jpql = "SELECT c FROM Chats c WHERE c.id = :id";
        TypedQuery<Chats> query = entityManager.createQuery(jpql, Chats.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public List<Chats> getChatBy1User(List<String> usernames) {
        String jpql = "SELECT c FROM Chats c JOIN c.users u WHERE u.username IN :usernames";
        TypedQuery<Chats> query = entityManager.createQuery(jpql, Chats.class);
        query.setParameter("usernames", usernames);
        return query.getResultList();
    }

    @Override
    public List<Chats> getChats() {
        String jpql = "SELECT c FROM Chats c";
        TypedQuery<Chats> query = entityManager.createQuery(jpql, Chats.class);
        return query.getResultList();
    }

    @Override
    public void saveChat(String user1, String user2) {
        Users user11 = this.usersDao.getUserByUsername(user1);
        Users user22 = this.usersDao.getUserByUsername(user2);
        List<Users> users = new ArrayList<>();
        users.add(user11);
        users.add(user22);
        Chats chat = new Chats(users);
        this.entityManager.persist(chat);
    }

    @Override
    public void updateChat(Chats chats) {
        this.entityManager.merge(chats);
        this.entityManager.flush();
    }

    @Override
    public void deleteChatById(int id) {
        Chats chat = this.entityManager.find(Chats.class, id);
        this.entityManager.remove(chat);
    }
}
