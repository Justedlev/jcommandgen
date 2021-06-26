package jcommandgen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JCommandGenApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/gui/main.fxml")));
        stage.setTitle("jcommandgen");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/gui/assets/3D_Grass_25701.png"))));
        stage.setScene(new Scene(root, 815, 455));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}