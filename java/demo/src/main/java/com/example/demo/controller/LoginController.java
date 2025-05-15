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
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password  ) {
        ResponData ResponData = new ResponData();
        boolean checkLogin = LoginServiceimp.checkLogin(email, password);
        if(checkLogin) {
            String token = jwtUntilHelper.generateToken(email);
            ResponData.setData(token);
        }else{
            ResponData.setData("");
            ResponData.setSuccess(Boolean.FALSE);   
        }
        return new ResponseEntity<>( ResponData, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody signupRequest signupRequest) {
        ResponData ResponData = new ResponData();
        ResponData.setData(LoginServiceimp.addUser(signupRequest));
        return new ResponseEntity<>( ResponData, HttpStatus.OK);
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
