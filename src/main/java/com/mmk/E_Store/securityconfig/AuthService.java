package com.mmk.E_Store.securityconfig;
import com.mmk.E_Store.entity.Users;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

        private final AuthenticationManager authenticationManager;
        private final JWTService jwtService;

        public AuthService(AuthenticationManager authenticationManager, JWTService jwtService) {
            this.authenticationManager = authenticationManager;
            this.jwtService = jwtService;
        }

//        public String authenticateAndGenerateToken(Users user) {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//            );
//
//            if (authentication.isAuthenticated()) {
//                return jwtService.generatetoken(user.getUsername());
//            }
//            return "fail";
//        }
        public String authenticateAndGenerateToken(String username, String rawPassword) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, rawPassword)
            );

            if (authentication.isAuthenticated()) {
                return jwtService.generatetoken(username);
            }

            throw new BadCredentialsException("Invalid username or password");
        }
    }


