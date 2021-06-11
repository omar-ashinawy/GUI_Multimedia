package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;

public class CBIR {

    private static int start = 0;
    private static final int imagesPerView = 8;
    private static final String imagesPath = "D:\\Quds_photos\\";
    private static final FlowPane flowPane = new FlowPane();
    private static String searchImagePath = "";
    public static void display () throws FileNotFoundException {
        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setTitle("CBIR System");
        HBox hViewer = new HBox();
        HBox hControl = new HBox();
        VBox vControl = new VBox();
        VBox vBox = new VBox();
        ChoiceBox<String> searchOptions = new ChoiceBox();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        Label searchLabel = new Label("Search Options");
        searchOptions.getItems().add("Color Layout");
        searchOptions.getItems().add("Histogram");
        searchOptions.getItems().add("Global Mean");
        searchOptions.setValue("Global Mean");

        File imageFile = new File(imagesPath+"assets\\"+"default.png");
        ImageView imageView = new ImageView(new Image(new FileInputStream(imageFile)));

        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        Button buttonUpload = new Button("Upload");
        buttonUpload.setPrefWidth(100);
        Button buttonAddToDb = new Button("Add To DB");
        buttonAddToDb.setPrefWidth(100);
        Button buttonSearch = new Button("Search");
        buttonSearch.setPrefWidth(100);

        buttonUpload.setOnAction(e -> {
            try {
                File image = fileChooser.showOpenDialog(primaryStage);
                imageView.setImage(new Image(new FileInputStream(image)));
                searchImagePath = imageFile.getAbsolutePath();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        buttonAddToDb.setOnAction(e -> {
            if (searchImagePath.equals("")){
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Error");
                errorAlert.setContentText("No image was uploaded!");
                errorAlert.showAndWait();
            } else {
                System.out.println("Added To DB!");
            }
        });
        buttonSearch.setOnAction(e -> {
            String searchTech = searchOptions.getValue();
            if(searchTech.equals("Global Mean")) {

            } else if (searchTech.equals("Histogram")) {

            } else if (searchTech.equals("Color Layout")) {

            }
            System.out.println(searchTech);
        });

        vControl.setPadding(new Insets(5, 5, 5, 5));
        vControl.getChildren().addAll(buttonUpload, buttonAddToDb, searchLabel, searchOptions, buttonSearch);
        vControl.setSpacing(10);
        vControl.setAlignment(Pos.CENTER);

        hControl.getChildren().addAll(vControl, imageView);
        hControl.setAlignment(Pos.CENTER);
        hControl.setSpacing(40);

        Button buttonMinus = new Button("<<");
        Button buttonPlus = new Button(">>");
        buttonMinus.setFont(Font.font ("Verdana", 18));
        buttonPlus.setFont(Font.font ("Verdana", 18));
        buttonMinus.setOnAction(e -> {
            modifyStart(false);
            try {
                addFlowPane(imagesPath, getImagesNames(imagesPath, start));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            System.out.println("Button - clicked");
        });
        buttonPlus.setOnAction(e -> {
            modifyStart(true);
            try {
                addFlowPane(imagesPath, getImagesNames(imagesPath, start));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            System.out.println("Button + clicked");
        });

        hViewer.getChildren().add(buttonMinus);
        initFlowPane();
        addFlowPane(imagesPath, getImagesNames(imagesPath, start));
        hViewer.getChildren().add(flowPane);
        hViewer.getChildren().add(buttonPlus);

        hViewer.setAlignment(Pos.CENTER);
        hViewer.setSpacing(20);

        vBox.getChildren().add(hControl);
        vBox.getChildren().add(hViewer);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(50);
        Scene scene = new Scene(vBox, 900, 700);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    private static void initFlowPane() {
        flowPane.setPadding(new Insets(5, 5, 5, 5));
        flowPane.setVgap(5);
        flowPane.setHgap(25);
        flowPane.setPrefHeight(320);
        flowPane.setPrefWidth(700);
        flowPane.setMaxSize(700, 320);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setStyle("-fx-background-color: DAE6F3;");
    }
    public static void addFlowPane(String imagesPath, String[] imagesNames) throws FileNotFoundException {
        flowPane.getChildren().clear();
        ImageView[] images = getImages(imagesPath, imagesNames);
        for (int i=0; i<imagesPerView; i++) {
            flowPane.getChildren().add(images[i]);
        }
    }
    private static ImageView[] getImages(String imagesPath, String[] imagesNames) throws FileNotFoundException {
        ImageView[] pages = new ImageView[imagesPerView];
        int i = 0;
        for (String imageName: imagesNames) {
            ImageView imageView = new ImageView(new Image(new FileInputStream(imagesPath + imageName)));
            imageView.setFitHeight(150);
            imageView.setFitWidth(150);
            pages[i] = imageView;
            i++;
        }
        return pages;
    }
    private static String[]getImagesNames(String imagesPath, int start) {
        return Arrays.copyOfRange(Objects.requireNonNull(new File(imagesPath).list()), start, start + imagesPerView);
    }
    private static int getMaxFileCount(String imagesPath) {
        return Objects.requireNonNull(new File(imagesPath).list()).length;
    }
    private static void modifyStart(boolean increment) {
        if (increment) {
            if (!((start + imagesPerView) > getMaxFileCount(imagesPath))) {
                start += imagesPerView;
            }
        } else {
            if (!((start - imagesPerView) < 0)) {
                start -= imagesPerView;
            }
        }
    }
}
