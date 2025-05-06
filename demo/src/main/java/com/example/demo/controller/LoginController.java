package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Users;
import com.example.demo.payload.ResponData;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.signupRequest;
import com.example.demo.service.imp.LoginServiceimp;
import com.example.demo.untils.JwtUntilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/login")

public class LoginController {
    @Autowired
    LoginServiceimp LoginServiceimp;
    @Autowired
    JwtUntilHelper jwtUntilHelper;
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password) {
        ResponData responseData = new ResponData();

        // 1. Kiểm tra các trường không được bỏ trống
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            responseData.setSuccess(false);
            responseData.setMessage("Email and password are required");
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // 2. Kiểm tra thông tin đăng nhập
        String checkLogin = LoginServiceimp.checkLogin(email, password);
        System.out.println(checkLogin);
        if (!checkLogin.isEmpty()) {
            String token = jwtUntilHelper.generateToken(email,checkLogin);
            responseData.setSuccess(true);
            responseData.setData(token);
            responseData.setMessage("Login successful");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } else {
            responseData.setSuccess(false);
            responseData.setMessage("Invalid email or password");
            return new ResponseEntity<>(responseData, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody signupRequest signupRequest) {
        ResponData ResponData = new ResponData();
        if(!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            ResponData.setSuccess(false);
            ResponData.setMessage("Passwords do not match");
            return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
        }

        if (signupRequest.getFullName() == null || signupRequest.getFullName().isEmpty() ||
                signupRequest.getPhone() == null || signupRequest.getPhone().isEmpty() ||
                signupRequest.getEmail() == null || signupRequest.getEmail().isEmpty() ||
                signupRequest.getPassword() == null || signupRequest.getPassword().isEmpty() ||
                signupRequest.getConfirmPassword() == null || signupRequest.getConfirmPassword().isEmpty()) {

            ResponData.setSuccess(false);
            ResponData.setMessage("Please fill in all required fields");
            return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
        }
        if(LoginServiceimp.emailExists(signupRequest.getEmail())) {
            ResponData.setSuccess(false);
            ResponData.setMessage("Email already exists");
            return new ResponseEntity<>(ResponData, HttpStatus.CONFLICT);
        }
        boolean creareUser = LoginServiceimp.addUser(signupRequest);
        if(creareUser) {
            ResponData.setSuccess(true);
            ResponData.setMessage("User successfully added");
            return new ResponseEntity<>(ResponData, HttpStatus.CREATED);
        }else{
            ResponData.setSuccess(false);
            ResponData.setMessage("Signup failed");
            return new ResponseEntity<>(ResponData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/home")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> userDTOS = LoginServiceimp.getAlluser();
        return new ResponseEntity<>( userDTOS, HttpStatus.OK);
    }



    @PostMapping("/{id}/ChangePassword")
    public ResponseEntity<?> changePassword(@PathVariable int id, @RequestBody ChangePasswordRequest changePasswordRequest) {
        ResponData ResponData = new ResponData();

        if(!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            ResponData.setSuccess(Boolean.FALSE);
            ResponData.setMessage("Passwords do not match");
            return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
        }else{
            boolean result = LoginServiceimp.changePassword(id, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());
            if(result) {
                ResponData.setSuccess(Boolean.TRUE);
                ResponData.setMessage("Password changed");
                return new ResponseEntity<>(ResponData, HttpStatus.OK);
            }else{
                ResponData.setSuccess(Boolean.FALSE);
                ResponData.setMessage("Password do not match");
                return new ResponseEntity<>(ResponData, HttpStatus.BAD_REQUEST);
            }
        }

    }

}
