package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UseridDTO;
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
    public boolean updateUser(UseridDTO useridDTO) {
        Optional<Users> userOptional = userRepository.findById(useridDTO.getId());
        if (userOptional.isEmpty()) {
            return false; // Không tìm thấy user cần cập nhật
        }

        // Kiểm tra email đã tồn tại ở user khác chưa
        boolean emailExists = userRepository.existsByEmailAndIdNot(useridDTO.getEmail(), useridDTO.getId());
        if (emailExists) {
            return false; // Email đã được sử dụng bởi user khác
        }

        Users user = userOptional.get();
        user.setFullname(useridDTO.getFullname());
        user.setEmail(useridDTO.getEmail());
        userRepository.save(user);
        return true;
    }


    @Override
    public List<UseridDTO> getuserbyid(int id) {
        Optional<Users> users = userRepository.findById(id);
        List<UseridDTO> userDTOS = new ArrayList<>();
        if(users.isPresent()){
            UseridDTO useridDTO = new UseridDTO();
            useridDTO.setId(users.get().getId());
            useridDTO.setEmail(users.get().getEmail());
            useridDTO.setFullname(users.get().getFullname());
            useridDTO.setPhone(users.get().getPhone());
            userDTOS.add(useridDTO);
        }
        return userDTOS;
    }


}
