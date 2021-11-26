package me.khun.smartrestaurant.auth;

import me.khun.smartrestaurant.application.exception.InvalidFieldException;
import me.khun.smartrestaurant.application.exception.LoginException;
import me.khun.smartrestaurant.bo.BoType;
import me.khun.smartrestaurant.bo.custom.UserBo;
import me.khun.smartrestaurant.bo.custom.impl.BoFactory;
import me.khun.smartrestaurant.entity.User;
import me.khun.smartrestaurant.application.ApplicationStrings;
import me.khun.smartrestaurant.util.StringValidator;

import java.time.LocalDateTime;

class LocalLoginManager implements LoginManager{

    private User loggedInUser;
    private LocalDateTime loggedInTime;
    private static LocalLoginManager INSTANCE;

    private LocalLoginManager() {}

    public static LocalLoginManager getInstance() {
        if (null == INSTANCE) {
            synchronized (LocalLoginManager.class) {
                if (null == INSTANCE) {
                    INSTANCE = new LocalLoginManager();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public User getLoggedInUser() {
        return null == loggedInUser ? null : loggedInUser.clone();
    }

    @Override
    public LocalDateTime getLoggedInTime() {
        return LocalDateTime.of(loggedInTime.toLocalDate(), loggedInTime.toLocalTime());
    }

    @Override
    public void login(String loginId, String password) throws IllegalArgumentException, LoginException {

        final var emptyLoginIdErrorText = ApplicationStrings.getString("say.to.enter.login.id");
        final var emptyPasswordErrorText = ApplicationStrings.getString("say.to.enter.password");



        StringValidator.notEmptyOrNotNull(loginId, emptyLoginIdErrorText);
        StringValidator.notEmptyOrNotNull(password, emptyPasswordErrorText);


        var userBo = (UserBo) BoFactory.getInstance().getBo(BoType.USER);

        var userDto = userBo.findByLoginIdAndPassword(loginId, password);

        if (null == userDto) {

            Throwable cause;

            if (!userBo.loginIdExists(loginId))
                cause = new InvalidFieldException(ApplicationStrings.getString("error.invalid.login.id"), "login_id");
            else
                cause = new InvalidFieldException(ApplicationStrings.getString("error.invalid.password"), "password");

            throw new LoginException(cause.getMessage(), cause);

        } else {

            if (!userDto.getStatus().equals(User.UserStatus.ACTIVE))
                throw new LoginException(ApplicationStrings.getString("error.no.access.to.login"));

            loggedInUser = userDto.getEntity();
            loggedInTime = LocalDateTime.now();
        }


    }

    public static void main(String[] args) {

    }
}
