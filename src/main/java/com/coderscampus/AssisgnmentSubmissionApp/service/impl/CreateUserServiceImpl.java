package com.coderscampus.AssisgnmentSubmissionApp.service.impl;

import com.coderscampus.AssisgnmentSubmissionApp.model.User;
import com.coderscampus.AssisgnmentSubmissionApp.repository.UserRepository;
import com.coderscampus.AssisgnmentSubmissionApp.service.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUserServiceImpl implements CreateUserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}