package com.mijndomein.api.config;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
 


@Path("/configuration")

public class ConfigurationApi 
{
	//Configuration API calls
	
	//Configuration add	
	@Path("/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response addConfiguration (Configuration config) throws JSONException 
	{		
		int hubID = 0;
		String name = null;
		
		try
		{
			hubID = config.getHubID();
			name = config.getName();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e);
		}
		
		MySQLAccess mysql = new MySQLAccess();
		
		try 
		{
			mysql.connectDataBase();
			mysql.addConfiguration(hubID,name);
		}
		catch (Exception e)
		{
			String result = "Failed: "+ e;
			return Response.status(400).entity(result).build();
		}
 
		String result = "Succes \n";
		return Response.status(200).entity(result).build();
	}
	
	//Configuration edit	
		@Path("/edit")
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		
		public Response editConfiguration (Configuration config) throws JSONException 
		{		
			int configurationID = 0;
			String name = null;
			
			try
			{
				configurationID = config.getConfigurationID();
				name = config.getName();
			}
			catch (Exception e)
			{
				System.out.println("Error: " + e);
			}
			
			MySQLAccess mysql = new MySQLAccess();
			
			try 
			{
				mysql.connectDataBase();
				mysql.editConfiguration(configurationID, name);
			}
			catch (Exception e)
			{
				return Response.status(400).entity(e).build();
			}
	 
			return Response.status(200).build();
		}
	
	//Configuration Remove
	@Path("/remove/{configurationID}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response removeHub(@PathParam("configurationID") int configurationID) throws JSONException 
	{				
		MySQLAccess mysql = new MySQLAccess();
		
		try 
		{
			mysql.connectDataBase();
			mysql.removeConfiguration(configurationID);
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
