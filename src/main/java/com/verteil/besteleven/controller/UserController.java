package com.verteil.besteleven.controller;

import com.verteil.besteleven.model.User;
import com.verteil.besteleven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/landing")
    public String landing(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        User userWithRank = userService.findOverAllUserScore(user);
        model.addAttribute("user", userWithRank);
        return "landing";
    }

    @GetMapping("/user/score")
    public String fetchScores(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("date", LocalDate.now().minusDays(1L));
        model.addAttribute("dailyLeaders", userService.findAllScoreByMatch());
        model.addAttribute("usersOverall", userService.findOverAllScore());
        model.addAttribute("isAdmin", user.isAdmin());
        return "LeaderBoard";
    }
}
