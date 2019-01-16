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
	    name = "ConnectionCheck"; 
	    t = new Thread(this, name);
	    System.out.println("New thread: " + t);
	    t.start();
	}
	
	@Override
	public void run() 
	{
		System.out.println("Checking for new devices");
		
		try 
		{
			getHubIDs();
			removeConnectedHubIDs();
			setConnectionThread();			
			System.out.println("Entering 15 sec sleep!");
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
	        
	        for (int j = 0; j < unconnectedHubID.size(); j++ )
	        {
	        	preparedStatement = connect
	                    .prepareStatement("SELECT * FROM  mijndomeindatabase.domoticahub WHERE hubID = (?)");
	            
	            preparedStatement.setInt(1, unconnectedHubID.get(j));
	            
	            ResultSet resultSet = preparedStatement.executeQuery();       	
	        	
		        while (resultSet.next())
		        {
		        	new HubConnector("HubConnector"+resultSet.getInt("hubID"),resultSet.getString("port"));
		        	connectedHubID.add(resultSet.getInt("hubID"));
		        }
	        }
	        removeConnectedHubIDs();
    	}
		catch (Exception e)
		{
			System.out.println("error: "+e);
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
	
	public void removeConnectedHubIDs()
	{
		unconnectedHubID.removeAll(connectedHubID);
	}
	
	
        
		/*
        try 
        {
        	System.out.println("Sending msg now...");
			outputStream.write(msg.getBytes());
		} 
        catch (IOException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		*/
		/*
		String address = null;
		String port = null;
	
		
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
	        }
        
    	}
		catch (Exception e)
		{
			System.out.println("error: "+e);
		}
    	finally
    	{
    		close();
    	}
    	
		if (!connected)
		{
			{
				obj = new Arduino("COM6",9600);
				if (obj.openConnection())
		        {
		        	connected = true;
		        	System.out.println("COM6 connection opened!");
		        	
		        	try 
		        	{
						Thread.sleep(10000);
					} 
		        	
		        	catch (InterruptedException e) 
		        	{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			}
        }
		
		System.out.println("sending serial....");
		obj.serialWrite("b",1,0);
		System.out.println("reading serial....");
    	msg = obj.serialRead();
    	
    	System.out.println("arduino data: " + msg);
		
        try 
        {
			Thread.sleep(5000);
		} 
        catch (InterruptedException e) 
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	
	
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
	        	System.out.println("hubID: " + resultSet.getInt("hubID") + " added");
	        }
        
    	}
		catch (Exception e)
		{
			System.out.println("error: "+e);
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
