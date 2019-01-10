package com.mijndomein.api.config;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("restservices")
public class ApiService extends ResourceConfig {
	
    public ApiService() {
        packages("com.fasterxml.jackson.jaxrs.json");
        packages("com.mijndomein.api.config");
    }
}