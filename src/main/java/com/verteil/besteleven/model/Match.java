package com.verteil.besteleven.model;

import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Match {
    private Integer id;
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate date;
    private String firstTeam;
    private String secondTeam;
    private MatchRound round;
    private String group;
    private String time;
    @CsvIgnore
    private boolean matchOver;
}
