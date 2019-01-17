package com.mijndomein.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;

import com.fazecast.jSerialComm.SerialPort;

public class ConnectionCheck implements Runnable
{
	private PreparedStatement preparedStatement = null;
	private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    
    List<Integer> connectedHubID;
    List<Integer> unconnectedHubID;
    List<Runnable> activeThreads;
    
	String name;
	Thread t;
	int i = 0;
	boolean connected = false;
	String msg = "b";
	OutputStream outputStream;
	SerialPort port;
	
	int connectedUnits = 0;
	
	int temp = 0;
	int humidity = 0;
    
	ConnectionCheck (String thread)
	{
		connectedHubID = new ArrayList<Integer>();
		unconnectedHubID = new ArrayList<Integer>();
		activeThreads = new ArrayList<Runnable>();
	    name = "ConnectionCheck"; 
	    t = new Thread(this, name);
	    System.out.println("New thread: " + t);
	    t.start();
	}
	
	@Override
	public void run() 
	{
		System.out.println("["+t.getName()+"]: Checking for new devices");
		
		try 
		{
			getHubIDs();
			setConnectionThread();
			//System.out.println("Entering 15 sec sleep!");
			Thread.sleep(15000);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		i++;
		run();
	}
	
	public void setConnectionThread()
	{
		try
    	{
			setupConnectionDatabase();
			
	    	// Statements allow to issue SQL queries to the database
	        statement = connect.createStatement();
	        
	        /*
	        System.out.println("amount of unconnectedHubID's: "+unconnectedHubID.size());
	        System.out.println("ID of unconnectedHubID's: "+unconnectedHubID.get(0));
	        */
	        
	        for (int j = 0; j < unconnectedHubID.size(); j++ )
	        {
	        	preparedStatement = connect
	                    .prepareStatement("SELECT * FROM  mijndomeindatabase.domoticahub WHERE hubID = (?)");
	            
	            preparedStatement.setInt(1, unconnectedHubID.get(j));
	            
	            ResultSet resultSet = preparedStatement.executeQuery();       	
	        	
		        while (resultSet.next())
		        {
		        	if(!connectedHubID.contains(resultSet.getInt("hubID")))
		        	{
			        	HubConnector temp = new HubConnector("HubConnector"+resultSet.getInt("hubID"),resultSet.getString("port"), resultSet.getInt("hubID"));
			        	connectedHubID.add(resultSet.getInt("hubID"));
			        	//System.out.println("hubID added to connected list: "+);
		        	}
		        }
	        }
	        unconnectedHubID.removeAll(connectedHubID);
    	}
		catch (Exception e)
		{
			System.out.println("error hier gaat het mis1: "+e);
		}
    	finally
    	{
    		close();
    	}
		
		
		//itterate over unconnected HubID's
		if(connectedUnits < 1)
		{
			
			connectedUnits++;
		}
	}	
	
	public void setupConnectionDatabase() throws Exception
	{		
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
	
	public void getHubIDs() throws SQLException
	{		
		try
    	{
			setupConnectionDatabase();
			
	    	// Statements allow to issue SQL queries to the database
	        statement = connect.createStatement();
	
	        // PreparedStatements can use variables and are more efficient
	        String query = String.format("SELECT * FROM  mijndomeindatabase.domoticahub");
	        resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next())
	        {
	        	// Hier alle info ophalen en in list zetten
	        	unconnectedHubID.add(resultSet.getInt("hubID"));
	        	//System.out.println("hubID: " + resultSet.getInt("hubID") + " added");
	        }
        
    	}
		catch (Exception e)
		{
			System.out.println("error hier gaat het mis2: "+e);
		}
    	finally
    	{
    		close();
    	}
	}
	
	private void close() 
	{
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
