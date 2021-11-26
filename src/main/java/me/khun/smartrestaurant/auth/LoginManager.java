package me.khun.smartrestaurant.auth;

import me.khun.smartrestaurant.entity.User;

import java.time.LocalDateTime;

public interface LoginManager {

    void login(String loginId, String password);

    User getLoggedInUser();

    LocalDateTime getLoggedInTime();
}
