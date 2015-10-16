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
import br.com.ws.daos.FotoDao;
import br.com.ws.managers.WSTemplate;
import br.com.ws.pojos.Foto;

@Path("foto")
public class FotoWS extends WSTemplate {
	private EntityManager manager = factory.createEntityManager();
	private FotoDao dao = new FotoDao(manager);

	@POST
	@Path("")
	@Produces(JSON.UTF8JSON)
	@Consumes(JSON.UTF8JSON)
	public Foto adicionarFoto(Foto foto) {
		try {
			System.out.println("Entrei em adicionar Foto");
			Foto fotoInserida = insert(foto);
			if (fotoInserida != null) {
				System.out.println("-----> Foto adicionada!");
				return fotoInserida;
			}
			if (fotoInserida == null) {
				System.out.println("-----> Foto não adicionada!");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Erro no adicionarFoto, FotoWS");
			System.out.println("-----> Foto não adicionada!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(JSON.UTF8JSON)
	public Foto pesquisarFotoPorId(@PathParam("id") Long id) {
		Foto fotoRetornada;
		try {
			fotoRetornada = selectOneById(Foto.class, id);
			if (fotoRetornada != null) {
				System.out.println("-----> Foto encontrada!");
				return fotoRetornada;
			}
			if (fotoRetornada == null) {
				System.out.println("-----> Foto não encontrada!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no pesquisarFotoPorId, FotoWS");
			System.out.println("-----> Foto não encontrada!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisarPorPessoa/{pessoaProcuradaId}")
	@Produces(JSON.UTF8JSON)
	public List<Foto> pesquisarFotoPorPessoaProcurada(
			@PathParam("pessoaProcuradaId") Long pessoaProcuradaId) {
		List<Foto> listaFotosRetornadas;
		try {
			listaFotosRetornadas = dao.pesquisarFotoPorIdPessoaProcurada(
					pessoaProcuradaId, manager);
			if (listaFotosRetornadas != null) {
				System.out.println("-----> Foto encontrada!");
				return listaFotosRetornadas;
			}
			if (listaFotosRetornadas == null) {
				System.out.println("-----> Foto não encontrada!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no pesquisarFotoPorId, FotoWS");
			System.out.println("-----> Foto não encontrada!");
			e.printStackTrace();
		}
		return null;
	}
}
