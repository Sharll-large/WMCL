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
    public List<String> before=new ArrayList<String>(Arrays.asList("Cute","Sussy","Shiny"));
    public List<String> after=new ArrayList<String>(Arrays.asList("Baka","Guy"));
    @FXML
    private ComboBox javaChooser;
    @FXML
    private ComboBox versionChooser;
    @FXML
    private TextField nameInput;
    private GameScanner scanner = new GameScanner();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        javaChooser.setEditable(true);
        scanJava();
        scanVersion();
        randomName();
    }

    @FXML
    protected void scanJava() {
        List<String> javaList = scanner.scanJava();
        javaChooser.getItems().removeAll();
        javaChooser.getItems().addAll(javaList);
        javaChooser.setValue("Please choose:");
    }

    @FXML
    protected void scanVersion() {
        List<String> versionList = scanner.scanMc("D:\\Plain Minecraft Launcher\\.minecraft");
        versionChooser.getItems().removeAll();
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
        String javaPath = javaChooser.getValue().toString();
        String gameVersion = versionChooser.getValue().toString();
        String playerName = nameInput.getText();
        if(javaPath!="Please choose:" && gameVersion!="Please choose:" && playerName!=""){
            Launcher launcher=new Launcher("D:\\Plain Minecraft Launcher\\.minecraft",gameVersion,javaPath,playerName);
            launcher.Launch();
        }
    }

}
