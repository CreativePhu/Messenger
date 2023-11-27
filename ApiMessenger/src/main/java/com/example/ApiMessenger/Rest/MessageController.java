package com.example.ApiMessenger.Rest;

import com.example.ApiMessenger.Dao.MessageDao;
import com.example.ApiMessenger.Model.Chats;
import com.example.ApiMessenger.Model.Messages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/messages/")
@Transactional
public class MessageController {
    private MessageDao messageDao;
    @Autowired
    public MessageController(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    @PostMapping("addmesssage/{message}/sender/{sender}/idChat/{idchat}")
    public void addMessage(@PathVariable String message, @PathVariable String sender, @PathVariable int idchat){
        this.messageDao.Save(message, sender, idchat);
    }

    @GetMapping("select/{idchat}")
    public List<Messages> getAllMessageByChat(@PathVariable int idchat){
        return this.messageDao.GetAllMessageByChat(idchat);
    }

    @GetMapping("select")
    public List<Messages> getAllMessage(){
        return this.messageDao.GetAllMesssage();
    }

}
