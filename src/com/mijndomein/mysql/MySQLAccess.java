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
    
    
    
    public JSONObject getComponent(int componentID) throws SQLException
    {
    	try
    	{
    		
    	JSONObject response = new JSONObject();
    	int hubID = 0;
    	int clusterID = 0;
    	String name = null;
    	String type = null;
    	int port = 0;
    	String status = null;
    	
    	// Statements allow to issue SQL queries to the database
        statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        String query = String.format("SELECT * FROM  mijndomeindatabase.domoticacomponent WHERE componentID = %d", componentID);
        resultSet = statement.executeQuery(query);
        
        while (resultSet.next())
        {
        	// Hier alle info ophalen en in JSON zetten
        	response.put("componentID", resultSet.getInt("componentID"));
        	response.put("hubID", resultSet.getInt("hubID"));
        	response.put("clusterID", resultSet.getInt("clusterID"));
        	response.put("name", resultSet.getString("name"));
        	response.put("type", resultSet.getString("type"));
        	response.put("port", resultSet.getInt("port"));
        	response.put("status", resultSet.getString("status"));
        }
        
    	return response;
    	}
    	
    	finally
    	{
    		close();
    	}
    }
    
    public JSONObject getComponentStatus(int componentID) throws SQLException
    {
    	try
    	{
    		
    	JSONObject response = new JSONObject();
    	
    	String status = null;
    	
    	// Statements allow to issue SQL queries to the database
        statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        String query = String.format("SELECT * FROM  mijndomeindatabase.domoticacomponent WHERE componentID = %d", componentID);
        resultSet = statement.executeQuery(query);
        
        while (resultSet.next())
        {
        	// Hier alle info ophalen en in JSON zetten
        	response.put("componentID", resultSet.getInt("componentID"));
        	response.put("status", resultSet.getString("status"));
        }
        
    	return response;
    	}
    	
    	finally
    	{
    		close();
    	}
    }
    
    public JSONObject setComponentStatus(int componentID, String status) throws SQLException
    {
    	try
    	{
    		
    	JSONObject response = new JSONObject();
    	
    	// Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

    	preparedStatement = connect
                .prepareStatement("UPDATE mijndomeindatabase.domoticacomponent SET status = (?) WHERE componentID = (?)");
        
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, componentID);
        preparedStatement.executeUpdate();
    	
        // PreparedStatements can use variables and are more efficient
        //String query = String.format("UPDATE mijndomeindatabase.domoticacomponent SET status = %s WHERE componentID = %d", status, componentID);
        //resultSet = statement.executeQuery(query);
        
    	return response;
    	}
    	
    	finally
    	{
    		close();
    	}
    }
    
    public JSONObject getAllComponents(int userID) throws SQLException
    {
    	try
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
        	response.append("componentID", componentID);
        }
        
    	return response;
    	}
    	
    	finally
    	{
    		close();
    	}
    }
    
    public boolean addCluster(int hubID, String name) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("INSERT into  mijndomeindatabase.domoticacluster values (NULL, ?, ?)");
        
        preparedStatement.setInt(1, hubID);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean removeCluster(int clusterID) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        preparedStatement = connect
                .prepareStatement("DELETE FROM  mijndomeindatabase.domoticacluster WHERE clusterID =  (?)");
        
        preparedStatement.setInt(1, clusterID);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean addConfiguration(int hubID, String name) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
    	preparedStatement = connect
                .prepareStatement("INSERT into  mijndomeindatabase.configuration values (NULL, ?, ?)");
        
        preparedStatement.setInt(1, hubID);
        preparedStatement.setString(2, name);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean editConfiguration(int configurationID, String name) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
    	preparedStatement = connect
                .prepareStatement("UPDATE mijndomeindatabase.configuration SET name = (?) WHERE configurationID = (?)");
        
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2, configurationID);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean removeConfiguration(int configurationID) throws SQLException 
    {
        // Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
    	preparedStatement = connect
                .prepareStatement("DELETE FROM  mijndomeindatabase.configuration WHERE configurationID =  (?)");
        
        preparedStatement.setInt(1, configurationID);
        preparedStatement.executeUpdate();
        close();
    	return true;
    }
    
    public boolean userLogin(String userName, String password) throws SQLException
    {    	
    	int results = 0;
    	// Statements allow to issue SQL queries to the database
        //statement = connect.createStatement();

        // PreparedStatements can use variables and are more efficient
        //String query = String.format("SELECT userID FROM  mijndomeindatabase.user WHERE username = %s AND password = %s", userName, password);
        
        preparedStatement = connect
                .prepareStatement("SELECT userID FROM  mijndomeindatabase.user WHERE username = (?) AND password = (?)");
        
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        
        ResultSet resultSet = preparedStatement.executeQuery();
        
        while (resultSet.next())
        {
        	results++;
        }
        close();
        if (results == 1)
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