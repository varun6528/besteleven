package com.verteil.besteleven.controller;

import com.verteil.besteleven.model.MatchSummary;
import com.verteil.besteleven.model.User;
import com.verteil.besteleven.service.MatchService;
import com.verteil.besteleven.service.MatchSummaryService;
import com.verteil.besteleven.service.PlayingElevenService;
import com.verteil.besteleven.service.UserService;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Controller
public class MatchController {

    private MatchService matchService;
    private PlayingElevenService playingElevenService;
    private MatchSummaryService matchSummaryService;
    private UserService userService;

    @Autowired
    public MatchController(MatchService matchService, PlayingElevenService playingElevenService, MatchSummaryService matchSummaryService, UserService userService) {
        this.matchService = matchService;
        this.playingElevenService = playingElevenService;
        this.matchSummaryService = matchSummaryService;
        this.userService = userService;
    }

    @GetMapping("/match")
    public String match(HttpServletRequest request, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        Map<Integer, User> userScores = userService.findOverAllScoreOfUserByMatch(user.getId());
        log.info("User Score Map : {}",userScores);
        model.addAttribute("user", user);
        model.addAttribute("schedule", matchService.findAll());
        model.addAttribute("userScores",userScores);
        return "matchSchedule";
    }

    @GetMapping("/match/calculate")
    public String calculate(@RequestParam("matchId") int matchId, Model model) {
        MatchSummary matchSummary = matchSummaryService.findByMatchId(matchId);
        return "redirect:/user/score";
    }
}
