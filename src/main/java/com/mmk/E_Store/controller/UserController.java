package com.mmk.E_Store.controller;


import com.mmk.E_Store.dto.LoginRequestDTO;
import com.mmk.E_Store.entity.Users;
import com.mmk.E_Store.securityconfig.AuthService;
import com.mmk.E_Store.service.UsersService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final AuthService authService;
    private final UsersService usersService;

    public UserController(AuthService authService, UsersService usersService) {
        this.authService = authService;
        this.usersService = usersService;
    }

    @GetMapping
    public ResponseEntity<List<Users>> getUsers() {
        List<Users> users = usersService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUser(@PathVariable Long id) {
        Users user = usersService.findUser(id);
        return user != null
                ? ResponseEntity.ok(user)
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Users> saveUser(@RequestBody Users user) {
        Users savedUser = usersService.saveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedUser);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Users>> saveUsers(@RequestBody List<Users> users) {
        List<Users> savedUsers = usersService.saveUsers(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsers);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO req) {
        String token = authService.authenticateAndGenerateToken(
                req.getUsername(), req.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users user)
    {
        try{
            Users updatedUser = usersService.updateUser(id,user);
            return ResponseEntity.ok(updatedUser);
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
