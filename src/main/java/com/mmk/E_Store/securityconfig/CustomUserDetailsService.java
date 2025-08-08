package com.mmk.E_Store.securityconfig;

import com.mmk.E_Store.entity.Users;
import com.mmk.E_Store.exception.UserNotFoundException;
import com.mmk.E_Store.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }
}
