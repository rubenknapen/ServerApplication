package com.mijndomein.api.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JSlider;
//import com.fazecast.jSerialComm.*;

@ApplicationPath("restservices")

public class ApiService extends ResourceConfig
{	
    public ApiService() 
    {
        packages("com.fasterxml.jackson.jaxrs.json");
        packages("com.mijndomein.api.config");
        startConnectionThread();
    }
    
    private void startConnectionThread()
    {
    	System.out.println("Setting up seperated Thread to setup connections");
    	new ConnectionCheck("MainConnectionThread");  	
    }
}