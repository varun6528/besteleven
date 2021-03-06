package com.verteil.besteleven.service.impl;

import com.verteil.besteleven.model.*;
import com.verteil.besteleven.repository.MatchSummaryRepository;
import com.verteil.besteleven.repository.PlayerRespository;
import com.verteil.besteleven.repository.PlayingElevenRepository;
import com.verteil.besteleven.service.PlayingElevenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlayingElevenServiceImpl implements PlayingElevenService {

    private PlayingElevenRepository playingElevenRepository;
    private MatchSummaryRepository matchSummaryRepository;
    private PlayerRespository playerRespository;

    @Autowired
    public PlayingElevenServiceImpl(PlayingElevenRepository playingElevenRepository, MatchSummaryRepository matchSummaryRepository, PlayerRespository playerRespository) {
        this.playingElevenRepository = playingElevenRepository;
        this.matchSummaryRepository = matchSummaryRepository;
        this.playerRespository = playerRespository;
    }

    @Override
    public PlayingEleven fetchTeam(String id) {
        return PlayingEleven.builder().build();
    }

    @Override
    public void saveTeam(PlayingEleven playingEleven) {
        playingEleven.setSubmittedDate(LocalDate.now());
        playingElevenRepository.save(playingEleven);
    }

    @Override
    public PlayingEleven findByUserNameAndMatchId(String user, int matchId) {
        return playingElevenRepository.findBySubmittedBy(user).stream()
                .filter(p -> p.getMatchId() == matchId)
                .findFirst()
                .orElse(new PlayingEleven());
    }

    public List<PlayingEleven> findByUserName(String user) {
        return playingElevenRepository.findBySubmittedBy(user);
    }

    @Override
    public List<PlayingEleven> findByMatch(int matchId) {
        return playingElevenRepository.findByMatchId(matchId);
    }

    @Override
    public void prepareScore(MatchSummary matchSummary) {
        List<PlayingEleven> playingElevens = playingElevenRepository.findByMatchId(matchSummary.getMatchId());
        List<ScoreCard> scoreCards = matchSummary.getScoreCards();

        Map<Integer, Integer> playerScoreMap = scoreCards.stream()
                .collect(Collectors.toMap(ScoreCard::getPlayerId, ScoreCard::getScore));

        Map<String, PlayingEleven> playingElevenMap = playingElevens.stream()
                .collect(Collectors.toMap(PlayingEleven::getSubmittedBy, Function.identity()));

        Map<String, Integer> userScoreMap = playingElevenMap.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> calculateScore(playerScoreMap, e.getValue(), matchSummary)));

        playingElevens.forEach(p -> p.setScore(userScoreMap.get(p.getSubmittedBy())));
        playingElevenRepository.saveAll(playingElevens);
    }

    private int calculateScore(Map<Integer, Integer> playerScoreMap, PlayingEleven playingEleven, MatchSummary matchSummary) {
        log.info("Player Score Map : {}", playerScoreMap);
        log.info("Playing Eleven : {}", playingEleven);
        int score = playingEleven.getPlayers().stream()
                .mapToInt(p -> playerScore(playerScoreMap, p.getId()))
                .sum();
        int additionalScore = calculateAdditionalScores(playingEleven, matchSummary);
        if (matchSummary.getMomId().equals(playingEleven.getManOfTheMatchSelected())) {
            score += playerScoreMap.get(matchSummary.getMomId());
        }
        return score + additionalScore;
    }

    private int playerScore(Map<Integer, Integer> playerScoreMap, int id) {
        Integer score = playerScoreMap.get(id);
        return score == null ? 0 : score;
    }


    private int calculateAdditionalScores(PlayingEleven playingEleven, MatchSummary matchSummary) {

        int additionalScore = 0;
        if (matchSummary.getWinner().equalsIgnoreCase(playingEleven.getWinner())) {
            additionalScore += 50;
        }

        Map<PlayerType, List<Player>> playerTypes = playingEleven.getPlayers().stream()
                .map(p -> playerRespository.findById(p.getId()))
                .filter(p -> p.getId() != null)
                .collect(Collectors.groupingBy(Player::getType));

        int batters = 0;
        int bowlers = 0;
        int wicketKeepers = 0;
        int allRounders = 0;

        if (!CollectionUtils.isEmpty(playerTypes.get(PlayerType.BATTER))) {
            batters = playerTypes.get(PlayerType.BATTER).size();
        }

        if (!CollectionUtils.isEmpty(playerTypes.get(PlayerType.BOWLER))) {
            bowlers = playerTypes.get(PlayerType.BOWLER).size();
        }

        if (!CollectionUtils.isEmpty(playerTypes.get(PlayerType.ALL_ROUNDER))) {
            wicketKeepers = playerTypes.get(PlayerType.ALL_ROUNDER).size();
        }

        if (!CollectionUtils.isEmpty(playerTypes.get(PlayerType.WICKET_KEEPER))) {
            allRounders = playerTypes.get(PlayerType.WICKET_KEEPER).size();
        }

        if (batters >= 4 &&
                bowlers >= 2 &&
                wicketKeepers >= 1 &&
                allRounders >= 1) {
            additionalScore += 50;
        }

        return additionalScore;
    }

    public void setPlayerRespository(PlayerRespository playerRespository) {
        this.playerRespository = playerRespository;
    }
}