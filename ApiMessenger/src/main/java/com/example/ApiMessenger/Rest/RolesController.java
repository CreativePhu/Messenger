package com.example.ApiMessenger.Rest;

import com.example.ApiMessenger.Dao.RolesDao;
import com.example.ApiMessenger.Model.Roles;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/roles")
public class RolesController {
    private RolesDao rolesDao;
    @Autowired
    public RolesController(RolesDao rolesDao) {
        this.rolesDao = rolesDao;
    }

    @GetMapping
    public List<Roles> getRoles(){
        return this.rolesDao.getRoles();
    }

    @GetMapping("/{id}")
    public Roles getRoleById(@PathVariable int id){
        Roles role = this.rolesDao.getRoleById(id);
        return role;
    }

    @PostMapping("/addRole")
    public ResponseEntity<String> addRole(@RequestBody Roles role){
        Roles roleNew = new Roles(role.getNameRole());
        this.rolesDao.saveRole(roleNew);
        return new ResponseEntity<>("Thêm role thành công", HttpStatus.CREATED);
    }

    @PatchMapping("/updateRole/{id}")
    public ResponseEntity<String> updateRoleById(@RequestBody Roles role, @PathVariable int id){
        Roles roleNew = this.rolesDao.getRoleById(id);
        roleNew.setNameRole(role.getNameRole());
        this.rolesDao.updateRole(roleNew);
        return new ResponseEntity<>("Update thành công role", HttpStatus.OK);
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable int id){
        this.rolesDao.deleteRoleById(id);
        return new ResponseEntity<>("Delete role thành công", HttpStatus.OK);
    }
}
