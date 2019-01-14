package com.mijndomein.api.config;

public class Configuration 
{
	private int configurationID;
	private int hubID;
	
	//Setters
	public void setConfigurationID(int newConfigurationID)
	{
		configurationID = newConfigurationID;
	}
	
	public void setHubID(int newHubID)
	{
		hubID = newHubID;
	}
	
	//Getters
	
	public int getConfigurationID()
	{
		return configurationID;
	}
	
	public int getHubId()
	{
		return hubID;
	}
}
