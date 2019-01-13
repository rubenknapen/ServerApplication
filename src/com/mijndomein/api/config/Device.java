package com.mijndomein.api.config;

public class Device 
{
	private int id;
	private int type;
	private int port;
	    
    public void setId(int newId) {
        id = newId;
    }
    public void setType(int newType) {
        type = newType;
    }
    
    public void setPort(int newPort) {
        port = newPort;
    }
    
    public int getId() {
        return id;
    }
    public int getType() {
        return type;
    }
    
    public int getPort() {
        return port;
    }
}
