package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {
    String logoPath = "D:\\Quds_photos\\assets\\";
    @Override
    public void start(Stage primaryStage) throws Exception{

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(200, 0, 0, 0));
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);
        StackPane stackPane = new StackPane();
        ImageView logo = new ImageView(new Image(new FileInputStream(logoPath + "logo.png")));
        logo.setFitWidth(400);
        logo.setFitHeight(400);
        Button buttonCbir = new Button("CBIR System");
        Button buttonCbvr = new Button("CBVR System");

        buttonCbir.setPrefWidth(125);
        buttonCbvr.setPrefWidth(125);

        buttonCbir.setOnAction(e -> {
            try{
                CBIR.display();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        });
        buttonCbvr.setOnAction(e -> {
            try{
                CBVR.display();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        });
        vbox.getChildren().addAll(buttonCbir, buttonCbvr);
        stackPane.getChildren().addAll(logo, vbox);
        Scene scene = new Scene(stackPane, 400, 400);
        primaryStage.setTitle("Multimedia Project");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}