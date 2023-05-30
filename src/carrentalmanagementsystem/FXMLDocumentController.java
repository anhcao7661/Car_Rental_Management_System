/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package carrentalmanagementsystem;

import carrentalmanagementsystem.data.database;
import carrentalmanagementsystem.data.getData;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author anhca
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Button close;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private double x = 0;
    private double y = 0;
    
    public void loginAdmin() {
        
        String slq = "SELECT * FROM admin WHERE username = ? and password = ?";
        
        connect = database.conectDB();
        
        try{
            prepare = connect.prepareStatement(slq);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());
            
            result = prepare.executeQuery();
            
            Alert alert;
            
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fills");
                alert.showAndWait();
            }else{
                
                if(result.next()){
                    
                    getData.username = username.getText();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!!");
                    alert.showAndWait();
                    
                    loginBtn.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);

                        stage.setOpacity(.8);
                    });

                    root.setOnMouseReleased((MouseEvent event) -> {
                        stage.setOpacity(1);
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                                    
                    stage.setScene(scene);
                    stage.show();
                    
                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
                
            }
            
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void close() {
        System.exit(0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
