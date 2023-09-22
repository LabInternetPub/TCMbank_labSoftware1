package com.tecnocampus.tcmbank.application;

import com.tecnocampus.tcmbank.application.dto.UserDTO;
import com.tecnocampus.tcmbank.domain.User;
import com.tecnocampus.tcmbank.persistence.AccountRepository;
import com.tecnocampus.tcmbank.persistence.UserRepository;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserController(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public UserDTO createUser(UserDTO userDTO) throws Exception {
        User user = new User(userDTO);

        userRepository.save(user);

        return new UserDTO(user);
    }

    public UserDTO getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();

        return new UserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll().stream().toList();

        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public UserDTO updateUser(String userId, UserDTO userDTO) throws Exception {
        User user = userRepository.findById(userId).orElseThrow();

        user.updateUser(userDTO);
        userRepository.save(user);

        return new UserDTO(user);
    }

    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();

        accountRepository.removeAllByUserId(userId);

        userRepository.delete(user);
    }

    public void deleteAllUsers() {
        accountRepository.deleteAll();
        userRepository.deleteAll();
    }
}