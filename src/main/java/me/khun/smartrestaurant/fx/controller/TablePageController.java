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
import me.khun.smartrestaurant.bo.custom.RestaurantTableBo;
import me.khun.smartrestaurant.bo.custom.impl.BoFactory;
import me.khun.smartrestaurant.dto.custom.RestaurantTableDto;
import me.khun.smartrestaurant.entity.RestaurantTable;
import me.khun.smartrestaurant.fx.custom.FloatingMessageView;
import me.khun.smartrestaurant.fx.custom.ImageFileChooser;
import me.khun.smartrestaurant.fx.custom.RestaurantTableCard;
import me.khun.smartrestaurant.fx.custom.inputadapter.IntegerInputAdapter;
import me.khun.smartrestaurant.fx.custom.inputadapter.NumberInputAdapter;
import me.khun.smartrestaurant.fx.custom.inputadapter.SimpleNameInputAdapter;
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
import static me.khun.smartrestaurant.entity.validator.RestaurantTableValidator.*;

public class TablePageController implements Initializable {


    @FXML
    private HBox actionBar;

    @FXML
    private Button addMealButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private FlowPane flowPane;

    @FXML
    private ScrollPane formScrollPane;

    @FXML
    private Label imageViewMessageLabel;

    @FXML
    private VBox tableFormView;

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
    private Label seatCountInputMessageLabel;

    @FXML
    private Button selectImageButton;

    @FXML
    private SplitPane splitPane;

    @FXML
    private Label statusSelectorMessageLabel;

    @FXML
    private ComboBox<RestaurantTable.TableStatus> statusFilter;

    @FXML
    private ComboBox<RestaurantTable.TableStatus> statusSelector;

    @FXML
    private Label formTitleLabel;

    @FXML
    private TextField seatCountInput;

    private static final int CARD_CLICK_COUNT = 1;

    private RestaurantTableBo restaurantTableBo;

    private boolean isFormUpdated;

    private RestaurantTableDto currentTable;

    private SimpleNameInputAdapter nameInputAdapter;

    private NumberInputAdapter seatCountInputAdapter;

    private Stage currentStage;

    private File selectedImageFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        restaurantTableBo = (RestaurantTableBo) BoFactory.getInstance().getBo(BoType.RESTAURANT_TABLE);

        Platform.runLater(()-> currentStage = (Stage) searchInput.getScene().getWindow());

        initSearchInput();
        initStatusFilter();
        initNameInput();
        initSeatCountInput();
        initStatusSelector();
        initImageView();

