package com.verteil.besteleven.service.impl;

import com.verteil.besteleven.model.Match;
import com.verteil.besteleven.model.PlayingEleven;
import com.verteil.besteleven.model.User;
import com.verteil.besteleven.repository.PlayingElevenRepository;
import com.verteil.besteleven.service.MatchService;
import com.verteil.besteleven.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final MatchService matchService;
    private final PlayingElevenRepository playingElevenRepository;

    @Autowired
    public UserServiceImpl(PlayingElevenRepository playingElevenRepository, MatchService matchService) {
        this.playingElevenRepository = playingElevenRepository;
        this.matchService = matchService;
    }

    @Override
    public User findUserScoreByMatch(int userId) {
        return null;
    }

    @Override
    public Map<String, List<User>> findAllScoreByMatch() {
        List<Match> matches = matchService.findByDate(LocalDate.now().minusDays(1L));
        if (CollectionUtils.isEmpty(matches)) {
            matches = matchService.findByDate(LocalDate.now().minusDays(2L));
            if (CollectionUtils.isEmpty(matches)) {
                matches = matchService.findByDate(LocalDate.now().minusDays(3L));
            }
        }
        return matches
                .stream()
                .map(match -> {
                    String team = match
                            .getFirstTeam()
                            .concat(" vs ")
                            .concat(match.getSecondTeam());
                    List<PlayingEleven> playingElevens = playingElevenRepository.findByMatchId(match.getId());
                    List<User> users = playingElevens
                            .stream()
                            .sorted(Comparator.comparing(PlayingEleven::getScore))
                            .map(this::createUser)
                            .sorted(Comparator
                                    .comparing(User::getScore)
                                    .reversed())
                            .collect(Collectors.toList());
                    return Map.of(team, users);
                })
                .flatMap(map -> map
                        .entrySet()
                        .stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public User findOverAllUserScore(User user) {
       List<User> users = findOverAllScore();
        int rank = 0;
        if(!CollectionUtils.isEmpty(users)) {
            rank = users.indexOf(user);
            if (-1 != rank) {
                User userWithScore = users.get(rank);
                user.setRank(rank + 1);
                user.setScore(userWithScore.getScore());
            }
        }
        return user;
    }

    @Override
    public Map<Integer, User> findOverAllScoreOfUserByMatch(String userId) {
        List<PlayingEleven> playingElevens = playingElevenRepository.findAll();

        Map<Integer, List<PlayingEleven>> playingElevenByMatchMap = playingElevens.stream()
                .collect(Collectors.groupingBy(PlayingEleven::getMatchId));
        return playingElevenByMatchMap.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> findPlayerRankByMatch(e.getKey(), userId, e.getValue())));

    }

    private User findPlayerRankByMatch(int matchId, String userId, List<PlayingEleven> playingElevens) {
        playingElevens.sort(Comparator.comparing(PlayingEleven::getScore).reversed());
        PlayingEleven playingEleven = playingElevenRepository.findByMatchIdAndSubmittedBy(matchId, userId);
        int rank = playingElevens.indexOf(playingEleven);
        User user = new User();
        user.setId(userId);
        user.setRank(rank + 1);
        if (null != playingEleven) {
            user.setScore(playingEleven.getScore());
        }
        return user;
    }

    @Override
    public List<User> findOverAllScore() {
        List<PlayingEleven> playingElevens = playingElevenRepository.findAll();

        Map<String, List<PlayingEleven>> playingElevenMap = playingElevens.stream()
                .collect(Collectors.groupingBy(PlayingEleven::getSubmittedBy));

        List<User> users = playingElevenMap.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> calculateOverAllScore(e.getValue())))
                .entrySet()
                .stream()
                .map(e -> createUser(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
        users.sort(Comparator.comparing(User::getScore).reversed());
        return users;
    }

    private int calculateOverAllScore(List<PlayingEleven> playingElevens) {
        return playingElevens.stream()
                .mapToInt(PlayingEleven::getScore)
                .sum();
    }

    private User createUser(PlayingEleven playingEleven) {
        User user = new User();
        // Populate User name by calling User repo
        user.setId(playingEleven.getSubmittedBy());
        user.setName(playingEleven.getSubmittedBy());
        user.setScore(playingEleven.getScore());
        return user;
    }

    private User createUser(String id, int score) {
        User user = new User();
        // Populate User name by calling User repo
        user.setId(id);
        user.setName(id);
        user.setScore(score);
        return user;
    }
}
