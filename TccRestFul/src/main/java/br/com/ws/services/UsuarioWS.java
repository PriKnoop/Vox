package br.com.ws.services;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.json.JSONObject;

import pojos.Usuario;
import br.com.ws.configs.JSON;
import br.com.ws.daos.UsuarioDao;
import br.com.ws.managers.WSTemplate;

@Path("usuario")
public class UsuarioWS extends WSTemplate {

	private EntityManager manager = factory.createEntityManager();

	@GET
	@Path("/adicionar/{login}/{senha}")
	@Produces(JSON.UTF8JSON)
	@Consumes(JSON.UTF8JSON)
	public Usuario adicionarUsuario(@PathParam("login") String login, @PathParam("senha") String senha) {

		try {
			Usuario usuario = new Usuario();
			usuario.setLogin(login);
			usuario.setSenha(senha);
			System.out.println("Entrei em adicionar usuario");
			Usuario usuarioInserido = insert(usuario);
			if (usuarioInserido != null) {
				System.out.println("-----> Usuário adicionado!");
				return usuario;
			}
		} catch (Exception e) {
			System.out.println("Erro no adicionarUsuario, usuarioWS");
			System.out.println("-----> Usuário não adicionado!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisar/{id}")
	@Produces(JSON.UTF8JSON)
	public Usuario pesquisarUsuarioPorId(@PathParam("id") Long id) {
		JSONObject json = null;
		// EntityManager manager = factory.createEntityManager();
		Usuario usuario;
		try {
			usuario = selectOneById(Usuario.class, id);
			if (usuario != null) {
				System.out.println("-----> Usuário encontrado!");
				return usuario;
			}
			if (usuario == null) {
				System.out.println("-----> Usuário não encontrado!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no MostrarUsuario, usuarioWS");
			System.out.println("-----> Usuário não encontrado!");
			e.printStackTrace();
		}
		return null;
	}

	public void clean() {
		System.gc();
	}

	@GET
	@Path("/autenticarLoginSenha/{login}/{senha}")
	@Produces(JSON.UTF8JSON)
	public Usuario autenticarUsuario(@PathParam("login") String login,
			@PathParam("senha") String senha) {
		JSONObject json = null;
		EntityManager manager = factory.createEntityManager();
		UsuarioDao usuarioDao = new UsuarioDao(manager);
		Usuario usuario;
		try {
			manager.getTransaction().begin();
			usuario = usuarioDao.autenticarLoginSenha(login, senha, manager);
			manager.getTransaction().commit();
			if (usuario != null) {
				System.out.println("-----> Usuário autenticado!");
				return usuario;
			}
			if (usuario == null) {
				System.out.println("-----> Usuário não autenticado!");
				return null;
			}

		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no autenticarUsuario, usuarioWS");
			System.out.println("Usuário não autenticado!");
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
			manager = null;
			clean();
		}
		return null;
	}

	@GET
	@Path("/verificarLogin/{login}")
	@Produces(JSON.UTF8JSON)
	public Usuario verificarLogin(@PathParam("login") String login) {
		EntityManager manager = factory.createEntityManager();
		UsuarioDao usuarioDao = new UsuarioDao(manager);
		Usuario usuario = null;

		try {
			manager.getTransaction().begin();
			usuario = usuarioDao.verificarSeLoginExiste(login, manager);
			manager.getTransaction().commit();
			if (usuario != null) {
				System.out.println("-----> Usuário já utilizado!");
				return usuario;
			}
			if (usuario == null) {
				System.out.println("-----> Usuário ainda não utilizado!");
				return null;

			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no autenticarUsuario, usuarioWS");
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
			manager = null;
			clean();
		}
		return usuario;
	}

	/*
	 * @POST
	 * 
	 * @Path("/{usuario}") //@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	 * 
	 * @Produces({"application/json"}) public String
	 * cadastrarUsuario(@PathParam("usuario") Usuario usuario) { //Usuario
	 * retorno = usuarioDAO.adicionar(usuario); String retornoString =
	 * "<echo>Web Service diz: Olá " + usuario.getIdUsuario() + "!</echo>";
	 * JsonObject usuarioJSON = Json.createObjectBuilder() .add("idUsuario",
	 * usuario.getIdUsuario()) .add("login", usuario.getLogin()) .add("nome",
	 * usuario.getNome()) .add("senha", usuario.getSenha()) .add("fotoPessoal",
	 * usuario.getFotoPessoal()) .build(); return retornoString; }
	 */

}
