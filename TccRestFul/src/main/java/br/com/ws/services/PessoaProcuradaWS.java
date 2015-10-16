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
import br.com.ws.daos.PessoaProcuradaDao;
import br.com.ws.managers.WSTemplate;
import br.com.ws.pojos.PessoaProcurada;

@Path("pessoa")
public class PessoaProcuradaWS extends WSTemplate {

	private EntityManager manager = factory.createEntityManager();
	private PessoaProcuradaDao dao = new PessoaProcuradaDao(manager);

	@POST
	@Path("")
	@Produces(JSON.UTF8JSON)
	@Consumes(JSON.UTF8JSON)
	public PessoaProcurada adicionarPessoa(PessoaProcurada pessoa) {
		try {
			System.out.println("Entrei em adicionar pessoa procurada");
			PessoaProcurada pessoaInserida = insert(pessoa);
			if (pessoaInserida != null) {
				System.out.println("-----> Pessoa adicionada!");
				return pessoaInserida;
			}
		} catch (Exception e) {
			System.out.println("Erro no adicionarPessoa, pessoaProcuradaWS");
			System.out.println("-----> Pessoa não adicionada!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/{id}")
	@Produces(JSON.UTF8JSON)
	public PessoaProcurada pesquisarPessoaPorId(@PathParam("id") Long id) {
		PessoaProcurada pessoaRetornada;
		try {
			pessoaRetornada = selectOneById(PessoaProcurada.class, id);
			if (pessoaRetornada != null) {
				System.out.println("-----> Pessoa encontrada!");
				return pessoaRetornada;
			}
			if (pessoaRetornada == null) {
				System.out.println("-----> Pessoa não encontrada!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no pesquisarPessoaPorId, pessoaWS");
			System.out.println("-----> Pessoa não encontrada!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisarNomeCompleto/{nomeCompleto}")
	@Produces(JSON.UTF8JSON)
	public List<PessoaProcurada> verificarCompatibilidadePorNomeCompleto(
			@PathParam("nomeCompleto") String nomeCompleto) {
		List<PessoaProcurada> listaPessoasRetornadas;
		try {
			listaPessoasRetornadas = dao
					.verificarCompatibilidadePorNomeCompleto(nomeCompleto,
							manager);
			if (listaPessoasRetornadas != null) {
				System.out
						.println("-----> Pessoas com mesmo nome encontradas!");
				return listaPessoasRetornadas;
			}
			if (listaPessoasRetornadas == null) {
				System.out
						.println("-----> Pessoa com mesmo nome não encontradas!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no verificarCompatibilidadePorNomeCompleto, pessoaWS");
			System.out
					.println("-----> Pessoas com mesmo nome não encontradas!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisarNome/{qualquerParteDoNome}")
	@Produces(JSON.UTF8JSON)
	public List<PessoaProcurada> verificarCompatibilidadePorNomeQualquerParteDoNome(
			@PathParam("qualquerParteDoNome") String qualquerParteDoNome) {
		List<PessoaProcurada> listaPessoasRetornadas;
		try {
			listaPessoasRetornadas = dao
					.verificarCompatibilidadePorQualquerParteDoNome(
							qualquerParteDoNome, manager);
			if (listaPessoasRetornadas != null) {
				System.out
						.println("-----> Pessoas com mesmo nome encontradas!");
				return listaPessoasRetornadas;
			}
			if (listaPessoasRetornadas == null) {
				System.out
						.println("-----> Pessoa com mesmo nome não encontradas!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no verificarCompatibilidadePorNomeCompleto, pessoaWS");
			System.out
					.println("-----> Pessoas com mesmo nome não encontradas!");
			e.printStackTrace();
		}
		return null;
	}

	@POST
	@Path("/verificarCaracteristicas")
	@Consumes(JSON.UTF8JSON)
	@Produces(JSON.UTF8JSON)
	public List<PessoaProcurada> verificarCompatibilidadePorCaracteristicas(
			PessoaProcurada pessoaProcurada) {
		List<PessoaProcurada> listaPessoasRetornadas;
		try {
			listaPessoasRetornadas = dao
					.verificarCompatibilidadePorCaracteristicas(
							pessoaProcurada, manager);
			if (listaPessoasRetornadas != null) {
				System.out
						.println("-----> Pessoas com mesmas caracteristicas encontradas!");
				return listaPessoasRetornadas;
			}
			if (listaPessoasRetornadas == null) {
				System.out
						.println("-----> Pessoa com mesma caracteristicas não encontradas!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out
					.println("Erro no verificarCompatibilidadePorCaracterísticas, pessoaWS");
			System.out
					.println("-----> Pessoas com mesmas caracteristicas não encontradas!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisarPorUsuario/{idUsuario}")
	@Produces(JSON.UTF8JSON)
	public List<PessoaProcurada> pesquisarPessoasPorUsuario(
			@PathParam("idUsuario") Long idUsuario) {
		List<PessoaProcurada> listaPessoasRetornadas;
		try {
			listaPessoasRetornadas = dao.procurarPessoaPorUsuario(idUsuario,
					manager);
			if (listaPessoasRetornadas != null) {
				System.out.println("-----> Pessoas encontradas!");
				return listaPessoasRetornadas;
			}
			if (listaPessoasRetornadas == null) {
				System.out.println("-----> Pessoa não encontradas!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no pesquisarPessoasPorUsuario, pessoaWS");
			System.out.println("-----> Pessoas não encontradas!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisarPorEstado/{estado}")
	@Produces(JSON.UTF8JSON)
	public List<PessoaProcurada> pesquisarPessoasPorEstado(
			@PathParam("estado") String estado) {
		List<PessoaProcurada> listaPessoasRetornadas;
		try {
			listaPessoasRetornadas = dao.pesquisarPessoaPorEstado(estado,
					manager);
			if (listaPessoasRetornadas != null) {
				System.out.println("-----> Pessoas encontradas!");
				return listaPessoasRetornadas;
			}
			if (listaPessoasRetornadas == null) {
				System.out.println("-----> Pessoa não encontradas!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no pesquisarPessoasPorUsuario, pessoaWS");
			System.out.println("-----> Pessoas não encontradas!");
			e.printStackTrace();
		}
		return null;
	}

	@GET
	@Path("/pesquisarPorCidade/{cidade}")
	@Produces(JSON.UTF8JSON)
	public List<PessoaProcurada> pesquisarPessoasPorCidade(
			@PathParam("cidade") String cidade) {
		List<PessoaProcurada> listaPessoasRetornadas;
		try {
			listaPessoasRetornadas = dao.pesquisarPessoaPorCidade(cidade, manager);
			if (listaPessoasRetornadas != null) {
				System.out.println("-----> Pessoas encontradas!");
				return listaPessoasRetornadas;
			}
			if (listaPessoasRetornadas == null) {
				System.out.println("-----> Pessoa não encontradas!");
				return null;
			}
		} catch (Exception e) {
			manager.getTransaction().rollback();
			System.out.println("Erro no pesquisarPessoasPorUsuario, pessoaWS");
			System.out.println("-----> Pessoas não encontradas!");
			e.printStackTrace();
		}
		return null;
	}
}
