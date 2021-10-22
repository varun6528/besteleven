package com.verteil.besteleven.controller;

import com.verteil.besteleven.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LogOutController {

    @GetMapping("/logout")
    public String logOut(final HttpServletRequest httpServletRequest, final Model model) {
        httpServletRequest.getSession().removeAttribute("user");
        model.addAttribute("user", new User());
        return "redirect:/login";
    }
}
