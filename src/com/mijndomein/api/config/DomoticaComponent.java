package com.mijndomein.api.config;

public class DomoticaComponent 
{
	private int componentID;
	private int hubID;
	private int clusterID;
	private String name;
	private int type;
	private int port;
	private String status;
	    
	//Setters
    public void setComponentID(int newComponentID) {
    	componentID = newComponentID;
    }
    
    public void setHubID(int newHubID) {
    	hubID = newHubID;
    }
    
    public void setClusterID(int newClusterID) {
    	clusterID = newClusterID;
    }
    
    public void setName(String newName) {
    	name = newName;
    }
    
    public void setType(int newType) {
        type = newType;
    }
    
    public void setPort(int newPort) {
        port = newPort;
    }
    
    public void setStatus(String newStatus) {
    	status = newStatus;
    }
    
    //Getters
    public int getComponentID() {
        return componentID;
    }
    
    public int getHubID() {
    	return hubID;
    }
    
    public int getClusterID() {
    	return clusterID;
    }
    
    public String getName() {
    	return name;
    }
    
    public int getType() {
        return type;
    }
    
    public int getPort() {
        return port;
    }
    
    public String getStatus() {
    	return status;
    }
}
