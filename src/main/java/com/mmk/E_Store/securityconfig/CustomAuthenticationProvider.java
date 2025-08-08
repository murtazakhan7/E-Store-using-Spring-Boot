package com.mmk.E_Store.securityconfig;
import com.mmk.E_Store.entity.Users;
import com.mmk.E_Store.repository.UsersRepo;
import com.mmk.E_Store.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;

    public CustomAuthenticationProvider(CustomUserDetailsService userDetailsService,
                                        PasswordEncoder passwordEncoder,
                                        UsersService usersService) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Users user = ((UserPrincipal) userDetails).getUser();

        usersService.upgradePasswordIfNecessary(user, rawPassword);

        // Finally, ensure password matches after any upgrade
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        
        return new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    }


