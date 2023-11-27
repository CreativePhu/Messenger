package com.example.ApiMessenger.Dao;

import com.example.ApiMessenger.Model.Messages;

import java.util.List;

public interface MessagesDaoInf {
    public void Save(String message, String usernameReceive, int idChat);
    public List<Messages> GetAllMessageByChat(int chat_id);
    public List<Messages> GetAllMesssage();
}
