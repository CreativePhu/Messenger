package com.example.ApiMessenger.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Chats {
    @Id
    @GeneratedValue
    private int id;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chat_user",
            joinColumns = @JoinColumn(name = "id_chat"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<Users> users;

    @JsonIgnoreProperties({"chats"})
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "chats")
    private List<Messages> messages = new ArrayList<>();

    public Chats(List<Users> users, List<Messages> messages) {
        this.users = users;
        this.messages = messages;
    }

    public Chats(List<Users> users) {
        this.users = users;
    }

    public Chats() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Chats{" +
                "id=" + id +
                ", users=" + users +
                ", messages=" + messages +
                '}';
    }
}
