package com.mmk.E_Store.service;

import com.mmk.E_Store.entity.Users;
import com.mmk.E_Store.exception.UserNotFoundException;
import com.mmk.E_Store.repository.UsersRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private UsersRepo usersRepo;

    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    public Users findUser(Long id) {
        return usersRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }

    public Users saveUser(Users user){
        return  usersRepo.save(user);
    }

    public List<Users> saveUsers(List<Users> users) {
        return usersRepo.saveAll(users);
    }


    public void deleteUser(Long id) {
        if(!usersRepo.existsById(id)){
            throw new EntityNotFoundException(("User with id "+ id + "not found"));
        }
        usersRepo.deleteById(id);
    }

    public Users updateUser(Long id , Users user) {
        return usersRepo.findById(id)
                .map(existingEntity ->{
                    existingEntity.setAddress(user.getAddress());
                    if(!user.getEmailAddress().contains("@")){
                        throw new IllegalArgumentException("Enter correct email address");
                    }
                    existingEntity.setEmailAddress(user.getEmailAddress());
                    existingEntity.setName(user.getName());
                    return usersRepo.save(existingEntity);
                })
                .orElseThrow( () -> new EntityNotFoundException("Not Found user with user id: "+ id));
    }


}