package com.mijndomein.api.config;

public class DomoticaCluster 
{
	private int clusterID;
	private int hubID;
	private String name;
	
	public void setClusterID(int newClusterID)
	{
		clusterID = newClusterID;
	}
	
	public void setHubID(int newHubID)
	{
		hubID = newHubID;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public int getClusterID()
	{
		return clusterID;
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
