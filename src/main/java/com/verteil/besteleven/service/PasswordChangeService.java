package com.verteil.besteleven.service;

import com.verteil.besteleven.model.User;

public interface PasswordChangeService {

    boolean changePassword(User user, String oldPass, String newPass);
}
