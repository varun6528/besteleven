package com.verteil.besteleven.controller;

import com.verteil.besteleven.model.Match;
import com.verteil.besteleven.model.User;
import com.verteil.besteleven.service.MatchService;
import com.verteil.besteleven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Controller
public class UserController {

    private final UserService userService;
    private final MatchService matchService;

    @Autowired
    public UserController(UserService userService, MatchService matchService) {
        this.userService = userService;
        this.matchService = matchService;
    }

    @GetMapping("/user/landing")
    public String landing(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        User userWithRank = userService.findOverAllUserScore(user);
        model.addAttribute("user", userWithRank);
        return "landing";
    }

    @GetMapping("/user/score")
    public String fetchScores(HttpServletRequest request, Model model, @RequestParam(name = "matchId", required = false) Integer matchId) {
        User user = (User) request.getSession().getAttribute("user");
        if (nonNull(matchId) && matchId != 0) {
            return fetchMatchWiseScore(user, matchId, model);
        }
        final var matches = fetchCompletedMatches();
        model.addAttribute("date", LocalDate.now().minusDays(1L));
        model.addAttribute("dailyLeaders", userService.findAllScoreByMatch());
        model.addAttribute("usersOverall", userService.findOverAllScore());
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("matches", matches);
        return "LeaderBoard";
    }

    private String fetchMatchWiseScore(User user, int matchId, Model model) {
        final var match = matchService.findById(matchId);
        final var matchDayLeaders = userService.findLeadersByMatch(matchId);
        final var matches = fetchCompletedMatches();
        model.addAttribute("match", match);
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("matchDayLeaders", matchDayLeaders);
        model.addAttribute("matches", matches);
        return "matchWiseLeaders";
    }

    private List<Match> fetchCompletedMatches() {
        return matchService.findAll()
                .stream()
                .filter(Match::isMatchOver)
                .collect(Collectors.toList());
    }
}
