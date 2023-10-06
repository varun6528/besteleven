package com.verteil.besteleven.controller;

import com.verteil.besteleven.model.*;
import com.verteil.besteleven.service.MatchService;
import com.verteil.besteleven.service.PlayingElevenService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.verteil.besteleven.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class PlayingElevenController {

    private final PlayingElevenService playingElevenService;
    private final MatchService matchService;
    private final TeamService teamService;

    @Autowired
    public PlayingElevenController(PlayingElevenService playingElevenService, MatchService matchService, TeamService teamService) {
        this.playingElevenService = playingElevenService;
        this.matchService = matchService;
        this.teamService = teamService;
    }

    @GetMapping("/index")
    public String fetchTeam(Model model) {
        model.addAttribute("team", playingElevenService.fetchTeam("id"));
        return "index";
    }

    @GetMapping("/addTeam")
    public String showAddTeamForm(HttpServletRequest request, @RequestParam("matchId") int matchId, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        PlayingEleven playingEleven = new PlayingEleven();
        Match match = matchService.findById(matchId);
        var firstTeam = teamService.findByCountry(match.getFirstTeam());
        var secondTeam = teamService.findByCountry(match.getSecondTeam());
        PlayingEleven saved = playingElevenService.findByUserNameAndMatchId(user.getId(), matchId);
        var players = fetchAllPlayers(firstTeam, secondTeam);
        if (Objects.nonNull(saved.getId())) {
            playingEleven = saved;
            model.addAttribute("selectedPlayers", fetchSelectedPlayers(saved, players));
        }
        model.addAttribute("playingEleven", playingEleven);
        model.addAttribute("matchId", matchId);
        model.addAttribute("firstTeam", firstTeam);
        model.addAttribute("secondTeam", secondTeam);
        model.addAttribute("allPlayers", players);
        model.addAttribute("match", match);
        model.addAttribute("user", user);
        extractImpactPlayer(playingEleven, players)
                .ifPresent(impactPlayer -> model.addAttribute("impactPlayer", impactPlayer));
        return "addTeam";
    }

    private Optional<Player> extractImpactPlayer(PlayingEleven playingEleven, List<Player> players) {
        return players.stream()
                .filter(player -> player.getId() == playingEleven.getManOfTheMatchSelected())
                .findFirst();
    }

    private List<Player> fetchSelectedPlayers(PlayingEleven saved, List<Player> players) {
        var savedPlayerIds = saved.getPlayers().stream()
                .map(Player::getId)
                .collect(Collectors.toList());
        return players.stream()
                .filter(player -> savedPlayerIds.contains(player.getId()))
                .map(player -> updatePlayerDetails(player, saved.getPlayers()))
                .collect(Collectors.toList());
    }

    private Player updatePlayerDetails(Player player, List<Player> savedPlayers) {
        savedPlayers.stream()
                .filter(savedPlayer -> Objects.equals(savedPlayer.getId(), player.getId()))
                .findFirst()
                .ifPresent(savedPlayer -> player.setMatchScore(savedPlayer.getMatchScore()));
        return player;
    }

    @PostMapping("/saveTeam")
    public String saveTeam(HttpServletRequest request, PlayingEleven playingEleven, Model model) {
        User user = (User) request.getSession().getAttribute("user");
        playingEleven.setSubmittedBy(user.getId());
        playingElevenService.saveTeam(playingEleven);
        return "redirect:/match";
    }

    @PutMapping("/updateTeam/{id}")
    public String updateTeam(PlayingEleven team, @PathVariable("id") String id, Model model) {
        playingElevenService.saveTeam(team);
        return "redirect:/index";
    }

    private List<Player> fetchAllPlayers(Team firstTeam, Team secondTeam) {
        var allPlayers = new ArrayList<>(firstTeam.getPlayers());
        allPlayers.addAll(new ArrayList<>(secondTeam.getPlayers()));
        return allPlayers;
    }
}
