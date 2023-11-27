package com.example.ApiMessenger.Rest;

import com.example.ApiMessenger.Dao.ChatDao;
import com.example.ApiMessenger.Model.Chats;
import com.example.ApiMessenger.Model.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/chats")
@Transactional
public class ChatController {

    private ChatDao chatDao;
    @Autowired
    public ChatController(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @PostMapping("/addchat/user1/{user1}/user2/{user2}")
    public void addchat(@PathVariable String user1, @PathVariable String user2){
        this.chatDao.saveChat(user1, user2);
    }

    @GetMapping("/select")
    public List<Chats> getChats(){
        return this.chatDao.getChats();
    }

    @GetMapping("/select/user1/{user1}")
    public List<Chats> getChats(@PathVariable String user1){
        List<String> users = new ArrayList<>();
        users.add(user1);
        return this.chatDao.getChatBy1User(users);
    }
//    @GetMapping("/select/idChat/{idChat}")
//    public Chats getChatById(@PathVariable int idChat){
//        return this.chatDao.getChatById(idChat);
//    }
}
