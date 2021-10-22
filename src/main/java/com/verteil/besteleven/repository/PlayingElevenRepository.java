package com.verteil.besteleven.repository;

import com.verteil.besteleven.model.PlayingEleven;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayingElevenRepository extends MongoRepository<PlayingEleven, Integer> {

    List<PlayingEleven> findByMatchId(Integer matchId);

    List<PlayingEleven> findBySubmittedBy(String user);

    PlayingEleven findByMatchIdAndSubmittedBy(int matchId, String user);
}
