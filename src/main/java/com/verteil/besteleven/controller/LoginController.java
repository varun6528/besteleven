package com.verteil.besteleven.controller;

import com.verteil.besteleven.exception.CustomException;
import com.verteil.besteleven.model.User;
import com.verteil.besteleven.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public String login(final Model model){
        model.addAttribute("user", new User());
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(final HttpServletRequest request, final @Valid User user,
            final BindingResult bindingResult, final Model model) {
        try {
            user.setId(user.getName());
            model.addAttribute("user", user);
            return loginService.userLogin(user, request);
        } catch (CustomException ex) {
            if (ex.getMessage().matches("Password(.*)")) {
                bindingResult.rejectValue("password", "user.password", ex.getMessage());
            } else {
                bindingResult.rejectValue("name", "user.name", ex.getMessage());
            }
            return "loginPage";
        }
    }
}
