package com.example.ApiMessenger.Dao;

import com.example.ApiMessenger.Model.Roles;
import com.example.ApiMessenger.Model.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class RolesDao implements RolesDaoInf{

    private EntityManager entityManager;

    @Autowired
    public RolesDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Roles> getRoles() {
        String jdql = "select s from Roles s";
        TypedQuery<Roles> query = this.entityManager.createQuery(jdql, Roles.class);
        List<Roles> roles = query.getResultList();
        return roles;
    }

    @Override
    public Roles getRoleById(int id) {
        String jdql = "select s from Roles s where s.id =: id";
        TypedQuery<Roles> query = this.entityManager.createQuery(jdql, Roles.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public void saveRole(Roles role) {
        this.entityManager.persist(role);
    }

    @Override
    public void updateRole(Roles role) {
        this.entityManager.merge(role);
        this.entityManager.flush();
    }

    @Override
    public void deleteRoleById(int id) {
        Roles role = this.entityManager.find(Roles.class, id);
        this.entityManager.remove(role);
    }
}
