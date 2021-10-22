package com.verteil.besteleven.service.impl;

import com.verteil.besteleven.exception.CustomException;
import com.verteil.besteleven.model.User;
import com.verteil.besteleven.repository.UserRepository;
import com.verteil.besteleven.service.PasswordChangeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class PasswordChangeServiceImpl implements PasswordChangeService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordChangeServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean changePassword(User user, String oldPass, String newPass) {
        if (null == user) {
            throw new CustomException("Please login to change password");
        }
        User userData = userRepository.findByName(user.getName());
        if (null != userData && !passwordEncoder.matches(oldPass, userData.getPassword())) {
            throw new CustomException("Incorrect Password!");
        } else if ("Verteil@123".equalsIgnoreCase(oldPass) || null != userData) {
            User userToUpsert = null != userData ? userData : createUser(user);
            userToUpsert.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(userToUpsert);
        } else if (!"Verteil@123".equalsIgnoreCase(oldPass)) {
            throw new CustomException("Incorrect password");
        } else {
            throw new CustomException("User not registered.");
        }
        return true;
    }

    private User createUser(User user) {
        return User
                .builder()
                .id(user.getName())
                .name(user.getName())
                .admin(user.isAdmin())
                .rank(user.getRank())
                .score(user.getScore())
                .build();
    }
}
