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

import org.json.JSONArray;
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
	//Hub API calls
	//Hub add
	
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
	
	//Hub Remove
	@Path("/hub/remove/{hubID}")
	@DELETE
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
	
	//Component API calls
	//Component Add
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
	
	//Component Retrieve All for userID
	@Path("/component/retrieve/all/{userID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	
	public Response getUserComponents (@PathParam("userID") int userID) throws JSONException {
		
		MySQLAccess mysql = new MySQLAccess();
		
		JSONArray returnData = new JSONArray();
		
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
	
	//Component Retrieve data based on componentID
		@Path("/component/retrieve/{componentID}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		
		public Response getComponentID (@PathParam("componentID") int componentID) throws JSONException {
			
			MySQLAccess mysql = new MySQLAccess();
			
			JSONObject returnData = null;
			
			try 
			{
				mysql.connectDataBase();
				returnData = mysql.getComponent(componentID);
			}
			catch (Exception e)
			{
				String result = "Failed: "+ e;
				return Response.status(400).entity(result).build();
			}
	 
			String result = "Succes \n";
			return Response.status(200).entity(result + returnData).build();
		}
		
	//Component Retrieve All for userID
		@Path("/component/retrieve/status/{componentID}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		
		public Response getComponentStatus (@PathParam("componentID") int componentID) throws JSONException {
			
			MySQLAccess mysql = new MySQLAccess();
			
			JSONObject returnData = null;
			
			try 
			{
				mysql.connectDataBase();
				returnData = mysql.getComponentStatus(componentID);
			}
			catch (Exception e)
			{
				String result = "Failed: "+ e;
				return Response.status(400).entity(result).build();
			}
	 
			String result = "Succes \n";
			return Response.status(200).entity(result + returnData).build();
		}
		
	//Component status edit based on componentID
		@Path("/component/edit/status/{componentID}/{status}")
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		
		public Response setComponentStatus (@PathParam("componentID") int componentID, @PathParam("status") String status) throws JSONException {
			
			MySQLAccess mysql = new MySQLAccess();
			
			JSONObject returnData = null;
			
			try 
			{
				mysql.connectDataBase();
				returnData = mysql.setComponentStatus(componentID,status);
			}
			catch (Exception e)
			{
				String result = "Failed: "+ e;
				return Response.status(400).entity(result).build();
			}
	 
			String result = "Succes \n";
			return Response.status(200).entity(result + returnData).build();
		}
	
	//Component Remove
		@Path("/component/remove/{componentID}")
		@DELETE
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
		
	//Cluster add
		@Path("/cluster/add")
		@POST
		@Produces(MediaType.APPLICATION_JSON)
		
		public Response addCluster (DomoticaCluster cluster) throws JSONException {
			
			
			MySQLAccess mysql = new MySQLAccess();
			
			int clusterID = 0;
			int hubID = 0;
			String name = null;
			
			try
			{
				//clusterID = cluster.getClusterID();
				hubID = cluster.getHubID();
				name = cluster.getName();
			}
			
			catch (Exception e)
			{
				String result = "Failed: "+ e;
				return Response.status(400).entity(result).build();
			}
			
			try 
			{
				mysql.connectDataBase();
				mysql.addCluster(hubID, name);
			}
			catch (Exception e)
			{
				String result = "Failed: "+ e;
				return Response.status(400).entity(result).build();
			}
	 
			String result = "Succes \n";
			return Response.status(200).entity(result).build();
		}
		
	//Cluster retrieve
		@Path("/cluster/retrieve/{clusterID}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		
		public Response retrieveCluster (@PathParam("clusterID") int clusterID) throws JSONException {
			
			
			MySQLAccess mysql = new MySQLAccess();
			JSONArray response = new JSONArray();
			
			try 
			{
				mysql.connectDataBase();
				response = mysql.retrieveCluster(clusterID);
			}
			catch (Exception e)
			{
				String result = "Failed: "+ e;
				return Response.status(400).entity(result).build();
			}
	 
			String result = "Succes \n";
			return Response.status(200).entity(result + response).build();
		}
		
		//Cluster retrieve
		@Path("/cluster/retrieve/all/{hubID}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		
		public Response retrieveAllCluster (@PathParam("hubID") int hubID) throws JSONException {
			
			
			MySQLAccess mysql = new MySQLAccess();
			JSONArray response = new JSONArray();
			
			try 
			{
				mysql.connectDataBase();
				response = mysql.retrieveAllCluster(hubID);
			}
			catch (Exception e)
			{
				String result = "Failed: "+ e;
				return Response.status(400).entity(result).build();
			}
			
			String result = "Succes \n";
			return Response.status(200).entity(result + response).build();
		}
		
	//Cluster remove based on clusterID
		@Path("/cluster/remove/{clusterID}")
		@DELETE
		@Produces(MediaType.APPLICATION_JSON)
		
		public Response removeCluster (@PathParam("clusterID") int clusterID) throws JSONException {
			
			
			MySQLAccess mysql = new MySQLAccess();
			
			try 
			{
				mysql.connectDataBase();
				mysql.removeCluster(clusterID);
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
