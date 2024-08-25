package com.demo.booking.train.service;

import com.demo.booking.train.dto.UserRequest;
import com.demo.booking.train.model.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface UserService {

    User createUser(UserRequest createRequest);

    User updateUser(Long userId, UserRequest updateRequest);

    void deleteUser(Long userId);

    Collection<User> getAllUsers();

    Optional<User> getUserById(Long userId);
}

