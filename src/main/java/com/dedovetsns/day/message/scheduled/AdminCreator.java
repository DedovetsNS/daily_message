package com.dedovetsns.day.message.scheduled;


import com.dedovetsns.day.message.model.Role;
import com.dedovetsns.day.message.model.User;
import com.dedovetsns.day.message.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
class AdminCreator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    AdminCreator(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void createAdmin() {
        User user = userRepository.findByUsername("admin");
        if (user == null) {
            user = new User();
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.ADMIN));
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);
    }}}

