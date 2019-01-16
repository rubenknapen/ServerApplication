package com.mijndomein.api.config;

public class DomoticaComponent 
{
	private int hubID;
	private int clusterID;
	private String name;
	private String type;
	private int port;
	private String status;
	private int componentTypeID;
	private String value;
	    
	//Setters
    public void setHubID(int newHubID) {
    	hubID = newHubID;
    }
    
    public void setClusterID(int newClusterID) {
    	clusterID = newClusterID;
    }
    
    public void setName(String newName) {
    	name = newName;
    }
    
    public void setType(String newType) {
        type = newType;
    }
    
    public void setPort(int newPort) {
        port = newPort;
    }
    
    public void setStatus(String newStatus) {
    	status = newStatus;
    }
    
    public void setComponentTypeID(int newComponentTypeID) {
    	componentTypeID = newComponentTypeID;
    }
    
    public void setValue(String newValue) {
    	value = newValue;
    }
    
    //Getters  
    public int getHubID() {
    	return hubID;
    }
    
    public int getClusterID() {
    	return clusterID;
    }
    
    public String getName() {
    	return name;
    }
    
    public String getType() {
        return type;
    }
    
    public int getPort() {
        return port;
    }
    
    public String getStatus() {
    	return status;
    }
    
    public int getComponentTypeID() {
        return componentTypeID;
    }
    
    public String getValue() {
    	return value;
    }
}
