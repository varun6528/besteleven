package com.verteil.besteleven.service.impl;

import com.verteil.besteleven.exception.CustomException;
import com.verteil.besteleven.model.User;
import com.verteil.besteleven.repository.UserRepository;
import com.verteil.besteleven.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginServiceImpl implements LoginService {
    ConcurrentHashMap<String, String> userDataMap = new ConcurrentHashMap<>();

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public LoginServiceImpl(final PasswordEncoder passwordEncoder, final UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public String userLogin(User user, HttpServletRequest request) {
        User existingUserDetail = userRepository.findByName(user.getName());
        if(null != existingUserDetail) {
            request.getSession().setAttribute("user", existingUserDetail);
            if (!isPasswordMatching(user, existingUserDetail)) {
                throw new CustomException("Password is wrong.");
            }
            return "redirect:/user/landing";
        } else if (isFirstAttempt(user)) {
            request.getSession().setAttribute("user", user);
            return "redirect:/changePassword";
        } else {
            throw new CustomException("User is not registered!");
        }
    }

    private boolean isPasswordMatching(User user, User existingUserDetail) {
        if (passwordEncoder.matches(user.getPassword(), existingUserDetail.getPassword())) {
            return true;
        }
        return false;
    }

    private boolean isFirstAttempt(User user) {
        if ("Verteil@123".equalsIgnoreCase(user.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
