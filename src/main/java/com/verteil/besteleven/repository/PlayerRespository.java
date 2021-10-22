package com.verteil.besteleven.repository;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.verteil.besteleven.model.Player;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class PlayerRespository {

    private List<Player> players;

    @Value("${data.location}")
    private String dataLocation;

    @PostConstruct
    public void init() {
        try {
            players = new ArrayList<>();
            //  Resource resource = new ClassPathResource("player.csv");
            log.info("Reading Player details from : {}", dataLocation);
            Resource resource = new FileSystemResource(dataLocation + "player.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            CsvToBean<Player> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Player.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .build();

            Iterator<Player> playerIterator = csvToBean.iterator();
            while (playerIterator.hasNext()) {
                players.add(playerIterator.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Player findById(int id) {
        return players.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .get();
    }

    public Player findByName(String name) {
        return players.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .get();
    }

    public List<Player> findByCountry(String country) {
        return players.stream()
                .filter(p -> p.getCountry().equalsIgnoreCase(country))
                .collect(Collectors.toList());
    }
}
