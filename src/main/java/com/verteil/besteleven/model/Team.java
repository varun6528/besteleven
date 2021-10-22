package com.verteil.besteleven.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    private int id;
    private String country;
    private Player captain;
    private Player viceCaptain;
    private List<Player> players;
}
