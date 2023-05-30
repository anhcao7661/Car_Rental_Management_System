/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalmanagementsystem;

import carrentalmanagementsystem.data.carData;
import carrentalmanagementsystem.data.database;
import carrentalmanagementsystem.data.getData;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author anhca
 */
public class dashboardController implements Initializable{
    
    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button availableCars_btn;

    @FXML
    private Button rentCar_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_availableCars;

    @FXML
    private Label home_totalCustomers;

    @FXML
    private Label home_totalIncome;

    @FXML
    private BarChart<?, ?> home_incomeChart;

    @FXML
    private LineChart<?, ?> home_customerChart;

    @FXML
    private AnchorPane availableCars_form;

    @FXML
    private TextField availableCars_carId;

    @FXML
    private TextField availableCars_brand;

    @FXML
    private TextField availableCars_model;

    @FXML
    private ComboBox<?> availableCars_status;

    @FXML
    private ImageView availableCars_imageView;

    @FXML
    private Button availableCars_importBtn;

    @FXML
    private Button availableCars_insertBtn;

    @FXML
    private Button availableCars_updateBtn;

    @FXML
    private Button availableCars_deleteBtn;

    @FXML
    private Button availableCars_clearBtn;

    @FXML
    private TextField availableCars_price;

    @FXML
    private TableView<carData> availableCars_tableView;

    @FXML
    private TableColumn<carData, String> availableCars_col_carId;

    @FXML
    private TableColumn<carData, String> availableCars_col_brand;

    @FXML
    private TableColumn<carData, String> availableCars_col_model;

    @FXML
    private TableColumn<carData, String> availableCars_col_price;

    @FXML
    private TableColumn<carData, String> availableCars_col_status;

    @FXML
    private TextField availableCars_search;

    @FXML
    private AnchorPane rent_form;

    @FXML
    private ComboBox<?> rent_carId;

    @FXML
    private ComboBox<?> rent_brand;

    @FXML
    private ComboBox<?> rent_model;

    @FXML
    private TextField rent_firstName;

    @FXML
    private TextField rent_lastName;

    @FXML
    private ComboBox<?> rent_gender;

    @FXML
    private DatePicker rent_dateRented;

    @FXML
    private DatePicker rent_dateReturn;

    @FXML
    private Button rentBtn;

    @FXML
    private Label rent_total;

    @FXML
    private TextField rent_amount;

    @FXML
    private Label rent_balance;

    @FXML
    private TableView<?> rent_tableView;

    @FXML
    private TableColumn<?, ?> rent_col_carId;

    @FXML
    private TableColumn<?, ?> rent_col_brand;

    @FXML
    private TableColumn<?, ?> rent_col_model;

    @FXML
    private TableColumn<?, ?> rent_col_price;

    @FXML
    private TableColumn<?, ?> rent_col_status;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Image image;
    
