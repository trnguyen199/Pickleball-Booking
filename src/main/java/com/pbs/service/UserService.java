package com.pbs.service;

import com.pbs.dto.UserDto;
import com.pbs.model.Role;
import com.pbs.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(UUID id);
    Optional<User> getUserByEmail(String email);
    List<User> getUsersByRole(Role role);
    User createUser(UserDto userDto);
    User updateUser(UUID id, UserDto userDto);
    void deleteUser(UUID id);
    boolean emailExists(String email);
}
