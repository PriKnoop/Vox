package br.com.ws.services;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("frete")
public class Transporte {

	@GET
	@Path("/{estado}")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public String getFrete (@PathParam("estado") String estado) {
		float total = 0;
		if (estado.equalsIgnoreCase("RS")) {
			total = 20;
		} else {
			return "Infelizmente não entregamos no estado solicitado!";
		}
		return "O valor do frete é R$" + Float.toString(total) + "!";
	}
}
