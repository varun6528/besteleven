package com.verteil.besteleven.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.verteil.besteleven.model.Match;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class MatchRepository {

    private List<Match> matches;

    @Value("${data.location}")
    private String dataLocation;

    @PostConstruct
    public void init() {
        {
            try {
                matches = new ArrayList<>();
                // Resource resource = new ClassPathResource("match.csv");
                log.info("Reading MatchSchedule from : {}", dataLocation);
                Resource resource = new FileSystemResource(dataLocation + "match.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

                CsvToBean<Match> csvToBean = new CsvToBeanBuilder(reader)
                        .withType(Match.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();

                Iterator<Match> matchIterator = csvToBean.iterator();
                while (matchIterator.hasNext()) {
                    Match match = matchIterator.next();
                    matches.add(match);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Match findById(int id) {
        return matches.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .get();
    }

    public List<Match> findAll() {
        return matches;
    }

    public List<Match> finByDate(LocalDate date) {
        return matches
                .stream()
                .filter(match -> match
                        .getDate()
                        .isEqual(date))
                .collect(Collectors.toList());
    }
}
