package com.mmk.E_Store.securityconfig;

import com.mmk.E_Store.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private final Users user;

    public UserPrincipal(Users user){
        this.user = user;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getName()))
//                .collect(Collectors.toList());
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    public Users getUser(){
        return user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
       // return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
       // return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return  true;
    }

    @Override
    public boolean isEnabled() {
       // return UserDetails.super.isEnabled();
        return true;
    }
}


