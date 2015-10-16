package br.com.ws.services;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import br.com.ws.configs.JSON;
import br.com.ws.daos.CircunstanciaDao;
import br.com.ws.managers.WSTemplate;
import br.com.ws.pojos.Circunstancia;

@Path("circunstancia")
public class CircunstanciaWS extends WSTemplate {
	private EntityManager manager = factory.createEntityManager();
	private CircunstanciaDao dao = new CircunstanciaDao(manager);

	@POST
	@Path("")
	@Produces(JSON.UTF8JSON)
	@Consumes(JSON.UTF8JSON)
	public Circunstancia adicionarCircunstancia(Circunstancia circunstancia) {
		try {
			System.out.println("Entrei em adicionar circunstancia");
			Circunstancia circunstanciaInserida = insert(circunstancia);
			if (circunstanciaInserida != null) {
				System.out.println("-----> Circunstancia adicionada!");
				return circunstanciaInserida;
			}
			if (circunstanciaInserida == null) {
				System.out.println("-----> Circunstancia não adicionada!");
				return null;
			}
		} catch (Exception e) {
			System.out
					.println("Erro no adicionarCircunstancia, circunstaciaWS");
			System.out.println("-----> Circunstancia não adicionada!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(JSON.UTF8JSON)
	public Circunstancia pesquisarCircunstanciaPorId(@PathParam("id") Long id) {
		Circunstancia circunstanciaRetornada;
		try {
			circunstanciaRetornada = selectOneById(Circunstancia.class, id);
			if (circunstanciaRetornada != null) {
				System.out.println("-----> Circunstancia encontrada!");
				return circunstanciaRetornada;
			}
			if (circunstanciaRetornada == null) {
				System.out.println("-----> Circunstancia não encontrada!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no pesquisarCircunstanciaPorId, circunstanciaWS");
			System.out.println("-----> Circunstancia não encontrada!");
			e.printStackTrace();
		}
		return null;
	}
}