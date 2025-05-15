package com.example.demo.service.imp;

import com.example.demo.dto.UserDTO;

import java.util.List;

public interface UserServiceimp {
    List<UserDTO> getAlluser();
    boolean deleteuser(int id);
    boolean updateUser(UserDTO user);
}
