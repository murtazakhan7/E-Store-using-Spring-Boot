package com.mmk.E_Store.controller;


import com.mmk.E_Store.entity.Users;
import com.mmk.E_Store.service.UsersService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("users/")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public List<Users> getUsers(){
        return usersService.getallusers();
    }

    @GetMapping("/{id}")
    public Users getUser(@PathVariable Long id) {
        return usersService.findUser(id);
    }

    @PostMapping
    public Users saveUser(@RequestBody Users user){
        return  usersService.saveUser(user);
    }

    @DeleteMapping("/{id")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        usersService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}
