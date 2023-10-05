package com.verteil.besteleven.service.impl;

import com.verteil.besteleven.model.Match;
import com.verteil.besteleven.repository.MatchRepository;
import com.verteil.besteleven.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.verteil.besteleven.util.TimeConversionUtil.checkDatePassed;

@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public Match findById(int id) {
        return matchRepository.findById(id);
    }

    @Override
    public List<Match> findAll() {
        var matches = matchRepository.findAll();
        findMatchOver(matches);
        return matches;
    }

    @Override
    public List<Match> findByDate(LocalDate date) {
        return matchRepository.finByDate(date);
    }

    private void findMatchOver(List<Match> matches) {
        matches.forEach(m -> m.setMatchOver(checkDatePassed(m.getDate(), m.getTime())));
    }

}
