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
import br.com.ws.daos.LocalizacaoDao;
import br.com.ws.managers.WSTemplate;
import br.com.ws.pojos.Localizacao;

@Path("localizacao")
public class LocalizacaoWS extends WSTemplate {
	private EntityManager manager = factory.createEntityManager();
	private LocalizacaoDao dao = new LocalizacaoDao(manager);

	@POST
	@Path("")
	@Produces(JSON.UTF8JSON)
	@Consumes(JSON.UTF8JSON)
	public Localizacao adicionarLocalizacao(Localizacao localizacao) {
		try {
			System.out.println("Entrei em adicionar Localizacao");
			Localizacao localizacaoInserida = insert(localizacao);
			if (localizacaoInserida != null) {
				System.out.println("-----> Localizacao adicionada!");
				return localizacaoInserida;
			}
			if (localizacaoInserida == null) {
				System.out.println("-----> Localizacao não adicionada!");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Erro no adicionarLocalizacao, LocalizacaoWS");
			System.out.println("-----> Localizacao não adicionada!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(JSON.UTF8JSON)
	public Localizacao pesquisarLocalizacaoPorId(@PathParam("id") Long id) {
		Localizacao localizacaoRetornada;
		try {
			localizacaoRetornada = selectOneById(Localizacao.class, id);
			if (localizacaoRetornada != null) {
				System.out.println("-----> Localizacao encontrada!");
				return localizacaoRetornada;
			}
			if (localizacaoRetornada == null) {
				System.out.println("-----> Localizacao não encontrada!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no pesquisarLocalizacaoPorId, LocalizacaoWS");
			System.out.println("-----> Localizacao não encontrada!");
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("/pesquisarPorPessoa/{idPessoaProcurada}")
	@Produces(JSON.UTF8JSON)
	public List<Localizacao> pesquisarLocalizacaoPorPessoaProcurada(@PathParam("idPessoaProcurada") Long idPessoaProcurada) {
		List<Localizacao> listaLocalizacoesRetornadas;
		try {
			listaLocalizacoesRetornadas = dao.pesquisarLocalizacaoPorIdPessoaProcurada(idPessoaProcurada, manager);
			if (listaLocalizacoesRetornadas != null) {
				System.out.println("-----> Localizações encontradas!");
				return listaLocalizacoesRetornadas;
			}
			if (listaLocalizacoesRetornadas == null) {
				System.out.println("-----> Localizações não encontradas!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no MostrarLocalizacao, LocalizacaoWS");
			System.out.println("-----> Localizações não encontradas!");
			e.printStackTrace();
		}
		return null;
	}
}
