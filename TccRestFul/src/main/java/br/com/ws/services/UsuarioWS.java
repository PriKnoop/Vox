package br.com.ws.services;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.ws.daos.UsuarioDao;
import br.com.ws.managers.SimpleEntityManager;
import pojos.Usuario;

@Path("usuario")
public class UsuarioWS {
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("tcc");
    private SimpleEntityManager simpleEntityManager = new SimpleEntityManager(factory);
    private UsuarioDao usuarioDAO = new UsuarioDao(simpleEntityManager.getEntityManager());
    
	@GET
	@Path("/{usuario}")
	@Produces(MediaType.APPLICATION_XML)
	//@Produces({"application/json"})
	public String mostrarUsuario(@PathParam("usuario") String usuario) {
		//Usuario retorno = usuarioDAO.adicionar(usuario);
		String retornoString = "<echo>Web Service diz: Olá " + usuario + "!</echo>";
		/*JsonObject usuarioJSON = Json.createObjectBuilder()
		        .add("idUsuario", usuario.getIdUsuario())
		        .add("login", usuario.getLogin())
		        .add("nome", usuario.getNome())
		        .add("senha", usuario.getSenha())
		        .add("fotoPessoal", usuario.getFotoPessoal())
		        .build();*/
		return retornoString;
	}
	
	@POST
	@Path("/{usuario}")
	//@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	@Produces({"application/json"})
	public String cadastrarUsuario(@PathParam("usuario") Usuario usuario) {
		//Usuario retorno = usuarioDAO.adicionar(usuario);
		String retornoString = "<echo>Web Service diz: Olá " + usuario.getIdUsuario() + "!</echo>";
		/*JsonObject usuarioJSON = Json.createObjectBuilder()
		        .add("idUsuario", usuario.getIdUsuario())
		        .add("login", usuario.getLogin())
		        .add("nome", usuario.getNome())
		        .add("senha", usuario.getSenha())
		        .add("fotoPessoal", usuario.getFotoPessoal())
		        .build();*/
		return retornoString;
	}
	

}
