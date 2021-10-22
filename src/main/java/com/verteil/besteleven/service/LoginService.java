package com.verteil.besteleven.service;

import com.verteil.besteleven.model.User;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {

    String userLogin(User user, HttpServletRequest request);
}
