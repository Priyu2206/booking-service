package com.demo.booking.train.impl;

import com.demo.booking.train.dto.UserRequest;
import com.demo.booking.train.exception.UserAlreadyExistsException;
import com.demo.booking.train.exception.UserNotFoundException;
import com.demo.booking.train.model.User;
import com.demo.booking.train.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserServiceImpl implements UserService {

    // Map to store users with a generated ID
    private final Map<Long, User> usersMap = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    private final AtomicLong idCounter = new AtomicLong();

    @Override
    public User createUser(UserRequest userRequest) {
        String email = userRequest.getEmail();
        if (emails.contains(userRequest.getEmail())) {
            throw new UserAlreadyExistsException(email);
        }
        User user = User.build(userRequest);
        emails.add(user.getEmail());
        Long id = idCounter.incrementAndGet();
        user.setId(id);
        usersMap.put(id, user);
        return user;
    }

    @Override
    public User updateUser(Long userId, UserRequest updateRequest) {
        User existingUser = usersMap.get(userId);
        if (existingUser == null) {
            throw new UserNotFoundException(updateRequest.getEmail());
        }

        // Update user details
        if (StringUtils.hasLength(updateRequest.getEmail()) &&
                !updateRequest.getEmail().equals(existingUser.getEmail()) &&
                emails.contains(updateRequest.getEmail())) {
            throw new UserAlreadyExistsException(updateRequest.getEmail());
        }

        if (StringUtils.hasLength(updateRequest.getFirstName())) {
            existingUser.setFirstName(updateRequest.getFirstName());
        }

        if (StringUtils.hasLength(updateRequest.getLastName())) {
            existingUser.setLastName(updateRequest.getLastName());
        }

        usersMap.put(userId, existingUser);
        return existingUser;
    }

    @Override
    public void deleteUser(Long id) {
        User user = usersMap.get(id);
        if (user == null) {
            throw new UserNotFoundException(String.valueOf(id));
        }
        emails.remove(user.getEmail());
        usersMap.remove(id);
    }

    @Override
    public Collection<User> getAllUsers() {
        return usersMap.values();
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return Optional.ofNullable(usersMap.get(userId));
    }
}
