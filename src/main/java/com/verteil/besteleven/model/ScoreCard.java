package com.verteil.besteleven.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScoreCard {

    private Integer id;
    private Integer playerId;
    private Integer matchId;
    private int runs;
    private int wickets;
    private int score;
    private boolean manOfTheMatch;
}
