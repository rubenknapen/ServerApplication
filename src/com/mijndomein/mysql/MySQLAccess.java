package com.mijndomein.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.json.JSONObject;

public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void connectDataBase() throws Exception {
        try {
            // Setup the connection swith the DB
            /*connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/mijndomeindatabase?"
                            + "user=sqluser&password=sqluserpw");
            */
        	
            Class.forName("com.mysql.jdbc.Driver");  
            connect = DriverManager.getConnection(  
            "jdbc:mysql://localhost/mijndomeindatabase?","sqluser","sqluserpw");  


        } catch (Exception e) {
            throw e;
        }
    }
    
    public boolean addDomoticaCluster(int clusterID, int hubID, String name) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("insert into  mijndomeindatabase.domoticacluster values (?, ?, ?)");
        
        preparedStatement.setInt(1, clusterID);
        preparedStatement.setInt(2, hubID);
        preparedStatement.setString(3, name);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean addComponent(int componentID, int hubID, int clusterID, String name, int type, int port, String status) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("insert into  mijndomeindatabase.domoticacomponent values (?, ?, ?, ?, ?, ?, ?)");
        
        preparedStatement.setInt(1, componentID);
        preparedStatement.setInt(2, hubID);
        preparedStatement.setInt(3, clusterID);
        preparedStatement.setString(4, name);
        preparedStatement.setInt(5, type);
        preparedStatement.setInt(6, port);
        preparedStatement.setString(7, status);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean addHub(int hubID) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("insert into  mijndomeindatabase.domoticahub values (?)");
        
        preparedStatement.setInt(1, hubID);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean removeComponent(int componentID) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("DELETE FROM  mijndomeindatabase.domoticacomponent WHERE componentID = (?)");
        
        preparedStatement.setInt(1, componentID);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean removeHub(int hubID) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("DELETE FROM  mijndomeindatabase.domoticahub WHERE hubID = (?)");
        
        preparedStatement.setInt(1, hubID);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public JSONObject getAllDevices() throws SQLException
    {
    	JSONObject response = null;
    	
    	// Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("SELECT componentID FROM  mijndomeindatabase.domoticacomponent");
        
        preparedStatement.executeUpdate();
        close();
    	
    	return response;
    }


    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}