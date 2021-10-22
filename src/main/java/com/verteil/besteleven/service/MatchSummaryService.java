package com.verteil.besteleven.service;

import com.verteil.besteleven.model.MatchSummary;

public interface MatchSummaryService {

    void save(MatchSummary matchSummaries);

    MatchSummary findByMatchId(Integer matchId);
}
