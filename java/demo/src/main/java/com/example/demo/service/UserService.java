package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Users;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.imp.UserServiceimp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class UserService implements UserServiceimp {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<UserDTO> getAlluser(){
        List<Users> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (Users user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setFullname(user.getFullname());
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    @Override
    public boolean deleteuser(int id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        Optional<Users> user = userRepository.findById(userDTO.getId());
        if(user.isPresent()){
            Users users = user.get();
            users.setFullname(userDTO.getFullname());
            users.setEmail(userDTO.getEmail());
            String password = passwordEncoder.encode(userDTO.getPassword());
            users.setPassword(password);
            userRepository.save(users);
            return true;
        }
        return false;
    }


}
