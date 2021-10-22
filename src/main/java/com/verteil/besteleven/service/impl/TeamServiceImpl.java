package com.verteil.besteleven.service.impl;

import com.verteil.besteleven.model.Team;
import com.verteil.besteleven.repository.PlayerRespository;
import com.verteil.besteleven.repository.TeamRepository;
import com.verteil.besteleven.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private PlayerRespository playerRespository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, PlayerRespository playerRespository) {
        this.teamRepository = teamRepository;
        this.playerRespository = playerRespository;
    }

    @PostConstruct
    public void init() {
        List<Team> teams = teamRepository.findAll();
        teams.forEach(team -> team.setPlayers(playerRespository.findByCountry(team.getCountry())));
    }

    @Override
    public Team findByCountry(String country) {
        return teamRepository.findByCountry(country);
    }
}
