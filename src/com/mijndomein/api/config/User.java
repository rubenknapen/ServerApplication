package com.mijndomein.api.config;

public class User 
{
	private int userID;
	private int hubID;
	private String name;
	private String userName;
	private String password;
	
	//Setters
	public void setUserID(int newUserID)
	{
		userID = newUserID;
	}
	
	public void setHubID(int newHubID)
	{
		hubID = newHubID;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public void setUserName(String newUserName)
	{
		userName = newUserName;
	}
	
	public void setPassword(String newPassword)
	{
		password = newPassword;
	}
	
	//Getters
	public int getUserID()
	{
		return userID;
	}
	
	public int getHubID()
	{
		return hubID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	public String getPassword()
	{
		return password;
	}
}
