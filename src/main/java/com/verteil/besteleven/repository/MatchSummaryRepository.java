package com.verteil.besteleven.repository;

import com.verteil.besteleven.model.MatchSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchSummaryRepository extends MongoRepository<MatchSummary, Integer> {
    MatchSummary findByMatchId(Integer matchId);
}
