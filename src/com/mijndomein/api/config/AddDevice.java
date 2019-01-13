package com.mijndomein.api.config;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mijndomein.mysql.MySQLAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
 


@Path("/add-device")

public class AddDevice 
{
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response addDevice(Device device) throws JSONException {
		int DeviceId = 0;
		int DeviceType = 0;
		int DevicePort = 0;
		
		System.out.println("incoming: "+device);
		
		try
		{
			DeviceId = device.getId();
			DeviceType = device.getType();
			DevicePort = device.getPort();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e);
		}
		
		MySQLAccess mysql = new MySQLAccess();
		
		try 
		{
			mysql.connectDataBase();
			mysql.addDevice(DeviceId,DeviceType,DevicePort);
		}
		catch (Exception e)
		{
			String result = "Failed: "+ e;
			return Response.status(400).entity(result).build();
		}
 
		String result = "Succes \n";
		return Response.status(200).entity(result).build();
	}
}
