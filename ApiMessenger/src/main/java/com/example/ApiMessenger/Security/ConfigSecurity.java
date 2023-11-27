package com.example.ApiMessenger.Security;

import com.example.ApiMessenger.Dao.RolesDao;
import com.example.ApiMessenger.Dao.UsersDao;
import com.example.ApiMessenger.Model.Roles;
import com.example.ApiMessenger.Model.Users;
import com.example.ApiMessenger.Service.CustomUserDetailService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import java.util.HashSet;
import java.util.Set;

@Configuration
public class ConfigSecurity {
    @Autowired
    private RolesDao rolesDao;

    @PostConstruct
    private void createRoleAdmin(){
        Roles role = new Roles("admin");
        this.rolesDao.saveRole(role);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity  httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(
                (authorize)->authorize
                        .requestMatchers("/v1/check_account").permitAll()
                        .requestMatchers("/v1/roles").permitAll()
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    @Autowired
    public AuthenticationManager authenticationManager(CustomUserDetailService userDetailService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }
}
