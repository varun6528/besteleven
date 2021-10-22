package com.verteil.besteleven.controller;

import com.verteil.besteleven.model.*;
import com.verteil.besteleven.service.MatchService;
import com.verteil.besteleven.service.MatchSummaryService;
import com.verteil.besteleven.service.PlayingElevenService;
import com.verteil.besteleven.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class MatchSummaryController {

    private final MatchSummaryService matchSummaryService;
    private final MatchService matchService;
    private final TeamService teamService;
    private final PlayingElevenService playingElevenService;

    @Autowired
    public MatchSummaryController(MatchSummaryService matchSummaryService,
                                  MatchService matchService,
                                  TeamService teamService,
                                  PlayingElevenService playingElevenService) {
        this.matchSummaryService = matchSummaryService;
        this.matchService = matchService;
        this.teamService = teamService;
        this.playingElevenService = playingElevenService;
    }

    @GetMapping("/matchSummary")
    public String showScoreForm(final HttpServletRequest httpServletRequest, @RequestParam("matchId") int matchId,
            Model model) {
        Match match = matchService.findById(matchId);
        log.info("Match : {}", match);
        var firstTeam = teamService.findByCountry(match.getFirstTeam());
        var secondTeam = teamService.findByCountry(match.getSecondTeam());
        var players = fetchAllPlayers(firstTeam, secondTeam);
        model.addAttribute("match", match);
        model.addAttribute("firstTeam", firstTeam);
        model.addAttribute("secondTeam", secondTeam);
        model.addAttribute("allPlayers", players);
        model.addAttribute("matchSummary", new MatchSummary());
        return "matchSummary";
    }

    @PostMapping("/matchSummary")
    public String save(MatchSummary matchSummary) {
        matchSummaryService.save(matchSummary);
        playingElevenService.prepareScore(matchSummary);
        return "redirect:/user/landing";
    }

    private List<Player> fetchAllPlayers(Team firstTeam, Team secondTeam) {
        var allPlayers = new ArrayList<>(firstTeam.getPlayers());
        allPlayers.addAll(new ArrayList<>(secondTeam.getPlayers()));
        return allPlayers;
    }
}
