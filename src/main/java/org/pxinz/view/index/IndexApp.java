package org.pxinz.view.index;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexApp extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Wool Minecraft Launcher");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