    public void availableCarAdd() {
        String sql = "INSERT INTO car (car_id, brand, model, price, status, image, date) "
                + "VALUES(?,?,?,?,?,?,?)";
        
        connect = database.conectDB();
        
        try{
        
            Alert alert;
            
            if(availableCars_carId.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, availableCars_carId.getText());
                prepare.setString(2, availableCars_brand.getText());
                prepare.setString(3, availableCars_model.getText());
                prepare.setString(4, availableCars_price.getText());
                prepare.setString(5, (String)availableCars_status.getSelectionModel().getSelectedItem());
                
                String url = getData.path;
                url = url.replace("\\", "\\\\");
                
                prepare.setString(6, url);
                
                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                
                prepare.setString(7, String.valueOf(sqlDate));
                
                prepare.executeUpdate();
                
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Added");
                alert.showAndWait();
                
                availableCarShowListData();
                availableCarClear();

            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void availableCarUpdate() {
        
        String url = getData.path;
        url = url.replace("\\", "\\\\");
        
        String sql = "UPDATE car SET brand = '"+availableCars_brand.getText()+"', model = '"
                +availableCars_model.getText()+"', status ='"
                +availableCars_status.getSelectionModel().getSelectedItem()+"', price = '"
                +availableCars_price.getText()+"', image = '"+url
                +"' WHERE car_id = '"+availableCars_carId.getText()+"'";
        
        connect = database.conectDB();
        
        try{
            
            Alert alert;
            
            if(availableCars_carId.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to update Car ID: " + availableCars_carId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();
                    
                    availableCarShowListData();
                    availableCarClear();
                }
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void availableCarDelete(){
        String sql = "DELETE FROM car WHERE car_id = '"+availableCars_carId.getText()+"'";
        
        connect = database.conectDB();
        
        try{
            
            Alert alert;
            
            if(availableCars_carId.getText().isEmpty()
                    || availableCars_brand.getText().isEmpty()
                    || availableCars_model.getText().isEmpty()
                    || availableCars_status.getSelectionModel().getSelectedItem() == null
                    || availableCars_price.getText().isEmpty()
                    || getData.path == null || getData.path == ""){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to delete Car ID: " + availableCars_carId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();
                
                if(option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();
                    
                    availableCarShowListData();
                    availableCarClear();
                }
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void availableCarClear() {
        availableCars_carId.setText("");
        availableCars_brand.setText("");
        availableCars_model.setText("");
        availableCars_status.getSelectionModel().clearSelection();
        availableCars_price.setText("");
        
        getData.path = "";
        
        availableCars_imageView.setImage(null);
        
    }
    
    private String[] listStatus = {"Available", "Not Available"};
    public void availableCarStatusList() {
        
        List<String> list = new ArrayList<>();
        
        for(String data: listStatus) {
            list.add(data);
        }
        
        ObservableList listData = FXCollections.observableArrayList(list);
        availableCars_status.setItems(listData);
        
    }
    
    public void availableCarImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Open Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*jpg", "*png"));
        
        File file = open.showOpenDialog(main_form.getScene().getWindow());
        
        if(file != null) {
            
            getData.path = file.getAbsolutePath();
            
            image = new Image(file.toURI().toString(), 151, 179, false, true);
            availableCars_imageView.setImage(image);
            
        }
        
    }
    
    public ObservableList<carData> availableCarListData() {
        
        ObservableList<carData> listData = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM car";
        
        connect = database.conectDB();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            carData carD;
            
            while(result.next()) {
                carD = new carData(result.getInt("car_id"), 
                        result.getString("brand"),
                        result.getString("model"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));
                
                listData.add(carD);
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return listData;
    }
    
    
    
    private ObservableList<carData> availableCarList;
    public void availableCarShowListData() {
        availableCarList = availableCarListData();
        
        availableCars_col_carId.setCellValueFactory(new PropertyValueFactory<>("carId"));
        availableCars_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        availableCars_col_model.setCellValueFactory(new PropertyValueFactory<>("model"));
        availableCars_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableCars_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        availableCars_tableView.setItems(availableCarList);
         
    }
    
    public void availableCarSelect() {
        
        carData carD = availableCars_tableView.getSelectionModel().getSelectedItem();
        int num = availableCars_tableView.getSelectionModel().getSelectedIndex();
        
        if((num - 1) < -1){return;}
        
        availableCars_carId.setText(String.valueOf(carD.getCarId()));
        availableCars_brand.setText(carD.getBrand());
        availableCars_model.setText(carD.getModel());
        availableCars_price.setText(String.valueOf(carD.getPrice()));
        
        getData.path = carD.getImage();
        
        String url = "file:" + carD.getImage();
                
        image = new Image(url, 151, 179, false, true);        
        availableCars_imageView.setImage(image);
    }
    
    
    public void displayUsername() {
        
        String user = getData.username;
        
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
        
    }
    
    private double x = 0;
    private double y = 0;
    public void logout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Comfirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        
        try{
            if(option.get().equals(ButtonType.OK)) {
                
                logoutBtn.getScene().getWindow().hide();
                
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                
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
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void switchForm(ActionEvent event) {
        
        if(event.getSource() == home_btn) {
            
            home_form.setVisible(true);
            availableCars_form.setVisible(false);
            rent_form.setVisible(false);
            
            home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #686f86, #8e9296);");
            availableCars_btn.setStyle("-fx-background-color: transparent");
            rentCar_btn.setStyle("-fx-background-color: transparent");
            
        }else if(event.getSource() == availableCars_btn) {
            
            home_form.setVisible(false);
            availableCars_form.setVisible(true);
            rent_form.setVisible(false);
            
            availableCars_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #686f86, #8e9296);");
            home_btn.setStyle("-fx-background-color: transparent");
            rentCar_btn.setStyle("-fx-background-color: transparent");
            
            availableCarShowListData();
            availableCarStatusList();
            
        }else if(event.getSource() == rentCar_btn) {
            
            home_form.setVisible(false);
            availableCars_form.setVisible(false);
            rent_form.setVisible(true);
            
            rentCar_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #686f86, #8e9296);");
            availableCars_btn.setStyle("-fx-background-color: transparent");
            home_btn.setStyle("-fx-background-color: transparent");
            
        }
        
    }
    
    public void close() {
        System.exit(0);
    }
    
    public void minimize() {
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        
        availableCarShowListData();
        availableCarStatusList();
    }
    
}
