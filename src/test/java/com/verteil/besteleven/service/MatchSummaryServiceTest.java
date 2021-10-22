package com.verteil.besteleven.service;

import com.verteil.besteleven.model.MatchSummary;
import com.verteil.besteleven.model.ScoreCard;
import com.verteil.besteleven.repository.MatchSummaryRepository;
import com.verteil.besteleven.service.impl.MatchSummaryServiceImpl;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

@RunWith(MockitoJUnitRunner.class)
@Ignore
public class MatchSummaryServiceTest {

    @InjectMocks
    private MatchSummaryServiceImpl matchSummaryService;

    @Mock
    private MatchSummaryRepository matchSummaryRepository;

    @Test
    public void saveTest() {
        MatchSummary matchSummary = prepareMatchSummary();
        //  verify(scoreCardRepository, times(1)).saveAll(scoreCards);
        matchSummaryService.save(matchSummary);
        assertThat(matchSummary.getScoreCards(), contains(
                hasProperty("score", is(120)),
                hasProperty("score", is(65)),
                hasProperty("score", is(45)),
                hasProperty("score", is(50))
        ));
    }

    private MatchSummary prepareMatchSummary() {

        List<ScoreCard> scoreCards = new ArrayList<>();
        ScoreCard s1 = new ScoreCard();
        s1.setId(1);
        s1.setMatchId(100);
        s1.setPlayerId(1);
        s1.setRuns(60);
        s1.setManOfTheMatch(true);

        ScoreCard s2 = new ScoreCard();
        s2.setId(2);
        s2.setMatchId(100);
        s2.setPlayerId(2);
        s2.setRuns(65);

        ScoreCard s3 = new ScoreCard();
        s3.setId(3);
        s3.setMatchId(100);
        s3.setPlayerId(3);
        s3.setRuns(45);

        ScoreCard s4 = new ScoreCard();
        s4.setId(4);
        s4.setMatchId(100);
        s4.setPlayerId(4);
        s4.setRuns(25);
        s4.setWickets(1);

        scoreCards.add(s1);
        scoreCards.add(s2);
        scoreCards.add(s3);
        scoreCards.add(s4);

        MatchSummary matchSummary = new MatchSummary();
        matchSummary.setMatchId(100);
        matchSummary.setScoreCards(scoreCards);

        return matchSummary;
    }

}
