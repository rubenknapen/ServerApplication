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
 


@Path("/domotica")

public class DomoticaApi 
{
	@Path("/hub/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response addHub (DomoticaHub hub) throws JSONException {
		
		int hubID = 0;
		
		try
		{
			hubID = hub.getHubID();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e);
		}
		
		MySQLAccess mysql = new MySQLAccess();
		
		try 
		{
			mysql.connectDataBase();
			mysql.addHub(hubID);
		}
		catch (Exception e)
		{
			String result = "Failed: "+ e;
			return Response.status(400).entity(result).build();
		}
 
		String result = "Succes \n";
		return Response.status(200).entity(result).build();
	}	
	
	@Path("/component/add")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response addComponent (DomoticaComponent component) throws JSONException {
		
		int hubID = 0;
		int clusterID = 0;
		String name = null;
		String type = null;
		int port = 0;
		String status = null;
		
		System.out.println("incoming new component: " + component.getName());
		
		try
		{
			hubID = component.getHubID();
			clusterID = component.getClusterID();
			name = component.getName();
			type = component.getType();
			port = component.getPort();
			status = component.getStatus();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e);
		}
		
		MySQLAccess mysql = new MySQLAccess();
		
		try 
		{
			mysql.connectDataBase();
			mysql.addComponent(hubID,clusterID,name,type,port,status);
		}
		catch (Exception e)
		{
			String result = "Failed: "+ e;
			return Response.status(400).entity(result).build();
		}
 
		String result = "Succes \n";
		return Response.status(200).entity(result).build();
	}
	
	@Path("/component/remove/{componentID}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response removeComponent(@PathParam("componentID") int componentID) throws JSONException {
		
		
		MySQLAccess mysql = new MySQLAccess();
		
		try 
		{
			mysql.connectDataBase();
			mysql.removeComponent(componentID);
		}
		catch (Exception e)
		{
			String result = "Failed: "+ e;
			return Response.status(400).entity(result).build();
		}
 
		String result = "Succes \n";
		return Response.status(200).entity(result).build();
	}
	
	@Path("/hub/remove/{hubID}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response removeHub(@PathParam("hubID") int hubID) throws JSONException {
		
		
		MySQLAccess mysql = new MySQLAccess();
		
		try 
		{
			mysql.connectDataBase();
			mysql.removeHub(hubID);
		}
		catch (Exception e)
		{
			String result = "Failed: "+ e;
			return Response.status(400).entity(result).build();
		}
 
		String result = "Succes \n";
		return Response.status(200).entity(result).build();
	}
	
	@Path("/component/retrieve/all/{userID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getUserComponents (@PathParam("userID") int userID) throws JSONException {
		
		MySQLAccess mysql = new MySQLAccess();
		
		JSONObject returnData = null;
		
		try 
		{
			mysql.connectDataBase();
			returnData = mysql.getAllComponents(userID);
		}
		catch (Exception e)
		{
			String result = "Failed: "+ e;
			return Response.status(400).entity(result).build();
		}
 
		String result = "Succes \n";
		return Response.status(200).entity(result + returnData).build();
	}
}
