package me.khun.smartrestaurant.fx.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import me.khun.smartrestaurant.application.ApplicationProperties;
import me.khun.smartrestaurant.application.exception.DuplicateEntryInsertionException;
import me.khun.smartrestaurant.application.exception.InvalidFieldException;
import me.khun.smartrestaurant.auth.AuthManager;
import me.khun.smartrestaurant.bo.BoType;
import me.khun.smartrestaurant.bo.custom.MealCategoryBo;
import me.khun.smartrestaurant.bo.custom.impl.BoFactory;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;
import me.khun.smartrestaurant.dto.custom.UserDto;
import me.khun.smartrestaurant.entity.MealCategory;
import me.khun.smartrestaurant.entity.validator.MealCategoryValidator;
import me.khun.smartrestaurant.fx.custom.FloatingMessageView;
import me.khun.smartrestaurant.fx.custom.ImageFileChooser;
import me.khun.smartrestaurant.fx.custom.LayoutPosition;
import me.khun.smartrestaurant.fx.custom.MealCategoryCard;
import me.khun.smartrestaurant.fx.custom.inputadapter.SimpleNameInputAdapter;
import me.khun.smartrestaurant.fx.custom.inputadapter.StringInputAdapter;
import me.khun.smartrestaurant.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import static me.khun.smartrestaurant.application.ApplicationStrings.getString;

public class MealCategoryPageController implements Initializable {

    @FXML
    private HBox actionBar;

    @FXML
    private ComboBox<MealCategory.MealCategoryStatus> mealCategoryStatusFilter;

    @FXML
    private Button addCategoryButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private FlowPane flowPane;

    @FXML
    private VBox mealCategoryFormView;

    @FXML
    private ImageView mealCategoryImageView;

    @FXML
    private TextField mealCategoryNameInput;

    @FXML
    private TextField searchInput;

    @FXML
    private TextField mealCategoryShortNameInput;

    @FXML
    private ComboBox<MealCategory.MealCategoryStatus> mealCategoryStatusSelector;

    @FXML
    private Label nameInputMessageLabel;

    @FXML
    private Label shortNameInputMessageLabel;

    @FXML
    private Label statusSelectorMessageLabel;

    @FXML
    private Label imageViewMessageLabel;

    @FXML
    private Label formMessageLabel;

    @FXML
    private Button removeImageButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button selectImageButton;

    @FXML
    private SplitPane splitPane;

    @FXML
    private ScrollPane formScrollPane;

    @FXML
    private Label formTitleLabel;

    private StringInputAdapter nameInputAdapter;

    private StringInputAdapter shortNameInputAdapter;

    private MealCategoryBo mealCategoryBo;

    private File selectedImageFile;

    private MealCategoryDto currentMealCategory;

    private Stage currentStage;

    private boolean isFormUpdated;

    private static final int CARD_CLICK_COUNT = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Platform.runLater(()-> currentStage = (Stage) searchInput.getScene().getWindow());

        mealCategoryBo = (MealCategoryBo) BoFactory.getInstance().getBo(BoType.MEAL_CATEGORY);

        initSearchInput();
        initStatusFilter();
        initNameInput();
        initShortNameInput();
        initStatusSelector();
        initImageView();

