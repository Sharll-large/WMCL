package org.pxinz.view.index;

import javafx.scene.control.TextField;
import org.sharll.file.GameScanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.sharll.launcher.Launcher;

import java.net.URL;
import java.util.*;

public class IndexController implements Initializable {
    public List<String> before= new ArrayList<>(Arrays.asList("Cute", "Sussy", "Shiny"));
    public List<String> after= new ArrayList<>(Arrays.asList("Baka", "Guy"));
    @FXML
    private ComboBox<String> javaChooser;
    @FXML
    private ComboBox<String> versionChooser;
    @FXML
    private TextField nameInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        javaChooser.setEditable(true);
        scanJava();
        scanVersion();
        randomName();
    }

    @FXML
    protected void scanJava() {
        List<String> javaList = GameScanner.scanJava();
        javaChooser.getItems().removeAll(javaList);
        javaChooser.getItems().addAll(javaList);
        javaChooser.setValue("Please choose:");
    }

    @FXML
    protected void scanVersion() {
        List<String> versionList = GameScanner.scanMc("D:\\Plain Minecraft Launcher\\.minecraft");
        versionChooser.getItems().removeAll(versionList);
        versionChooser.getItems().addAll(versionList);
        versionChooser.setValue("Please choose:");
    }

    @FXML
    protected void randomName(){
        Random random=new Random();
        String name="";
        name+=before.get(random.nextInt(before.size()));
        name+=after.get(random.nextInt(after.size()));
        name+=String.valueOf(random.nextInt(10000));
        nameInput.setText(name);
    }

    @FXML
    protected void launch() {
        String javaPath = javaChooser.getValue();
        String gameVersion = versionChooser.getValue();
        String playerName = nameInput.getText();
        if(!Objects.equals(javaPath, "Please choose:") && !Objects.equals(gameVersion, "Please choose:") && !Objects.equals(playerName, "")){
            Launcher launcher=new Launcher("D:\\Plain Minecraft Launcher\\.minecraft",gameVersion,javaPath,playerName);
            launcher.Launch();
        }
    }

}
