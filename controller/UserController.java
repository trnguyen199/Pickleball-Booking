package com.example.userapi.controller;

import com.userapi.controller.*;
import com.userapi.controller.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.userapi.dto.*;
import com.example.userapi.entity.User;
import com.example.userapi.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/profile")
    public com.userapi.controller.ResponseEntity<UserResponse> updateProfile(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UpdateUserRequest request) {
        User updatedUser = userService.updateUser(currentUser.getId(), request);
        return ResponseEntity.ok(new UserResponse(updatedUser));
    }

    @PutMapping("/change-password")
    public ResponseEntity<String>  \\(
            @AuthenticationPrincipal User currentUser,
            @RequestBody ChangePasswordRequest request) {
        userService.changePassword(currentUser.getId(), request);
        return ResponseEntity.ok("Password changed successfully");
    }
}