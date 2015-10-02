package br.com.ws.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import pojos.Planet;

@Path("planet")
public class PlanetWS {
	

	    @GET
	    @Produces(MediaType.APPLICATION_JSON)
	    public Planet getPlanet() {
	        Planet p = new Planet();
	        p.id = 1;
	        p.name = "Earth";
	        p.radius = 1.0;

	        return p;
	    }

	    @POST
	    @Consumes(MediaType.APPLICATION_JSON)
	    public void setPlanet(Planet p) {
	        System.out.println("setPlanet " + p.name);
	    }

	
}
