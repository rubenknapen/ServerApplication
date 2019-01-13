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
    
    public boolean addComponent(int hubID, int clusterID, String name, String type, int port, String status) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("insert into  mijndomeindatabase.domoticacomponent values (NULL, ?, ?, ?, ?, ?, ?)");
        
        preparedStatement.setInt(1, hubID);
        preparedStatement.setInt(2, clusterID);
        preparedStatement.setString(3, name);
        preparedStatement.setString(4, type);
        preparedStatement.setInt(5, port);
        preparedStatement.setString(6, status);
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
    
    public JSONObject getAllComponents(int userID) throws SQLException
    {
    	JSONObject response = new JSONObject();
    	int i = 0;
    	int hubID = 0;
    	
    	// Statements allow to issue SQL queries to the database
        statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        String subQuery = String.format("SELECT hubID FROM  mijndomeindatabase.user WHERE userID = %d", userID);
        resultSet = statement.executeQuery(subQuery);
        while (resultSet.next())
        {
        	hubID = resultSet.getInt("hubID");
        	System.out.println("hubID found: "+hubID);
        }
        resultSet.close();
        
        String query = String.format("SELECT componentID FROM  mijndomeindatabase.domoticacomponent WHERE hubID = %d", hubID);
        resultSet = statement.executeQuery(query);
        
        while (resultSet.next())
        {
        	
        	System.out.println("ComponentID found: "+resultSet.getInt("componentID"));
        	int componentID = resultSet.getInt("componentID");
        	response.put("componentID"+i, componentID);
        	i++;
        }
        close();    	
    	return response;
    }
    
    public boolean userLogin(int userID) throws SQLException
    {    	
    	int results = 0;
    	// Statements allow to issue SQL queries to the database
        statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        String query = String.format("SELECT userID FROM  mijndomeindatabase.user WHERE userID = (%d)", userID);
        		
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next())
        {
        	results++;
        }
        close();
        if (results != 0)
        {
        	return true;
        }
        else
        {
        	return false;
        }
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