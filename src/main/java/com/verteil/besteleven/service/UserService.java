package com.verteil.besteleven.service;

import com.verteil.besteleven.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User findUserScoreByMatch(int userId);

    Map<String, List<User>> findAllScoreByMatch();

    Map<Integer, User> findOverAllScoreOfUserByMatch(String userId);

    User findOverAllUserScore(User user);

    List<User> findOverAllScore();
}
