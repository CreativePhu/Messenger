package com.example.ApiMessenger.Rest;

import com.example.ApiMessenger.Dao.RolesDao;
import com.example.ApiMessenger.Dao.UsersDao;
import com.example.ApiMessenger.Model.Friends;
import com.example.ApiMessenger.Model.Roles;
import com.example.ApiMessenger.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UsersController {

    private UsersDao usersDao;
    private RolesDao rolesDao;
    @Autowired
    public UsersController(UsersDao usersDao, RolesDao rolesDao) {
        this.usersDao = usersDao;
        this.rolesDao = rolesDao;
    }

    @GetMapping
    public List<Users> getUsers(){
        return this.usersDao.getUsers();
    }

    @GetMapping("username/{username}")
    public Users getUserByUsername(@PathVariable String username){
        return this.usersDao.getUserByUsername(username);
    }

    @GetMapping("name/{name}/username/{username}")
    public List<Friends> getUserByName(@PathVariable String name, @PathVariable String username){
        return this.usersDao.getUserByName(name, username);
    }

    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody Users user){
        Users users = this.usersDao.getUserByUsername(user.getUsername());
        if(users != null){
            return new ResponseEntity<>("Tên tài khoản đã tôn tại", HttpStatus.NOT_ACCEPTABLE);
        }else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            Roles role = this.rolesDao.getRoleById(1);
            user.setActive(true);
            user.addRole(role);
            String newPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(newPassword);
            this.usersDao.saveUser(user);
            return new ResponseEntity<>("Tạo user thành công", HttpStatus.CREATED);
        }
    }

    @PatchMapping("/updateUser/{username}")
    public ResponseEntity<String> updateUserByUsername(@RequestBody Users user, @PathVariable String username){
        Users userNew = this.usersDao.getUserByUsername(username);
        userNew.setPassword(user.getPassword());
        userNew.setActive(user.isActive());
        userNew.setRoles(user.getRoles());
        userNew.setName(user.getName());
        userNew.setImage(user.getImage());
        this.usersDao.updateUser(userNew);
        return new ResponseEntity<>("Update thành công", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<String> deleteUserByUsername(@PathVariable String username){
        this.usersDao.deleteUserByUsername(username);
        return new ResponseEntity<>("Xóa thành công", HttpStatus.OK);
    }

    @PostMapping("/addfriend/{friendusername}/user/{username}")
    public ResponseEntity<String> addfriend(@PathVariable String friendusername, @PathVariable String username){
        Users friend = this.usersDao.getUserByUsername(friendusername);
        Users users = this.usersDao.getUserByUsername(username);
        if(users == null || friend == null){
            return new ResponseEntity<>("Không tìm thấy !", HttpStatus.NOT_FOUND);
        }else{
            users.addFriends(friend);
            friend.addFriends(users);
            this.usersDao.updateUser(users);
            this.usersDao.updateUser(friend);
            return new ResponseEntity<>("add friend thành công", HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/deletefriend/{friendusername}/user/{username}")
    public ResponseEntity<String> deletefriend(@PathVariable String friendusername, @PathVariable String username){
        Users friend = this.usersDao.getUserByUsername(friendusername);
        Users users = this.usersDao.getUserByUsername(username);
        if(users == null || friend == null){
            return new ResponseEntity<>("Không tìm thấy !", HttpStatus.NOT_FOUND);
        }else{
            users.deleteFriends(friend);
            friend.deleteFriends(users);
            this.usersDao.updateUser(users);
            this.usersDao.updateUser(friend);
            return new ResponseEntity<>("delete friend thành công", HttpStatus.CREATED);
        }
    }
}
