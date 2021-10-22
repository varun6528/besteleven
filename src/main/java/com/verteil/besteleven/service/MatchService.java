package com.verteil.besteleven.service;

import com.verteil.besteleven.model.Match;

import java.time.LocalDate;
import java.util.List;

public interface MatchService {

    Match findById(int id);

    List<Match> findAll();

    List<Match> findByDate(LocalDate date);
}
