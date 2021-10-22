package com.verteil.besteleven.service;

import com.verteil.besteleven.model.Match;
import com.verteil.besteleven.model.MatchSummary;
import com.verteil.besteleven.model.PlayingEleven;

import java.util.List;

public interface PlayingElevenService {

    PlayingEleven fetchTeam(String id);

    void saveTeam(PlayingEleven team);

    PlayingEleven findByUserNameAndMatchId(String user, int matchId);

    List<PlayingEleven> findByUserName(String user);

    List<PlayingEleven> findByMatch(int matchId);

    void prepareScore(MatchSummary matchSummary);

}
