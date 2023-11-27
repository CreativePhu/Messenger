package com.example.ApiMessenger.Service;

import com.example.ApiMessenger.Dao.RolesDao;
import com.example.ApiMessenger.Dao.UsersDao;
import com.example.ApiMessenger.Model.Roles;
import com.example.ApiMessenger.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailService implements CustomUserDetailServiceInf{
    private RolesDao rolesDao;
    private UsersDao usersDao;
    @Autowired
    public CustomUserDetailService(RolesDao rolesDao, UsersDao usersDao) {
        this.rolesDao = rolesDao;
        this.usersDao = usersDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = this.usersDao.getUserByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Không tìm thấy username");
        }else{
            return new User(user.getUsername(), user.getPassword(), authorities(user.getRoles()));
        }
    }

    private Collection<? extends GrantedAuthority> authorities(Collection<Roles> roles){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Roles role : roles){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getNameRole()));
        }
        return authorities;
    }

}
