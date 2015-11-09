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
import br.com.ws.daos.AvistamentoDao;
import br.com.ws.managers.WSTemplate;
import br.com.ws.pojos.Avistamento;

@Path("avistamento")
public class AvistamentoWS extends WSTemplate {
	private EntityManager manager = factory.createEntityManager();
	private AvistamentoDao dao = new AvistamentoDao(manager);

	@POST
	@Path("")
	@Produces(JSON.UTF8JSON)
	@Consumes(JSON.UTF8JSON)
	public Avistamento adicionarAvistamento(Avistamento avistamento) {
		try {
			System.out.println("Entrei em adicionar avistamento");
			Avistamento avistamentoInserido = insert(avistamento);
			if (avistamentoInserido != null) {
				System.out.println("-----> Avistamento adicionado!");
				return avistamentoInserido;
			}
			if (avistamentoInserido == null) {
				System.out.println("-----> Avistamento não adicionado!");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Erro no adicionarAvistamento, avistamentoWS");
			System.out.println("-----> avistamento não adicionado!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(JSON.UTF8JSON)
	public Avistamento pesquisarAvistamentoPorId(@PathParam("id") Long id) {
		Avistamento avistamentoRetornado;
		try {
			avistamentoRetornado = selectOneById(Avistamento.class, id);
			if (avistamentoRetornado != null) {
				System.out.println("-----> Avistamento encontrado!");
				return avistamentoRetornado;
			}
			if (avistamentoRetornado == null) {
				System.out.println("-----> Avistamento não encontrado!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no pesquisarAvistamentoPorId, avistamentoWS");
			System.out.println("-----> Avistamento não encontrado!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisaPorPessoa/{idPessoaProcurada}")
	@Produces(JSON.UTF8JSON)
	public List<Avistamento> pesquisarAvistamentoPorIdPessoaProcurada(
			@PathParam("idPessoaProcurada") Long idPessoaProcurada) {
		List<Avistamento> listaAvistamentosRetornados;
		try {
			listaAvistamentosRetornados = dao
					.pesquisarAvistamentoPorIdPessoaProcurada(
							idPessoaProcurada, manager);
			if (listaAvistamentosRetornados != null) {
				System.out.println("-----> Avistamento encontrado!");
				return listaAvistamentosRetornados;
			}
			if (listaAvistamentosRetornados == null) {
				System.out.println("-----> Avistamento não encontrado!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no pesquisarAvistamentoPorId, avistamentoWS");
			System.out.println("-----> Avistamento não encontrado!");
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/pesquisaUltimoAvistamentoPorPessoa/{idPessoaProcurada}")
	@Produces(JSON.UTF8JSON)
	public Avistamento pesquisarUltimoAvistamentoPorIdPessoaProcurada(
			@PathParam("idPessoaProcurada") Long idPessoaProcurada) {
		List<Avistamento> listaAvistamentosRetornados;
		try {
			listaAvistamentosRetornados = dao
					.pesquisarAvistamentoPorIdPessoaProcurada(
							idPessoaProcurada, manager);
			if (listaAvistamentosRetornados != null) {
				System.out.println("-----> Avistamento encontrado!");
				return listaAvistamentosRetornados.get(0);
			}
			if (listaAvistamentosRetornados == null) {
				System.out.println("-----> Avistamento não encontrado!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no pesquisarAvistamentoPorId, avistamentoWS");
			System.out.println("-----> Avistamento não encontrado!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisaPorUsuario/{idUsuario}")
	@Produces(JSON.UTF8JSON)
	public List<Avistamento> pesquisarAvistamentoPorIdUsuario(
			@PathParam("idUsuario") Long idUsuario) {
		List<Avistamento> listaAvistamentosRetornados;
		try {
			listaAvistamentosRetornados = dao.pesquisarAvistamentoPorUsuario(
					idUsuario, manager);
			if (listaAvistamentosRetornados != null) {
				System.out.println("-----> Avistamento encontrado!");
				return listaAvistamentosRetornados;
			}
			if (listaAvistamentosRetornados == null) {
				System.out.println("-----> Avistamento não encontrado!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no pesquisarAvistamentoPorId, avistamentoWS");
			System.out.println("-----> Avistamento não encontrado!");
			e.printStackTrace();
		}
		return null;
	}
}
