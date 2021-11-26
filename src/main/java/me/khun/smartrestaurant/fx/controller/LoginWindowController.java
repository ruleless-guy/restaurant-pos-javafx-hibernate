package me.khun.smartrestaurant.fx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.khun.smartrestaurant.application.SmartRestaurantApplication;
import me.khun.smartrestaurant.application.exception.InvalidFieldException;
import me.khun.smartrestaurant.application.exception.LoginException;
import me.khun.smartrestaurant.auth.AuthManager;
import me.khun.smartrestaurant.fx.custom.inputadapter.SimpleLoginIdInputAdapter;
import me.khun.smartrestaurant.fx.custom.inputadapter.StringInputAdapter;
import me.khun.smartrestaurant.util.FxStageUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {

    @FXML
    private TextField loginIdInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label loginIdMessageLabel;

    @FXML
    private Label passwordMessageLabel;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        StringInputAdapter loginIdInputAdapter = new SimpleLoginIdInputAdapter(loginIdInput);

        loginIdInput.textProperty().addListener((l, o, n) -> {
            clearAllErrorMessageLabel();
        });

        passwordInput.textProperty().addListener((l, o, n) -> {
            clearAllErrorMessageLabel();
        });
    }

    private void clearAllErrorMessageLabel() {
        loginIdMessageLabel.setText("");
        passwordMessageLabel.setText("");
        errorLabel.setText("");
    }

    @FXML
    public void onActionClose(ActionEvent event) {
        closeStage(event);
    }

    private void closeStage(ActionEvent event) {
        FxStageUtil.stageFromEvent(event).close();
    }

    @FXML
    public void onActionLogin(ActionEvent event) {

        clearAllErrorMessageLabel();

        try {

            /*final var loginId = StringUtil.normalizeNotNullTrim(loginIdInput.getText());

            final var password = StringUtil.normalizeNotNullTrim(passwordInput.getText());

            final var emptyLoginIdErrorText = ApplicationStrings.getString("say.to.enter.login.id");
            final var emptyPasswordErrorText = ApplicationStrings.getString("say.to.enter.password");

            if (StringUtil.isNullOrBlank(loginId))
                throw new InvalidFieldException(emptyLoginIdErrorText, "login_id");
            if (StringUtil.isNullOrBlank(password))
                throw new InvalidFieldException(emptyPasswordErrorText, "password");
*/
            AuthManager authManager = AuthManager.getInstance();
            authManager.authenticate("kkk", "kkk");

            closeStage(event);
            SmartRestaurantApplication.showMainWindow();

        } catch (InvalidFieldException e) {

            handleInvalidInputException(e);

        } catch (LoginException e) {

            var cause = e.getCause();

            if (cause instanceof InvalidFieldException) {
                handleInvalidInputException((InvalidFieldException) cause);
            }else {
                errorLabel.setText(e.getMessage());
            }
        }
    }

    private void handleInvalidInputException(InvalidFieldException e) {
        final var field = e.getFieldName();
        final  var message = e.getMessage();

        switch (field) {
            case "login_id" -> loginIdMessageLabel.setText(message);
            case "password" -> passwordMessageLabel.setText(message);
        }
    }
}
