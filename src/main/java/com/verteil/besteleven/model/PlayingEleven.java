package com.verteil.besteleven.model;

import java.time.LocalDate;
import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"matchId","submittedBy"})
public class PlayingEleven {
    @Id
    private String id;
    private Integer matchId;
    private List<Player> players;
    private String winner;
    private String submittedBy;
    private LocalDate submittedDate;
    private int manOfTheMatchSelected;
    private int score;
}
