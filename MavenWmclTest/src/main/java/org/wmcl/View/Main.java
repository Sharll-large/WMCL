package org.wmcl.View;

import com.alibaba.fastjson.JSONObject;
import org.wmcl.Core.Config;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    static JFrame jFrame = new JFrame();
    static Container container = jFrame.getContentPane();
    static JTextField mc_path_input = new JTextField();
    static JTextField java_path_input = new JTextField();
    static JTextField PlayerName = new JTextField();
    static JTextField JVMMaxRam = new JTextField();


    public static void main(String[] args) throws IOException {
        jFrame.setTitle("Wool Minecraft Launcher");
        jFrame.setSize(400, 300);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLayout(null);
        launch();
        jFrame.setVisible(true);

    }
    public void write(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("McPath", mc_path_input.getText());
        jsonObject.put("JavaPath", java_path_input.getText());
        jsonObject.put("PlayerName", PlayerName.getText());
        jsonObject.put("JvmMaxRam", JVMMaxRam.getText());
        try {
            Config.write(jsonObject);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static void launch() throws IOException {

        JButton mc_path_choose = new JButton("Browse...");
        mc_path_input.setBounds(0, 100, 300, 30);
        mc_path_choose.setBounds(300, 100, 100, 30);
        mc_path_choose.addActionListener(e -> mc_path_input.setText(ChoosePath(false)));

        JButton java_path_choose = new JButton("Browse...");
        java_path_input.setBounds(0, 130, 300, 30);
        java_path_choose.setBounds(300, 130, 100, 30);
        java_path_choose.addActionListener(e -> java_path_input.setText(ChoosePath(true)));

        PlayerName.setBounds(0, 160, 100, 30);

        JVMMaxRam.setBounds(100, 160, 100, 30);

        JButton SaveConfig = new JButton("Save");
        SaveConfig.setBounds(200, 160, 100, 30);
        SaveConfig.addActionListener(e -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("McPath", mc_path_input.getText());
            jsonObject.put("JavaPath", java_path_input.getText());
            jsonObject.put("PlayerName", PlayerName.getText());
            jsonObject.put("JvmMaxRam", JVMMaxRam.getText());
            try {
                Config.write(jsonObject);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton Launch = new JButton("Launch");
        Launch.setBounds(300, 160, 100, 30);


        container.add(mc_path_choose);
        container.add(mc_path_input);
        container.add(java_path_choose);
        container.add(java_path_input);
        container.add(PlayerName);
        container.add(JVMMaxRam);
        container.add(SaveConfig);
        container.add(Launch);
        {
            JSONObject config = Config.read();
            mc_path_input.setText(config.get("McPath").toString());
            java_path_input.setText(config.get("JavaPath").toString());
            PlayerName.setText(config.get("PlayerName").toString());
            JVMMaxRam.setText(config.get("JvmMaxRam").toString());
        }
    }
    public static String ChoosePath(Boolean ChooseFile){
        JFileChooser jFileChooser = new JFileChooser();
        if(ChooseFile) jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        else jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        Container container = jFrame.getContentPane();
        container.add(jFileChooser);
        int option = jFileChooser.showOpenDialog(jFrame);
        if(option == JFileChooser.APPROVE_OPTION){
            File file = jFileChooser.getSelectedFile();
            return file.toPath().toAbsolutePath().toString();
        }else{
            return null;
        }
    }
}
