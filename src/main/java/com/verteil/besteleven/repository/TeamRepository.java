package com.verteil.besteleven.repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.verteil.besteleven.model.Player;
import com.verteil.besteleven.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Repository
public class TeamRepository {

    private List<Team> teams;

    @Value("${data.location}")
    private String dataLocation;

    @PostConstruct
    public void init() {
        try {
            teams = new ArrayList();
            //    Resource resource = new ClassPathResource("team.csv");
            log.info("Reading Team details from : {}", dataLocation);
            Resource resource = new FileSystemResource(dataLocation + "team.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            CsvToBean<Team> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Team.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            Iterator<Team> teamIterator = csvToBean.iterator();
            while (teamIterator.hasNext()) {
                teams.add(teamIterator.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Team findByCountry(String country) {
        return teams.stream()
                .filter(t -> t.getCountry().equalsIgnoreCase(country))
                .findFirst().get();
    }

    public List<Team> findAll() {
        return teams;
    }

}
