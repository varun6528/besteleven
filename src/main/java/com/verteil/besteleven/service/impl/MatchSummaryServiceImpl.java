package com.verteil.besteleven.service.impl;

import com.verteil.besteleven.model.MatchSummary;
import com.verteil.besteleven.model.ScoreCard;
import com.verteil.besteleven.repository.MatchRepository;
import com.verteil.besteleven.repository.MatchSummaryRepository;
import com.verteil.besteleven.service.MatchSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.verteil.besteleven.util.PointCriteria.RUN_POINT;
import static com.verteil.besteleven.util.PointCriteria.WICKET_POINT;

@Slf4j
@Service
public class MatchSummaryServiceImpl implements MatchSummaryService {

    private final MatchSummaryRepository matchSummaryRepository;
    private final MatchRepository matchRepository;

    @Autowired
    public MatchSummaryServiceImpl(MatchSummaryRepository matchSummaryRepository, MatchRepository matchRepository) {
        this.matchSummaryRepository = matchSummaryRepository;
        this.matchRepository = matchRepository;
    }

    @Override
    public void save(MatchSummary matchSummary) {
        matchSummary.getScoreCards().stream()
                .forEach(s -> s.setScore(calculateScore(s)));
        matchSummaryRepository.save(matchSummary);
    }

    @Override
    public MatchSummary findByMatchId(Integer matchId) {
        return matchSummaryRepository.findByMatchId(matchId);
    }

    private int calculateScore(ScoreCard scoreCard) {
        return scoreCard.getRuns() * RUN_POINT + scoreCard.getWickets() * WICKET_POINT;
    }
}
