package br.com.ws.services;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello")
public class HelloWorld {

	
	@GET
	@Path("/{nome}")
	@Produces(MediaType.APPLICATION_XML)
	public String sayHello(@PathParam("nome") String nome) {
		String retorno = "<echo>Web Service diz: Olá " + nome + "!</echo>";
		System.out.println("SAY HELLO FUNCIONOU!");
		return retorno;
	}
}