        closeRestaurantTableForm();
        loadAllRestaurantTables();
    }

    private void initSearchInput() {
        searchInput.setPromptText(getString("search"));
        searchInput.textProperty().addListener((l, o, n) -> {
            if (!StringUtil.isNullOrBlank(o) && StringUtil.isNullOrEmpty(n))
                loadAllRestaurantTables();

        });
    }

    private void initStatusFilter() {
        statusFilter.getItems().addAll(RestaurantTable.TableStatus.values());
        statusFilter.getSelectionModel().selectedItemProperty().addListener((l, o, n) -> loadAllRestaurantTables());
        statusFilter.getSelectionModel().select(RestaurantTable.TableStatus.ALL);
    }

    private void initNameInput() {
        nameInputAdapter = new SimpleNameInputAdapter(nameInput);
        nameInput.textProperty().addListener((l, o, n) -> {
            clearInputMessageLabel(nameInput);
            onFormUpdated();
        });
    }

    private void initSeatCountInput() {
        seatCountInputAdapter = new IntegerInputAdapter(seatCountInput);
        seatCountInput.textProperty().addListener((l, o, n) -> {
            clearInputMessageLabel(seatCountInput);
            onFormUpdated();
        });
    }

    private void initStatusSelector() {
        statusSelector.getItems().addAll(RestaurantTable.TableStatus.AVAILABLE, RestaurantTable.TableStatus.REMOVED);
        statusSelector.valueProperty().addListener((l, o, n) ->{
            clearInputMessageLabel(statusSelector);
            onFormUpdated();
        });
    }

    private void initImageView() {
        mealImageView.imageProperty().addListener((observable, oldValue, newValue) -> {
            clearInputMessageLabel(mealImageView);
            onFormUpdated();
        });
    }

    private void onFormUpdated() {
        var item = getItemFromForm();

        isFormUpdated = !Objects.equals(StringUtil.normalizeNotNullTrim(currentTable.getName()), StringUtil.normalizeNotNullTrim(item.getName()))
                        || !Objects.equals(currentTable.getSeatCount(), item.getSeatCount())
                        || currentTable.getStatus() != item.getStatus()
                        || !Objects.equals(StringUtil.normalizeNotNullTrim(currentTable.getImagePath()), StringUtil.normalizeNotNullTrim(item.getImagePath()));


        removeImageButton.setDisable(null == selectedImageFile || !selectedImageFile.exists());
        saveButton.setDisable(!isFormUpdated);
    }



    private void loadRestaurantTables(List<RestaurantTableDto> list) {
        flowPane.getChildren().clear();
        list.stream()
                .filter(i-> {
                    final var status = statusFilter.getValue();
                    if (status != RestaurantTable.TableStatus.ALL) {
                        return i.getStatus() == status;
                    }
                    return true;
                })
                .forEach(i->{
                    flowPane.getChildren().add(createNewRestaurantTableCard(i));
                });

    }

    private void loadAllRestaurantTables() {
        List<RestaurantTableDto> list = restaurantTableBo.findAll();

        loadRestaurantTables(list);
    }

    private void showRestaurantTableDetail(RestaurantTableDto dto) {
        if (Objects.equals(currentTable, dto)) return;
        if (confirmToCloseForm()) {
            showRestaurantTableForm();
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

    private void setFormData(RestaurantTableDto dto) {

        if (null == dto.getId()) {
            formTitleLabel.setText(getString("add.new.table"));
            deleteButton.setDisable(true);
        }else {
            formTitleLabel.setText(getString("edit.table"));
            deleteButton.setDisable(false);
        }

        this.currentTable = dto;

        nameInputAdapter.setValue(StringUtil.normalizeNotNullTrim(this.currentTable.getName()));
        seatCountInputAdapter.setValue(this.currentTable.getSeatCount());
        statusSelector.setValue(this.currentTable.getStatus());

        selectedImageFile = new File(StringUtil.normalizeNotNullTrim(this.currentTable.getImagePath()));

        mealImageView.setImage(getImageOrDefaultIfNotExists(this.currentTable.getImagePath()));

        removeImageButton.setDisable(null == selectedImageFile || !selectedImageFile.exists());
    }

    @FXML
    public void onAddButtonClicked(ActionEvent event) {
        showRestaurantTableDetail(new RestaurantTableDto());
    }

    @FXML
    public void onCloseButtonClicked(ActionEvent event) {
        if (confirmToCloseForm())
            closeRestaurantTableForm();
    }

    @FXML
    public void onDeleteButtonClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure to delete this item?");
        alert.showAndWait().ifPresent(b->{
            if (b == ButtonType.OK) {
                deleteRestaurantTable(currentTable);
            }
        });
    }

    private void deleteRestaurantTable(RestaurantTableDto item) {
        boolean deleted = restaurantTableBo.delete(item);

        if (deleted) {
            var popup = new FloatingMessageView(getString("item.is.successfully.deleted"));
            popup.show(currentStage);
            loadAllRestaurantTables();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not delete this item.");
            alert.showAndWait();
        }
        if (deleted)
            closeRestaurantTableForm();
    }

    @FXML
    public void onRemoveRestaurantTableImage(ActionEvent event) {
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
            restaurantTableBo.saveOrUpdate(getItemFromForm());
            var popup = new FloatingMessageView(getString("item.is.successfully.saved"));
            popup.show(currentStage);
            loadAllRestaurantTables();
            closeRestaurantTableForm();
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
            case SEAT_COUNT_FIELD -> seatCountInputMessageLabel.setText(message);
            case STATUS_FIELD -> statusSelectorMessageLabel.setText(message);
            case IMAGE_PATH_FIELD -> imageViewMessageLabel.setText(message);
        }
    }

    @FXML
    public void onSearch(ActionEvent event) {
        final var keyword = StringUtil.normalizeNotNullTrim(searchInput.getText());
        if (keyword.isBlank()) return;
        List<RestaurantTableDto> list = restaurantTableBo.findByKeyword(keyword);
        loadRestaurantTables(list);
    }

    @FXML
    public void onSelectRestaurantTableImage(ActionEvent event) {
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

    private RestaurantTableDto getItemFromForm() {
        final var name = StringUtil.normalizeNotNullTrim(nameInputAdapter.getValue());
        final var seatCount = null == seatCountInputAdapter.getValue() ? null : seatCountInputAdapter.getValue().intValue();
        final var status = statusSelector.getValue();
        final var imagePath = getSelectedImagePath();

        var dto = new RestaurantTableDto();
        dto.setId(currentTable.getId());
        dto.setName(name);
        dto.setSeatCount(seatCount);
        dto.setStatus(status);
        dto.setImagePath(imagePath);

        return dto;
    }

    private String getSelectedImagePath() {
        if (null == selectedImageFile)
            return null;
        else
            return selectedImageFile.exists() ? selectedImageFile.getAbsolutePath() : null;
    }

    private void clearAllMessageLabels() {
        nameInputMessageLabel.setText("");
        seatCountInputMessageLabel.setText("");
        statusSelectorMessageLabel.setText("");
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

    private void setDefaultImageToImageView() {
        Image image = getImageOrDefaultIfNotExists(null);
        mealImageView.setImage(image);
    }

    private void showRestaurantTableForm() {
        if (!splitPane.getItems().contains(tableFormView))
            splitPane.getItems().add(tableFormView);
        formScrollPane.setVvalue(0);
    }

    private void closeRestaurantTableForm() {

        splitPane.getItems().remove(tableFormView);
        currentTable = null;
        isFormUpdated = false;
    }

    private RestaurantTableCard createNewRestaurantTableCard(RestaurantTableDto table) {
        RestaurantTableCard card = new RestaurantTableCard(table);
        card.setOnMouseClicked(e-> showRestaurantTableDetail(table), CARD_CLICK_COUNT);
        return card;
    }

    private void clearInputMessageLabel(Node source) {
        if (source == nameInput)
            nameInputMessageLabel.setText("");
        else if (source == seatCountInput)
            seatCountInputMessageLabel.setText("");
        else if (source == statusSelector)
            statusSelectorMessageLabel.setText("");
        else if (source == mealImageView)
            imageViewMessageLabel.setText("");
    }

}
