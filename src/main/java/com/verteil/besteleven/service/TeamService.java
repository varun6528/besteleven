package com.verteil.besteleven.service;

import com.verteil.besteleven.model.Team;

public interface TeamService {

    Team findByCountry(String country);
}
