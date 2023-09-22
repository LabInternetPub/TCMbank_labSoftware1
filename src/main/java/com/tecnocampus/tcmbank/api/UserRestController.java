package com.tecnocampus.tcmbank.api;

import com.tecnocampus.tcmbank.application.UserController;
import com.tecnocampus.tcmbank.application.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {
    private final UserController userController;

    public UserRestController(UserController userController) {this.userController = userController;}

    @PostMapping("/users")
    public UserDTO createUser(@RequestBody UserDTO userDTO) throws Exception {
        return userController.createUser(userDTO);
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable String id) {return userController.getUser(id);}

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {return userController.getAllUsers();}

    @PutMapping("/users/{id}")
    public UserDTO updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) throws Exception {
        return userController.updateUser(id, userDTO);
    }

    @DeleteMapping("users/{id}")
    public void deleteUser(@PathVariable String id) {userController.deleteUser(id);}

    @DeleteMapping("/users")
    public void deleteAllUsers() {userController.deleteAllUsers();}
}