package com.example.ApiMessenger.Rest;

import com.example.ApiMessenger.Dao.UsersDao;
import com.example.ApiMessenger.Model.Friends;
import com.example.ApiMessenger.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class FriendController {

    private UsersDao usersDao;

    @Autowired
    public FriendController(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @GetMapping("/friends/{username}")
    public List<Friends> getFriendsByUsername(@PathVariable String username) {
        return this.usersDao.getFriendsByUsername(username);
    }
}
