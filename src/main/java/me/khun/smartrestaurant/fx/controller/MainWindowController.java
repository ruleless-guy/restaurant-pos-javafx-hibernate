package me.khun.smartrestaurant.fx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import me.khun.smartrestaurant.auth.AuthManager;
import me.khun.smartrestaurant.auth.Permission;
import me.khun.smartrestaurant.application.ApplicationStrings;
import me.khun.smartrestaurant.fx.custom.FloatingMessageView;
import me.khun.smartrestaurant.util.FxViewUtil;

import java.net.URL;
import java.util.ResourceBundle;

import static me.khun.smartrestaurant.application.ApplicationStrings.getString;

public class MainWindowController implements Initializable {

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupMealCategoryPage();
    }

    private void setupMealCategoryPage() {

        addTab("Dash Board", "/view/dash_board.fxml", Permission.VIEW_DASH_BOARD);
        addTab(getString("tab.category.management"), "/view/meal_category_page.fxml", Permission.MANAGE_MEAL_CATEGORY);
        addTab(getString("tab.menu.management"), "/view/meal_page.fxml", Permission.MANAGE_MEAL);
        addTab(getString("tab.table.management"), "/view/table_page.fxml", Permission.MANAGE_RESTAURANT_TABLE);
    }

    private void addTab(String tabName, String resourcePath, Permission permission) {

        AuthManager authManager = AuthManager.getInstance();
        if (!authManager.isAuthorizedForAll(permission)) return;

        Parent view = FxViewUtil.loadView(resourcePath);

        Tab tab = new Tab(tabName);
        tab.setClosable(false);
        tab.setContent(view);

        tabPane.getTabs().add(tab);
    }


}
