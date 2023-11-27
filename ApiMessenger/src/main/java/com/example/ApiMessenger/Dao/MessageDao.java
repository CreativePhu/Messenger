package com.example.ApiMessenger.Dao;

import com.example.ApiMessenger.Model.Chats;
import com.example.ApiMessenger.Model.Messages;
import com.example.ApiMessenger.Model.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageDao implements MessagesDaoInf{

    private EntityManager entityManager;
    private ChatDao chatDao;
    private UsersDao usersDao;
    @Autowired
    public MessageDao(EntityManager entityManager, ChatDao chatDao, UsersDao usersDao) {
        this.entityManager = entityManager;
        this.chatDao = chatDao;
        this.usersDao = usersDao;
    }

    @Override
    public void Save(String message, String usernameSender, int idChat) {
            Chats chat = this.chatDao.getChatById(idChat);
            Users usersender = this.usersDao.getUserByUsername(usernameSender);
            Messages messages = new Messages(usersender, message, chat, Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDateTime());
            this.entityManager.persist(messages);
    }

    @Override
    public List<Messages> GetAllMessageByChat(int chat_id) {
        String jdql = "SELECT m FROM Messages m WHERE m.chats.id =: id";
        TypedQuery<Messages> query = this.entityManager.createQuery(jdql, Messages.class);
        query.setParameter("id", chat_id);
        return query.getResultList();
    }

    @Override
    public List<Messages> GetAllMesssage() {
        String jdql = "SELECT m FROM Messages m ";
        TypedQuery<Messages> query = this.entityManager.createQuery(jdql, Messages.class);
        return query.getResultList();
    }
}
