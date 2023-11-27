package com.example.ApiMessenger.Dao;

import com.example.ApiMessenger.Model.Chats;

import java.util.List;

public interface ChaoDaoInf {
    public Chats getChatById(int id);
    public List<Chats> getChatBy1User(List<String> usernames);
    public List<Chats> getChats();
    public void saveChat(String user1, String user2);
    public void updateChat(Chats chats);
    public void deleteChatById(int id);
}
