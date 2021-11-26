package me.khun.smartrestaurant.fx.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import me.khun.smartrestaurant.application.ApplicationProperties;
import me.khun.smartrestaurant.application.SmartRestaurantApplication;
import me.khun.smartrestaurant.application.exception.DuplicateEntryInsertionException;
import me.khun.smartrestaurant.application.exception.InvalidFieldException;
import me.khun.smartrestaurant.bo.BoType;
import me.khun.smartrestaurant.bo.custom.MealBo;
import me.khun.smartrestaurant.bo.custom.MealCategoryBo;
import me.khun.smartrestaurant.bo.custom.impl.BoFactory;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;
import me.khun.smartrestaurant.dto.custom.MealDto;
import me.khun.smartrestaurant.entity.Meal;
import me.khun.smartrestaurant.entity.MealCategory;
import me.khun.smartrestaurant.fx.custom.FloatingMessageView;
import me.khun.smartrestaurant.fx.custom.ImageFileChooser;
import me.khun.smartrestaurant.fx.custom.MealCard;
import me.khun.smartrestaurant.fx.custom.cellconverter.MealCatgoryNameConverter;
import me.khun.smartrestaurant.fx.custom.cellfactory.MealCategoryComboBoxCellFactory;
import me.khun.smartrestaurant.fx.custom.inputadapter.FloatingNumberInputAdapter;
import me.khun.smartrestaurant.fx.custom.inputadapter.NumberInputAdapter;
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
import java.util.stream.Collectors;

import static me.khun.smartrestaurant.entity.validator.MealValidator.*;
import static me.khun.smartrestaurant.application.ApplicationStrings.getString;

public class MealPageController implements Initializable {

    @FXML
    private HBox actionBar;

    @FXML
    private Button addMealButton;

    @FXML
    private Label categorySelectorMessageLabel;

    @FXML
    private ComboBox<MealCategoryDto> categorySelector;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private FlowPane flowPane;

    @FXML
    private Label imageViewMessageLabel;

    @FXML
    private VBox mealFormView;

    @FXML
    private ImageView mealImageView;

    @FXML
    private Label formMessageLabel;

    @FXML
    private Label nameInputMessageLabel;

    @FXML
    private TextField nameInput;

    @FXML
    private Button removeImageButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField searchInput;

    @FXML
    private Button selectImageButton;

    @FXML
    private Label shortNameInputMessageLabel;

    @FXML
    private TextField shortNameInput;

    @FXML
    private Label sizeSelectorMessageLabel;

    @FXML
    private ComboBox<Meal.MealSize> sizeSelector;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Label statusSelectorMessageLabel;

    @FXML
    private ComboBox<Meal.MealStatus> statusFilter;

    @FXML
    private ComboBox<Meal.MealStatus> statusSelector;

    @FXML
    private Label formTitleLabel;

    @FXML
    private TextField unitPriceInput;

    @FXML
    private Label unitPriceInputMessageLabel;

    @FXML
    private ScrollPane formScrollPane;

    private boolean isFormUpdated;

    private static final int CARD_CLICK_COUNT = 1;

    private MealDto currentMeal;

    private File selectedImageFile;

    private Stage currentStage;

    private StringInputAdapter nameInputAdapter;
    private StringInputAdapter shortNameInputAdapter;
    private NumberInputAdapter unitPriceInputAdapter;

    private MealBo mealBo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mealBo = (MealBo) BoFactory.getInstance().getBo(BoType.MEAL);

        Platform.runLater(()-> currentStage = (Stage) searchInput.getScene().getWindow());


        initSearchInput();

        initStatusFilter();

        initNameInput();

        initShortNameInput();

        initUnitPriceInput();

        initCategorySelector();

        initStatusSelector();

        initSizeSelector();

        initMealImageView();


