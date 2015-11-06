package br.com.ws.services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.ws.configs.JSON;
import br.com.ws.daos.InfoContatoDao;
import br.com.ws.managers.WSTemplate;
import br.com.ws.pojos.InfoContato;

@Path("infoContato")
public class InfoContatoWS extends WSTemplate {

	private EntityManager manager = factory.createEntityManager();
	InfoContatoDao infoContatoDao = new InfoContatoDao(manager);

	@POST
	@Path("")
	@Produces(JSON.UTF8JSON)
	@Consumes(JSON.UTF8JSON)
	public InfoContato adicionarinfoContato(InfoContato infoContato) {
		try {
			System.out.println("Entrei em adicionar InfoContato");
			InfoContato infoContatoInserido = insert(infoContato);
			if (infoContatoInserido != null) {
				System.out.println("-----> InfoContato adicionado!");
				return infoContatoInserido;
			}
		} catch (Exception e) {
			System.out.println("Erro no adicionarInfoContato, InfoContatoWS");
			System.out.println("-----> InfoContato não adicionado!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(JSON.UTF8JSON)
	public InfoContato pesquisarinfoContatoPorId(@PathParam("id") Long id) {
		InfoContato infoContatoRetornado;
		try {
			infoContatoRetornado = selectOneById(InfoContato.class, id);
			if (infoContatoRetornado != null) {
				System.out.println("-----> InfoContato encontrado!");
				return infoContatoRetornado;
			}
			if (infoContatoRetornado == null) {
				System.out.println("-----> InfoContato não encontrado!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no MostrarInfoContato, InfoContatoWS");
			System.out.println("-----> InfoContato não encontrado!");
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/pesquisarPorUsuario/{idUsuario}")
	@Produces(JSON.UTF8JSON)
	public List<InfoContato> pesquisarinfoContatoPorUsuario(@PathParam("idUsuario") Long idUsuario) {
		List<InfoContato> listaInfoContatosRetornados;
		try {
			listaInfoContatosRetornados = infoContatoDao.pesquisarInfoContatoPorUsuario(idUsuario, manager);
			if (listaInfoContatosRetornados != null) {
				System.out.println("-----> InfoContato encontrado!");
				return listaInfoContatosRetornados;
			}
			if (listaInfoContatosRetornados == null) {
				System.out.println("-----> InfoContato não encontrado!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no MostrarInfoContato, InfoContatoWS");
			System.out.println("-----> InfoContato não encontrado!");
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/pesquisarPorPessoa/{idPessoaProcurada}")
	@Produces(JSON.UTF8JSON)
	public List<InfoContato> pesquisarInfoContatoPorPessoaProcurada(@PathParam("idPessoaProcurada") Long idPessoaProcurada) {
		List<InfoContato> listaInfoContatosRetornados;
		try {
			listaInfoContatosRetornados = infoContatoDao.pesquisarInfoContatoPorIdPessoaProcurada(idPessoaProcurada, manager);
			if (listaInfoContatosRetornados != null) {
				System.out.println("-----> InfoContato encontrado!");
				return listaInfoContatosRetornados;
			}
			if (listaInfoContatosRetornados == null) {
				System.out.println("-----> InfoContato não encontrado!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no MostrarInfoContato, InfoContatoWS");
			System.out.println("-----> InfoContato não encontrado!");
			e.printStackTrace();
		}
		return null;
	}
}
