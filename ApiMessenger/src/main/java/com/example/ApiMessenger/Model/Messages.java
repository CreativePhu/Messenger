package com.example.ApiMessenger.Model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Users userSender;

    private String content;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Chats chats;

    private LocalDateTime dateSend;

    public Messages(Users userSender, String content, Chats chats) {
        this.userSender = userSender;
        this.content = content;
        this.chats = chats;
    }

    public Messages(Users userSender, String content, Chats chats, LocalDateTime dateSend) {
        this.userSender = userSender;
        this.content = content;
        this.chats = chats;
        this.dateSend = dateSend;
    }

    public Messages() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users getUserSender() {
        return userSender;
    }

    public void setUserSender(Users userSender) {
        this.userSender = userSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Chats getChats() {
        return chats;
    }

    public void setChats(Chats chats) {
        this.chats = chats;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "id=" + id +
                ", userSender=" + userSender +
                ", content='" + content + '\'' +
                ", chats=" + chats +
                '}';
    }
}
