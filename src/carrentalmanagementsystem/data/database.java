/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package carrentalmanagementsystem.data;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 *
 * @author anhca
 */
public class database {
    
    public static Connection conectDB() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/rentcar", "root", "");
            return connect;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
}
