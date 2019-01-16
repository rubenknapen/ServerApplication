package com.mijndomein.api.config;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.mijndomein.mysql.MySQLAccess;

@Path("/user")

public class UserApi 
{
	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	
	public Response userLogin (User user) throws JSONException {
		
		int userID = 0;
		int hubID = 0;
		String name = null;
		String userName = null;
		String password = null;
		
		boolean query = false;
		
		try
		{
			userID = user.getUserID();
			hubID = user.getHubID();
			name = user.getName();
			userName = user.getUserName();
			password = user.getPassword();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e);
		}
		
		MySQLAccess mysql = new MySQLAccess();
		
		try 
		{
			mysql.connectDataBase();
			query = mysql.userLogin(userName,password);
		}
		catch (Exception e)
		{
			String result = "Failed: "+ e;
			return Response.status(400).entity(result).build();
		}
 
		if (query)
		{
			String result = "";
			return Response.status(200).entity(result).build();
		}
		else
		{
			String result = "Failed: Login Error \n";
			return Response.status(400).entity(result).build();
		}
	}
}
