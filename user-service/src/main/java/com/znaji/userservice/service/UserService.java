package com.znaji.userservice.service;


import com.znaji.userservice.domain.User;
import com.znaji.userservice.dto.CreateUserRequest;
import com.znaji.userservice.dto.UpdateUserRequest;
import com.znaji.userservice.dto.UserAlertPreferenceResponse;
import com.znaji.userservice.dto.UserResponse;
import com.znaji.userservice.exception.DuplicateEmailException;
import com.znaji.userservice.exception.UserNotFoundException;
import com.znaji.userservice.mapper.UserMapper;
import com.znaji.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateEmailException(request.email());
        }

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    public UserResponse getUserById(Long id) {
        User user = findUserById(id);
        return userMapper.toResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User existingUser = findUserById(id);

        if (userRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new DuplicateEmailException(request.email());
        }

        userMapper.updateEntity(existingUser, request);
        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponse(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public List<UserAlertPreferenceResponse> getEnabledAlertPreferences() {
        return userRepository.findByAlertingEnabledTrue()
                .stream()
                .map(userMapper::toAlertPreferenceResponse)
                .toList();
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}