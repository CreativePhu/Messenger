package com.example.ApiMessenger.Dao;

import com.example.ApiMessenger.Model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesDaoInf {
    public List<Roles> getRoles();
    public Roles getRoleById(int id);
    public void saveRole(Roles role);
    public void updateRole(Roles role);
    public void deleteRoleById(int id);
}