        closeMealForm();
        loadAllMeals();
    }

    private void initSearchInput() {
        searchInput.setPromptText(getString("search"));
        searchInput.textProperty().addListener((l, o, n) -> {
            if (!StringUtil.isNullOrEmpty(o) && StringUtil.isNullOrEmpty(n))
                loadAllMeals();

        });
    }

    private void initStatusFilter() {
        statusFilter.getItems().addAll(Meal.MealStatus.values());
        statusFilter.getSelectionModel().selectedItemProperty().addListener((l, o, n) -> loadAllMeals());
        statusFilter.getSelectionModel().select(Meal.MealStatus.ALL);
    }

    private void initNameInput() {
        nameInputAdapter = new SimpleNameInputAdapter(nameInput);
        nameInput.textProperty().addListener((l, o, n) -> {
            clearInputMessageLabel(nameInput);
            onFormUpdated();
        });
    }

    private void initShortNameInput() {
        shortNameInputAdapter = new SimpleNameInputAdapter(shortNameInput);
        shortNameInput.textProperty().addListener((l, o, n) -> {
            clearInputMessageLabel(shortNameInput);
            onFormUpdated();
        });
    }

    private void initUnitPriceInput() {
        unitPriceInputAdapter = new FloatingNumberInputAdapter(unitPriceInput);
        unitPriceInput.textProperty().addListener((l, o, n) -> {
            clearInputMessageLabel(shortNameInput);
            onFormUpdated();
        });
    }

    private void initCategorySelector() {

        categorySelector.setCellFactory(new MealCategoryComboBoxCellFactory());
        categorySelector.setConverter(new MealCatgoryNameConverter());

        MealCategoryBo mealCategoryBo = (MealCategoryBo) BoFactory.getInstance().getBo(BoType.MEAL_CATEGORY);

        categorySelector.setOnMouseClicked(e->{
            categorySelector.getItems().clear();
            List<MealCategoryDto> mealCategoryDtoList = mealCategoryBo.findAll().stream().filter(i-> i.getStatus() == MealCategory.MealCategoryStatus.ACTIVE).collect(Collectors.toList());
            categorySelector.getItems().addAll(mealCategoryDtoList);
        });

        categorySelector.valueProperty().addListener((l, o, n)->{
            clearInputMessageLabel(categorySelector);
            onFormUpdated();
        });

    }

    private void initStatusSelector() {
        statusSelector.getItems().addAll(Meal.MealStatus.ACTIVE, Meal.MealStatus.INACTIVE);
        statusSelector.valueProperty().addListener((l, o, n) ->{
            clearInputMessageLabel(statusSelector);
            onFormUpdated();
        });
    }

    private void initSizeSelector() {
        sizeSelector.getItems().addAll(Meal.MealSize.SMALL, Meal.MealSize.MEDIUM, Meal.MealSize.LARGE);
        sizeSelector.valueProperty().addListener((l, o, n)->{
            clearInputMessageLabel(sizeSelector);
            onFormUpdated();
        });
    }

    private void initMealImageView() {
        mealImageView.imageProperty().addListener((observable, oldValue, newValue) -> {
            clearInputMessageLabel(mealImageView);
            onFormUpdated();
        });
    }


    private void onFormUpdated() {
        var item = getItemFromForm();

        isFormUpdated = !Objects.equals(StringUtil.normalizeNotNullTrim(currentMeal.getName()), StringUtil.normalizeNotNullTrim(item.getName()))
                        || !Objects.equals(StringUtil.normalizeNotNullTrim(currentMeal.getShortName()), StringUtil.normalizeNotNullTrim(item.getShortName()))
                        || !Objects.equals(currentMeal.getUnitPrice(), item.getUnitPrice())
                        || !Objects.equals(currentMeal.getCategory(), item.getCategory())
                        || currentMeal.getStatus() != item.getStatus()
                        || currentMeal.getSize() != item.getSize()
                        || !Objects.equals(StringUtil.normalizeNotNullTrim(currentMeal.getImagePath()), StringUtil.normalizeNotNullTrim(item.getImagePath()));

        removeImageButton.setDisable(null == selectedImageFile || !selectedImageFile.exists());
        saveButton.setDisable(!isFormUpdated);
    }


    private void loadAllMeals() {
        List<MealDto> list = mealBo.findAll();
        loadMeals(list);
    }

    private void loadMeals(List<MealDto> mealList) {
        flowPane.getChildren().clear();
        mealList.stream()
                .filter(i->{
                    final var status = statusFilter.getValue();
                    if (status != Meal.MealStatus.ALL) {
                        return i.getStatus() == status;
                    }
                    return true;
                })
                .forEach(i->{
                    MealCard card = createNewMealCard(i);
                    flowPane.getChildren().add(card);
                });
    }

    private void showMealDetail(MealDto dto) {
        if (Objects.equals(currentMeal, dto)) return;
        if (confirmToCloseForm()) {
            showMealForm();
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

    private void setFormData(MealDto dto) {

        if (null == dto.getId()) {
            formTitleLabel.setText(getString("add.new.menu"));
            deleteButton.setDisable(true);
        }else {
            formTitleLabel.setText(getString("edit.menu"));
            deleteButton.setDisable(false);
        }

        this.currentMeal = dto;

        nameInputAdapter.setValue(StringUtil.normalizeNotNullTrim(this.currentMeal.getName()));
        shortNameInputAdapter.setValue(StringUtil.normalizeNotNullTrim(this.currentMeal.getShortName()));
        unitPriceInputAdapter.setValue(this.currentMeal.getUnitPrice());
        categorySelector.setValue(this.currentMeal.getCategory());
        statusSelector.setValue(this.currentMeal.getStatus());
        sizeSelector.setValue(this.currentMeal.getSize());

        selectedImageFile = new File(StringUtil.normalizeNotNullTrim(this.currentMeal.getImagePath()));

        mealImageView.setImage(getImageOrDefaultIfNotExists(this.currentMeal.getImagePath()));

        removeImageButton.setDisable(null == selectedImageFile || !selectedImageFile.exists());
    }

    private void setDefaultImageToImageView() {
        Image image = getImageOrDefaultIfNotExists(null);
        mealImageView.setImage(image);
    }



    @FXML
    public void onAddButtonClicked(ActionEvent event) {
        showMealDetail(new MealDto());
    }

    @FXML
    public void onCloseButtonClicked(ActionEvent event) {
        if (confirmToCloseForm())
            closeMealForm();
    }

    @FXML
    public void onDeleteButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure to delete this item?");
        alert.showAndWait().ifPresent(b->{
            if (b == ButtonType.OK) {
                deleteMeal(currentMeal);
            }
        });
    }

    private void deleteMeal(MealDto item) {
        boolean deleted = mealBo.delete(item);

        if (deleted) {
            var popup = new FloatingMessageView(getString("item.is.successfully.deleted"));
            popup.show(currentStage);
            loadAllMeals();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not delete this item.");
            alert.showAndWait();
        }
        if (deleted)
            closeMealForm();
    }



    @FXML
    public void onSaveButtonClicked(ActionEvent event) {
        try {
            mealBo.saveOrUpdate(getItemFromForm());
            var popup = new FloatingMessageView(getString("item.is.successfully.saved"));
            popup.show(currentStage);
            loadAllMeals();
            closeMealForm();
        } catch (InvalidFieldException e) {
            handleInvalidFieldException(e);
        } catch (DuplicateEntryInsertionException e) {
            formMessageLabel.setText("Duplicate item exists for " + e.getEntryValue());
        }
    }

    private void handleInvalidFieldException(InvalidFieldException e) {
        final var field = e.getFieldName();
        final var message = e.getMessage();

        switch (field) {
            case NAME_FIELD -> nameInputMessageLabel.setText(message);
            case SHORT_NAME_FIELD -> shortNameInputMessageLabel.setText(message);
            case UNIT_PRICE_FIELD -> unitPriceInputMessageLabel.setText(message);
            case CATEGORY_FIELD -> categorySelectorMessageLabel.setText(message);
            case STATUS_FIELD -> statusSelectorMessageLabel.setText(message);
            case SIZE_FILED -> sizeSelectorMessageLabel.setText(message);
            case IMAGE_PATH_FIELD -> imageViewMessageLabel.setText(message);
        }
    }

    private MealDto getItemFromForm() {

        final var name = StringUtil.normalizeNotNullTrim(nameInputAdapter.getValue());
        final var shortName = StringUtil.normalizeNotNullTrim(shortNameInputAdapter.getValue());
        final var unitPrice = null == unitPriceInputAdapter.getValue() ? null : unitPriceInputAdapter.getValue().doubleValue();
        final var category = categorySelector.getValue();
        final var status = statusSelector.getValue();
        final var size = sizeSelector.getValue();
        final var imagePath = getSelectedImagePath();


        var dto = new MealDto();
        dto.setId(currentMeal.getId());
        dto.setName(name);
        dto.setShortName(shortName);
        dto.setUnitPrice(unitPrice);
        dto.setCategory(category);
        dto.setStatus(status);
        dto.setSize(size);
        dto.setImagePath(imagePath);

        return dto;
    }


    @FXML
    public void onSearch(ActionEvent event) {
        final var keyword = StringUtil.normalizeNotNullTrim(searchInput.getText());
        if (keyword.isBlank()) return;
        List<MealDto> list = mealBo.findByKeyword(keyword);
        loadMeals(list);
    }

    @FXML
    public void onSelectMealCategoryImage(ActionEvent event) {
        var fileChooser = new ImageFileChooser(getString("select.image.for.meal.category"));
        File file = fileChooser.showOpenDialog(currentStage);

        if (null == file) return;

        selectedImageFile = file;

        try {
            mealImageView.setImage(new Image(new FileInputStream(selectedImageFile)));
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

    private String getSelectedImagePath() {
        if (null == selectedImageFile)
            return null;
        else
            return selectedImageFile.exists() ? selectedImageFile.getAbsolutePath() : null;
    }


    private void clearAllMessageLabels() {
        nameInputMessageLabel.setText("");
        shortNameInputMessageLabel.setText("");
        unitPriceInputMessageLabel.setText("");
        categorySelectorMessageLabel.setText("");
        statusSelectorMessageLabel.setText("");
        sizeSelectorMessageLabel.setText("");
        imageViewMessageLabel.setText("");
        formMessageLabel.setText("");
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

    private void closeMealForm() {
        splitPane.getItems().remove(mealFormView);
        currentMeal = null;
        isFormUpdated = false;
    }

    private void showMealForm() {
        if (!splitPane.getItems().contains(mealFormView))
            splitPane.getItems().add(mealFormView);
        formScrollPane.setVvalue(0);
    }

    private MealCard createNewMealCard(MealDto mealDto) {
        var card = new MealCard(mealDto);
        card.setOnMouseClicked(e-> showMealDetail(mealDto), CARD_CLICK_COUNT);
        return card;
    }

    private void clearInputMessageLabel(Node source) {
        if (source == nameInput)
            nameInputMessageLabel.setText("");
        else if (source == shortNameInput)
            shortNameInputMessageLabel.setText("");
        else if (source == unitPriceInput)
            unitPriceInputMessageLabel.setText("");
        else if (source == categorySelector)
            categorySelectorMessageLabel.setText("");
        else if (source == sizeSelector)
            sizeSelectorMessageLabel.setText("");
        else if (source == statusSelector)
            statusSelectorMessageLabel.setText("");
        else if (source == mealImageView)
            imageViewMessageLabel.setText("");
    }

}
