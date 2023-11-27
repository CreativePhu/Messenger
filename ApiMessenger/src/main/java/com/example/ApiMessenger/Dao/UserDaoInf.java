package com.example.ApiMessenger.Dao;

import com.example.ApiMessenger.Model.Roles;
import com.example.ApiMessenger.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDaoInf {
    public List<Users> getUsers();
    public Users getUserByUsername(String username);
    public void saveUser(Users user);
    public void updateUser(Users user);
    public void deleteUserByUsername(String username);
}
