package com.example.ApiMessenger.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Users {
    @Id
    @Column(length = 100, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean active;

    @Column(nullable = false)
    private String name;
    @Lob
    @Column(columnDefinition = "NVARCHAR(MAX)", nullable = false)
    private String image;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "username"),
            inverseJoinColumns = @JoinColumn(name = "idRole")
    )
    private Set<Roles> roles = new HashSet<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<Users> friends = new HashSet<>();

    public Users() {
    }

    public Users(String username, String password, boolean active, String name, String image) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.name = name;
        this.image = image;
    }

    public Users(String username, String password, boolean active, String name, String image, Set<Roles> roles) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.name = name;
        this.image = image;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public void addRole(Roles role){
        this.roles.add(role);
    }

    public void removeRole(Roles role){
        this.roles.remove(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Users> getFriends() {
        return friends;
    }

    public void setFriends(Set<Users> friends) {
        this.friends = friends;
    }

    public void addFriends(Users users){
        this.friends.add(users);
    }

    public void deleteFriends(Users users){
        this.friends.remove(users);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(username, users.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Users{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", roles=" + roles +
                '}';
    }
}
