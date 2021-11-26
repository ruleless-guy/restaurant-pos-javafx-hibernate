package me.khun.smartrestaurant.auth;

import lombok.Getter;
import me.khun.smartrestaurant.entity.User;

public class AuthManager {

    private static AuthManager instance;

    public static AuthManager getInstance() {
        if (null == instance) {
            synchronized (AuthManager.class) {
                if (null == instance) {
                    instance = new AuthManager();
                }

            }
        }
        return instance;
    }

    @Getter
    private final LoginManager loginManager;

    public AuthManager() {
        this.loginManager = LocalLoginManager.getInstance();
    }

    public void authenticate(String loginId, String password) {
        loginManager.login(loginId, password);
    }

    public User getAuthenticatedUser() {
        return loginManager.getLoggedInUser();
    }

    public boolean isAuthorizedForAll(Permission ... permissions) {
        return getAuthenticatedUser().hasAccessForAll(permissions);
    }
}
