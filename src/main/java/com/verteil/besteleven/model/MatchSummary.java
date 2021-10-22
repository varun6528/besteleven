package com.verteil.besteleven.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchSummary {
    @Id
    private String id;
    private Integer matchId;
    private String winner;
    private Integer momId;
    private List<ScoreCard> scoreCards;
}
