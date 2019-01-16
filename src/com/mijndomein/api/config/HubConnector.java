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
import com.mijndomein.mysql.MySQLAccess;

public class HubConnector implements Runnable
{
	String name;
	Thread t;
	SerialPort port;
	OutputStream outputStream;
	int hubID = 0;
	
	//Commands to send to Arduino
	String portString;
	String tempCommand = "a";
	String humidityCommand = "b";
	String switch1Command = "c";
	String switch2Command = "d";
	String switch3Command = "e";
	
	//Values to write to the database
	boolean switch1 = false;
	boolean switch2 = false;
	boolean switch3 = false;	
	int temp = 0;
	int humidity = 0;
	
	boolean connected = false;
	
	HubConnector (String thread, String portValue, int currentHubID)
	{
		hubID = currentHubID;
		portString = portValue;
	    name = "HubConnector"; 
	    t = new Thread(this, name);
	    System.out.println("New thread: " + t);
	    t.start();
	}
	
	@Override
	public void run() 
	{
		
		try 
		{
			if (!connected)
			{
				setupConnection();
				System.out.println("Entering sleeptimer (20s)");
				Thread.sleep(20000);
			}
			
			if (connected)
			{
				getTemp();
				System.out.println("Entering sleeptimer (10s)");
				Thread.sleep(10000);
			    getHumidity();
			    System.out.println("Entering sleeptimer (10s) then we write the new values into the database");
			    Thread.sleep(10000);
			    writeValuesToDatabase();
			}
			
		    System.out.println("Entering sleeptimer (30s)");
			Thread.sleep(30000);
			System.out.println("Looping again through arduino current values!)");
			run();
		}
		
		catch (SQLException | InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setupConnection() throws SQLException
	{
		try
		{
			// determine which serial port to use
		    SerialPort ports[] = SerialPort.getCommPorts();
		    int i = 0;
		    int selectedPortIndex = 0;
		    System.out.println("received port to connect: "+portString);
		    for(SerialPort port : ports) 
		    {
		            System.out.println(i++ + ". " + port.getSystemPortName());
		            if(port.getSystemPortName().equals(portString))
					{
						selectedPortIndex = i;
						System.out.println("selectedPortIndex = "+ selectedPortIndex);
					}
		    }
		    
		    // open and configure the port
	        port = ports[selectedPortIndex];
	        if(port.openPort()) {
	                System.out.println("Successfully opened the port.");
	                connected = true;
	        } 
	        else 
	        {
	                System.out.println("Unable to open the port.");
	                return;
	        }
	        
	        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		       
	        port.addDataListener(new SerialPortDataListener() 
	        {
	    	   @Override
	    	   public int getListeningEvents() 
	    	   { 
	    		   return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; 
	    	   }
	    	   @Override
	    	   public void serialEvent(SerialPortEvent event)
	    	   {
	    	      if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
	    	         return;
	    	      try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	      byte[] newData = new byte[port.bytesAvailable()];
	    	      int numRead = port.readBytes(newData, newData.length);
	    	      if(newData.length == 0)
	    	    	  return;
	    	      System.out.println("Read " + numRead + " bytes.");
	    	      String arduinoResponse = new String(newData);
	    	      System.out.println("String read: "+ arduinoResponse);
	    	      stringSplitter(arduinoResponse);
	    	      
	    	   }
	        });
			System.out.println("Datalistener added!");
	        outputStream = port.getOutputStream();
	        System.out.println("Outputstream added!");
		    
		}
		catch(ArrayIndexOutOfBoundsException exception)
		{
			System.out.println("Device not found on COM-port");
			return;
		}
	}
		
	
	public void stringSplitter(String stringToSplit)
	{
		try
		{
			String[] parts = stringToSplit.split(":");
			System.out.println("parts[0]: "+parts[0]);
			System.out.println("parts[1]: "+parts[1]);
			
			if(parts[0].equals("temp"))
			{
				System.out.println("new temp: "+parts[1]);
				temp = Integer.parseInt(parts[1]);
			}
			
			else if(parts[0].equals("humidity"))
			{
				System.out.println("new humidity: "+parts[1]);
				humidity = Integer.parseInt(parts[1]);
			}
			else if(parts[0].equals("switch1"))
			{
				System.out.println("new status switch1: "+parts[1]);
				if(Integer.parseInt(parts[1]) == 1)
				{
					switch1 = true;
				}
				else if(Integer.parseInt(parts[1]) == 0)
				{
					switch1 = false;
				}
			}
			else if(parts[0].equals("switch2"))
			{
				System.out.println("new status switch2: "+parts[1]);
				if(Integer.parseInt(parts[1]) == 1)
				{
					switch2 = true;
				}
				else if(Integer.parseInt(parts[1]) == 0)
				{
					switch2 = false;
				}
			}
			else if(parts[0].equals("switch3"))
			{
				System.out.println("new status switch3: "+parts[1]);
				if(Integer.parseInt(parts[1]) == 1)
				{
					switch3 = true;
				}
				else if(Integer.parseInt(parts[1]) == 0)
				{
					switch3 = false;
				}
			}
		}
		catch (java.lang.ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Unknown command, skipping!");
		}
	}
	
	public void getTemp()
	{
		System.out.println("Sending tempCommand now...");
		
		try 
		{
			outputStream.write(tempCommand.getBytes());
		} 
		catch (IOException e)
		{
			connected = false;
			System.out.println("Connection lost...");
			return;
		}
	}
	
	public void getHumidity()
	{
		System.out.println("Sending humidityCommand now...");
		
		try 
		{
			outputStream.write(humidityCommand.getBytes());
		} 
		catch (IOException e) 
		{
			connected = false;
			System.out.println("Connection lost...");
			return;
		}
	}
	
	public void writeValuesToDatabase()
	{
		/*
		MySQLAccess mysql = new MySQLAccess();
		
		int tempComponentID = 0;
		int humidityComponentID = 0;
		int switch1ID = 0;
		int switch2ID = 0;
		int switch3ID = 0;
		
		mysql.connectDataBase();
		tempComponentID = mysql.getTempComponentID();
		humidityComponentID = mysql.getHumidityComponentID();
		switch1ID = mysql.getSwitchComponentID("switch1");
		switch2ID = mysql.getSwitchComponentID("switch2");
		switch3ID = mysql.getSwitchComponentID("switch3");
		
		mysql.setComponentStatus(tempComponentID,Integer.toString(temp));
		mysql.setComponentStatus(humidityComponentID,Integer.toString(humidity));
		*/
	}
}

