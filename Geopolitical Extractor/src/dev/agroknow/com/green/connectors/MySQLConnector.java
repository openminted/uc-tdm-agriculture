/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of version 3 of the GNU General Public License as published by
 * the Free Software Foundation, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 */
package dev.agroknow.com.green.connectors;
import java.sql.*;  
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SOTIRIS SON
 */

public class MySQLConnector{  
    
    private Connection conn;
    private String database_name;
    private String username;
    private String password;

    public MySQLConnector(String database_name, String username, String password) {
        this.database_name = database_name;
        this.username = username;
        this.password = password;
    }   
    
    public Connection getConnection(){
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+this.database_name+","+this.username+","+this.password);                    
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(MySQLConnector.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
        return conn;        
    }
    
}  
