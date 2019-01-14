package com.mijndomein.api.config;

public class Configuration 
{
	private int configurationID;
	private int hubID;
	private String name;
	
	//Setters
	public void setConfigurationID(int newConfigurationID)
	{
		configurationID = newConfigurationID;
	}
	
	public void setHubID(int newHubID)
	{
		hubID = newHubID;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	//Getters
	
	public int getConfigurationID()
	{
		return configurationID;
	}
	
	public int getHubID()
	{
		return hubID;
	}
	
	public String getName()
	{
		return name;
	}
}