        closeMealCategoryForm();
        loadAllMealCategories();

    }

    private void initSearchInput() {
        searchInput.setPromptText(getString("search"));
        searchInput.textProperty().addListener((l, o, n) -> {
            if (!StringUtil.isNullOrEmpty(o) && StringUtil.isNullOrEmpty(n))
                loadAllMealCategories();

        });
    }

    private void initStatusFilter() {
        mealCategoryStatusFilter.getItems().addAll(MealCategory.MealCategoryStatus.values());
        mealCategoryStatusFilter.getSelectionModel().selectedItemProperty().addListener((l, o, n) -> loadAllMealCategories());
        mealCategoryStatusFilter.getSelectionModel().select(MealCategory.MealCategoryStatus.ALL);
    }

    private void initNameInput() {
        nameInputAdapter = new SimpleNameInputAdapter(mealCategoryNameInput);
        mealCategoryNameInput.textProperty().addListener((l, o, n) -> {
            clearInputMessageLabel(mealCategoryNameInput);
            onFormUpdated();
        });
    }

    private void initShortNameInput() {
        shortNameInputAdapter = new SimpleNameInputAdapter(mealCategoryShortNameInput);
        mealCategoryShortNameInput.textProperty().addListener((l, o, n) -> {
            clearInputMessageLabel(mealCategoryShortNameInput);
            onFormUpdated();
        });
    }

    private void initStatusSelector() {
        mealCategoryStatusSelector.getItems().addAll(
                MealCategory.MealCategoryStatus.ACTIVE,
                MealCategory.MealCategoryStatus.INACTIVE
        );
        mealCategoryStatusSelector.valueProperty().addListener((l, o, n) -> {
            clearInputMessageLabel(mealCategoryStatusSelector);
            onFormUpdated();
        });
    }

    private void initImageView() {
        mealCategoryImageView.imageProperty().addListener((observable, oldValue, newValue) -> {
            clearInputMessageLabel(mealCategoryImageView);
            onFormUpdated();

        });
    }

    private void onFormUpdated() {
        var item = getItemFromForm();

        isFormUpdated = !Objects.equals(StringUtil.normalizeNotNullTrim(currentMealCategory.getName()), StringUtil.normalizeNotNullTrim(item.getName()))
                        || !Objects.equals(StringUtil.normalizeNotNullTrim(currentMealCategory.getShortName()), StringUtil.normalizeNotNullTrim(item.getShortName()))
                        || currentMealCategory.getStatus() != item.getStatus()
                        || !Objects.equals(StringUtil.normalizeNotNullTrim(currentMealCategory.getImagePath()), StringUtil.normalizeNotNullTrim(item.getImagePath()));

        removeImageButton.setDisable(null == selectedImageFile || !selectedImageFile.exists());
        saveButton.setDisable(!isFormUpdated);
    }

    private MealCategoryCard createMealCategoryCard(MealCategoryDto dto) {
        var card = new MealCategoryCard(dto);
        card.setOnMouseClicked(e -> showMealCategoryDetail(dto), CARD_CLICK_COUNT);
        return card;
    }



    private void showMealCategoryDetail(MealCategoryDto dto) {

        if (Objects.equals(currentMealCategory, dto)) return;
        if (confirmToCloseForm()) {

            showMealCategoryForm();
            clearAllMessageLabels();
            setFormData(dto);

            isFormUpdated = false;
            saveButton.setDisable(true);
        }

    }

    private boolean confirmToCloseForm() {
        if (!isFormUpdated) {
            return true;
        }

        AtomicBoolean confirm = new AtomicBoolean(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(getString("this.item.have.not.been.saved"));
        alert.setContentText(getString("are.you.sure.to.close"));
        alert.showAndWait().ifPresent(
                b->{
                    if (b == ButtonType.OK) {
                        confirm.set(true);
                    }
                }
        );

        return confirm.get();
    }

    private void setFormData(MealCategoryDto dto) {

        if (null == dto.getId()) {
            formTitleLabel.setText(getString("add.new.category"));
            deleteButton.setDisable(true);
        }else {
            formTitleLabel.setText(getString("edit.category"));
            deleteButton.setDisable(false);
        }

        this.currentMealCategory = dto;

        mealCategoryNameInput.setText(StringUtil.normalizeNotNullTrim(this.currentMealCategory.getName()));
        mealCategoryShortNameInput.setText(StringUtil.normalizeNotNullTrim(this.currentMealCategory.getShortName()));
        mealCategoryStatusSelector.setValue(this.currentMealCategory.getStatus());

        selectedImageFile = new File(StringUtil.normalizeNotNullTrim(this.currentMealCategory.getImagePath()));

        Image image = getImageOrDefaultIfNotExists(this.currentMealCategory.getImagePath());
        mealCategoryImageView.setImage(image);

        removeImageButton.setDisable(null == selectedImageFile || !selectedImageFile.exists());

    }

    private void setDefaultImageToImageView() {
        Image image = getImageOrDefaultIfNotExists(null);

        mealCategoryImageView.setImage(image);
    }


    private Image getImageOrDefaultIfNotExists(String path) {
        Image image;
        try {
            image = new Image(new FileInputStream(path));
        } catch (NullPointerException | FileNotFoundException e) {
            image = new Image(getClass().getResourceAsStream(ApplicationProperties.getDefaultNoImageResourcePath()));
        }
        return image;
    }

    private void clearInputMessageLabel(Node source) {
        if (source == mealCategoryNameInput)
            nameInputMessageLabel.setText("");
        else if (source == mealCategoryShortNameInput)
            shortNameInputMessageLabel.setText("");
        else if (source == mealCategoryStatusSelector)
            statusSelectorMessageLabel.setText("");
        else if (source == mealCategoryImageView)
            imageViewMessageLabel.setText("");
    }

    @FXML
    public void onSearch(ActionEvent event) {
        final var keyword = StringUtil.normalizeNotNullTrim(searchInput.getText());
        if (keyword.isBlank()) return;
        List<MealCategoryDto> list = mealCategoryBo.findByKeyword(keyword);
        loadMealCategories(list);
    }


    private void loadAllMealCategories() {
        List<MealCategoryDto> list = mealCategoryBo.findAll();
        loadMealCategories(list);
    }


    private void loadMealCategories(List<MealCategoryDto> list) {
        flowPane.getChildren().clear();

        list.stream().
                filter(i -> {
                    final var status = mealCategoryStatusFilter.getValue();
                    if (status != MealCategory.MealCategoryStatus.ALL)
                        return i.getStatus() == status;
                    return true;
                }).
                forEach(i->{
                    var card = createMealCategoryCard(i);
                    flowPane.getChildren().add(card);
                });
    }


    @FXML
    public void onAddButtonClicked(ActionEvent event) {
        showMealCategoryDetail(new MealCategoryDto());
    }

    private void showMealCategoryForm() {
        if (!splitPane.getItems().contains(mealCategoryFormView))
            splitPane.getItems().add(mealCategoryFormView);

        formScrollPane.setVvalue(0);
    }

    @FXML
    public void onCloseButtonClicked(ActionEvent event) {

        if (confirmToCloseForm())
            closeMealCategoryForm();
    }

    private void closeMealCategoryForm() {
        splitPane.getItems().remove(mealCategoryFormView);
        currentMealCategory = null;
        isFormUpdated = false;
    }

    @FXML
    public void onDeleteButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure to delete this item?");
        alert.showAndWait().ifPresent(b->{
            if (b == ButtonType.OK) {
                deleteMealCategory(currentMealCategory);
            }
        });
    }

    public void deleteMealCategory(MealCategoryDto item) {
        boolean deleted = mealCategoryBo.delete(item);


        if (deleted) {
            var popup = new FloatingMessageView(getString("item.is.successfully.deleted"));
            popup.show(currentStage, Pos.TOP_LEFT);
            loadAllMealCategories();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(getString("could.not.delete.this.item"));
            alert.showAndWait();
        }

        if (deleted)
            closeMealCategoryForm();
    }


    @FXML
    public void onSelectMealCategoryImage(ActionEvent event) {

        var fileChooser = new ImageFileChooser(getString("select.image.for.meal.category"));
        File file = fileChooser.showOpenDialog(currentStage);

        if (null == file) return;

        selectedImageFile = file;

        try {
            mealCategoryImageView.setImage(new Image(new FileInputStream(selectedImageFile)));
        } catch (FileNotFoundException e) {
            setDefaultImageToImageView();
            selectedImageFile = null;
        }
    }

    @FXML
    public void onRemoveMealCategoryImage(ActionEvent event) {

        if (selectedImageFile != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(getString("are.you.sure.to.remove.this.image"));
            alert.showAndWait().ifPresent(
                    b->{
                        if (b == ButtonType.OK) {
                            selectedImageFile = null;
                            setDefaultImageToImageView();
                        }
                    }
            );
        }
    }

    @FXML
    public void onSaveButtonClicked(ActionEvent event) {

        try {
            mealCategoryBo.saveOrUpdate(getItemFromForm());
            var popup = new FloatingMessageView(getString("item.is.successfully.saved"));
            popup.show(currentStage, Pos.TOP_LEFT);
            loadAllMealCategories();
            closeMealCategoryForm();
        } catch (InvalidFieldException e) {
            handleInvalidFieldException(e);
        } catch (DuplicateEntryInsertionException e) {
            formMessageLabel.setText("Duplicate item exists for " + e.getEntryValue());
        }
    }


    private MealCategoryDto getItemFromForm() {

        final var name = StringUtil.normalizeNotNullTrim(nameInputAdapter.getValue());
        final var shortName = StringUtil.normalizeNotNullTrim(shortNameInputAdapter.getValue());
        final var status = mealCategoryStatusSelector.getValue();
        final var imagePath = getSelectedImagePath();

        var dto = new MealCategoryDto();
        dto.setId(currentMealCategory.getId());
        dto.setName(name);
        dto.setShortName(shortName);
        dto.setStatus(status);
        dto.setImagePath(imagePath);
        if (null == dto.getAddedBy()) {
            UserDto currentUser = new UserDto(AuthManager.getInstance().getAuthenticatedUser());
            dto.setAddedBy(currentUser);
        }

        return dto;
    }



    private void handleInvalidFieldException(InvalidFieldException e) {

        final var field = e.getFieldName();

        switch (field) {
            case MealCategoryValidator.NAME_FIELD ->
                    nameInputMessageLabel.setText(e.getMessage());
            case MealCategoryValidator.SHORT_NAME_FIELD ->
                    shortNameInputMessageLabel.setText(e.getMessage());
            case MealCategoryValidator.STATUS_FIELD ->
                    statusSelectorMessageLabel.setText(e.getMessage());
            case MealCategoryValidator.IMAGE_PATH_FIELD -> {}

        }

    }



    private String getSelectedImagePath() {
        if (null == selectedImageFile)
            return null;
        else
            return selectedImageFile.exists() ? selectedImageFile.getAbsolutePath() : null;
    }



    private void clearAllMessageLabels() {
        nameInputMessageLabel.setText("");
        shortNameInputMessageLabel.setText("");
        statusSelectorMessageLabel.setText("");
        imageViewMessageLabel.setText("");
        formMessageLabel.setText("");
    }

}
