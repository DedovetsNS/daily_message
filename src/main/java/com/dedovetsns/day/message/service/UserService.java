package com.dedovetsns.day.message.service;

import com.dedovetsns.day.message.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    String getUserIdByName(String name) {
        return String.valueOf(userRepository.findByUsername(name).getId());
    }
}
