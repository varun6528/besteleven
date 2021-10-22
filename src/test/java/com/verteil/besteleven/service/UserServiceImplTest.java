//package com.verteil.besteleven.service;
//
//import com.verteil.besteleven.model.Player;
//import com.verteil.besteleven.model.PlayerType;
//import com.verteil.besteleven.model.PlayingEleven;
//import com.verteil.besteleven.model.User;
//import com.verteil.besteleven.repository.PlayingElevenRepository;
//import com.verteil.besteleven.service.impl.UserServiceImpl;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.contains;
//import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
//import static org.mockito.Mockito.when;
//
//@RunWith(MockitoJUnitRunner.class)
//public class UserServiceImplTest {
//
//    @InjectMocks
//    private UserServiceImpl userService;
//
//    @Mock
//    private PlayingElevenRepository playingElevenRepository;
//
//    @Test
//    public void testFindAllScoreByMatch() {
//        int matchId = 100;
//        when(playingElevenRepository.findByMatchId(matchId)).thenReturn(preparePlayingElevens(1));
//        List<User> users = userService.findAllScoreByMatch(matchId);
//        assertThat(users, contains(
//                hasProperty("score", is(180)),
//                hasProperty("score", is(155)),
//                hasProperty("score", is(130)),
//                hasProperty("score", is(115))
//        ));
//    }
//
//    @Test
//    public void testFindOverAllScore() {
//        int matchId = 100;
//        when(playingElevenRepository.findAll()).thenReturn(preparePlayingElevens(5));
//        List<User> users = userService.findOverAllScore();
//        assertThat(users, contains(
//                hasProperty("score", is(950)),
//                hasProperty("score", is(825)),
//                hasProperty("score", is(700)),
//                hasProperty("score", is(625))
//        ));
//    }
//
//
//    private List<PlayingEleven> preparePlayingElevens(int max) {
//        List<PlayingEleven> playingElevens = new ArrayList<>();
//        int matchId = 100;
//        int id = 10;
//        for (int i = 1; i <= max; i++) {
//            matchId += i;
//            PlayingEleven p1 = new PlayingEleven();
//            p1.setId(String.valueOf(id++));
//            p1.setMatchId(matchId);
//            p1.setSubmittedBy("user one");
//            p1.setPlayers(preparePlayers("one"));
//            p1.setScore(150 + i * 5);
//
//            PlayingEleven p2 = new PlayingEleven();
//            p2.setId(String.valueOf(id++));
//            p2.setMatchId(matchId);
//            p2.setSubmittedBy("user two");
//            p2.setPlayers(preparePlayers("two"));
//            p2.setScore(125 + i * 5);
//
//            PlayingEleven p3 = new PlayingEleven();
//            p3.setId(String.valueOf(id++));
//            p3.setMatchId(matchId);
//            p3.setSubmittedBy("user three");
//            p3.setPlayers(preparePlayers("three"));
//            p3.setScore(110 + i * 5);
//
//            PlayingEleven p4 = new PlayingEleven();
//            p4.setId(String.valueOf(id++));
//            p4.setMatchId(matchId);
//            p4.setSubmittedBy("user four");
//            p4.setPlayers(preparePlayers("four"));
//            p4.setWinner("IND");
//            p4.setScore(175 + i * 5);
//
//            playingElevens.add(p1);
//            playingElevens.add(p2);
//            playingElevens.add(p3);
//            playingElevens.add(p4);
//        }
//        return playingElevens;
//    }
//
//    private List<Player> preparePlayers(String name) {
//        List<Player> players = new ArrayList<>();
//        Player p1 = new Player();
//        p1.setId(1);
//        p1.setName("kohli");
//        p1.setType(PlayerType.BATTER);
//
//        Player p2 = new Player();
//        p2.setId(2);
//        p2.setName("Rohit");
//        p2.setType(PlayerType.BATTER);
//
//        Player p3 = new Player();
//        p3.setId(3);
//        p3.setName("Smith");
//        p3.setType(PlayerType.BATTER);
//
//
//        Player p4 = new Player();
//        p4.setId(4);
//        p4.setName("Maxwell");
//        p4.setType(PlayerType.ALL_ROUNDER);
//
//
//        if (name.contains("one")) {
//            players.add(p1);
//            players.add(p2);
//        } else if (name.contains("two")) {
//            players.add(p3);
//            players.add(p4);
//        } else if (name.contains("three")) {
//            players.add(p1);
//            players.add(p3);
//        } else {
//            players.add(p2);
//            players.add(p4);
//        }
//        return players;
//    }
//}