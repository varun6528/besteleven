package com.verteil.besteleven.service;

import com.verteil.besteleven.model.*;
import com.verteil.besteleven.repository.MatchSummaryRepository;
import com.verteil.besteleven.repository.PlayerRespository;
import com.verteil.besteleven.repository.PlayingElevenRepository;
import com.verteil.besteleven.service.impl.PlayingElevenServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class PlayingElevenServiceTest {

    @InjectMocks
    private PlayingElevenServiceImpl playingElevenService;

    @Mock
    private PlayingElevenRepository playingElevenRepository;

    @Mock
    private MatchSummaryRepository matchSummaryRepository;

   // @Mock
    private PlayerRespository playerRespository;

    @Test
    public void testPrepareScore() {
        playerRespository = new PlayerRespository();
        playerRespository.init();
        playingElevenService.setPlayerRespository(playerRespository);

        int matchId = 100;
        MatchSummary matchSummary = prepareMatchSummary().get();
        List<PlayingEleven> playingElevens = preparePlayingElevens();
        when(playingElevenRepository.findByMatchId(matchId)).thenReturn(playingElevens);

        playingElevenService.prepareScore(matchSummary);

        System.out.println("------------------------------------------------\n\n");
        System.out.println(playingElevens);
        assertThat(playingElevens, contains(
                hasProperty("score", is(125)),
                hasProperty("score", is(70)),
                hasProperty("score", is(85)),
                hasProperty("score", is(160))
        ));
    }

    private List<PlayingEleven> preparePlayingElevens() {
        List<PlayingEleven> playingElevens = new ArrayList<>();

        PlayingEleven p1 = new PlayingEleven();
        p1.setId("10");
        p1.setMatchId(100);
        p1.setSubmittedBy("user one");
        p1.setPlayers(preparePlayers("one"));

        PlayingEleven p2 = new PlayingEleven();
        p2.setId("11");
        p2.setMatchId(100);
        p2.setSubmittedBy("user two");
        p2.setPlayers(preparePlayers("two"));

        PlayingEleven p3 = new PlayingEleven();
        p3.setId("12");
        p3.setMatchId(100);
        p3.setSubmittedBy("user three");
        p3.setPlayers(preparePlayers("three"));

        PlayingEleven p4 = new PlayingEleven();
        p4.setId("13");
        p4.setMatchId(100);
        p4.setSubmittedBy("user four");
        p4.setPlayers(preparePlayers("four"));
        p4.setWinner("IND");

        playingElevens.add(p1);
        playingElevens.add(p2);
        playingElevens.add(p3);
        playingElevens.add(p4);
        return playingElevens;
    }

    private Optional<MatchSummary> prepareMatchSummary() {
        List<ScoreCard> scoreCards = new ArrayList<>();
        ScoreCard s1 = new ScoreCard();
        s1.setId(1);
        s1.setMatchId(100);
        s1.setPlayerId(16);
        s1.setScore(60);

        ScoreCard s2 = new ScoreCard();
        s2.setId(2);
        s2.setMatchId(100);
        s2.setPlayerId(17);
        s2.setScore(65);

        ScoreCard s3 = new ScoreCard();
        s3.setId(3);
        s3.setMatchId(100);
        s3.setPlayerId(37);
        s3.setScore(45);

        ScoreCard s4 = new ScoreCard();
        s4.setId(4);
        s4.setMatchId(100);
        s4.setPlayerId(39);
        s4.setScore(25);

        scoreCards.add(s1);
        scoreCards.add(s2);
        scoreCards.add(s3);
        scoreCards.add(s4);

        MatchSummary matchSummary = new MatchSummary();
        matchSummary.setMatchId(100);
        matchSummary.setScoreCards(scoreCards);
        matchSummary.setWinner("IND");
        return Optional.of(matchSummary);
    }

    private List<Player> preparePlayers(String name) {
        List<Player> players = new ArrayList<>();
        Player p1 = new Player();
        p1.setId(16);
        p1.setName("kohli");
        p1.setType(PlayerType.BATTER);

        Player p2 = new Player();
        p2.setId(17);
        p2.setName("Rohit");
        p2.setType(PlayerType.BATTER);

        Player p3 = new Player();
        p3.setId(39);
        p3.setName("Smith");
        p3.setType(PlayerType.BATTER);


        Player p4 = new Player();
        p4.setId(37);
        p4.setName("Maxwell");
        p4.setType(PlayerType.ALL_ROUNDER);


        if (name.contains("one")) {
            players.add(p1);
            players.add(p2);
        } else if (name.contains("two")) {
            players.add(p3);
            players.add(p4);
        } else if (name.contains("three")) {
            players.add(p1);
            players.add(p3);
        } else {
            players.add(p2);
            players.add(p4);
        }
        return players;
    }
}
