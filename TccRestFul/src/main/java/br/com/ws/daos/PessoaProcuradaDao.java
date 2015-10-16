package br.com.ws.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ws.pojos.PessoaProcurada;

public class PessoaProcuradaDao extends GenericDao<Long, PessoaProcurada> {

	public PessoaProcuradaDao(EntityManager entityManager) {
		super(entityManager);
	}

	public List<PessoaProcurada> verificarCompatibilidadePorNomeCompleto(
			String nomeCompleto, EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select p from PessoaProcurada as p where p.nome like :nomeCompleto");
			consulta.setParameter("nomeCompleto", nomeCompleto);
			@SuppressWarnings("unchecked")
			List<PessoaProcurada> lista = consulta.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			if (!lista.isEmpty()) {
				System.out.println("Encontrando " + getTypeClass().getName());
				return lista;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PessoaProcurada> verificarCompatibilidadePorQualquerParteDoNome(
			String qualquerParteDoNome, EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select p from PessoaProcurada as p where p.nome like :qualquerParteDoNome");
			consulta.setParameter("qualquerParteDoNome", "%"
					+ qualquerParteDoNome + "%");
			@SuppressWarnings("unchecked")
			List<PessoaProcurada> lista = consulta.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			if (!lista.isEmpty()) {
				System.out.println("Encontrando " + getTypeClass().getName());
				return lista;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PessoaProcurada> verificarCompatibilidadePorCaracteristicas(
			PessoaProcurada pessoa, EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select p from PessoaProcurada as p where p.genero like :genero "
							+ "and p.etnia like:etnia and p.olhos like:olhos and p.cabeloCor like:cabeloCor "
							+ "and p.cabeloTipo like:cabeloTipo");
			consulta.setParameter("genero", pessoa.getGenero());
			consulta.setParameter("etnia", pessoa.getEtnia());
			consulta.setParameter("olhos", pessoa.getOlhos());
			consulta.setParameter("cabeloCor", pessoa.getCabeloCor());
			consulta.setParameter("cabeloTipo", pessoa.getCabeloTipo());
			
			System.out.println(""+pessoa.getGenero()+ pessoa.getEtnia()+ pessoa.getOlhos()+ pessoa.getCabeloCor()+ pessoa.getCabeloTipo());

			@SuppressWarnings("unchecked")
			List<PessoaProcurada> lista = consulta.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			if (!lista.isEmpty()) {
				System.out.println("Encontrando " + getTypeClass().getName());
				return lista;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PessoaProcurada> procurarPessoaPorUsuario(Long idUsuario,
			EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select p from PessoaProcurada as p where p.usuario.idUsuario = :idUsuario "
							+ "order by p.idPessoaProcurada desc");
			consulta.setParameter("idUsuario", idUsuario);

			@SuppressWarnings("unchecked")
			List<PessoaProcurada> lista = consulta.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			if (!lista.isEmpty()) {
				System.out.println("Encontrando " + getTypeClass().getName());
				return lista;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PessoaProcurada> pesquisarPessoaPorEstado(String estado,
			EntityManager em) {
		try {
			/*Query consulta = em
					.createQuery("Select p from Circunstancia c inner join c.avistamento as a "
							+ "inner join a.pessoaProcurada as p inner join c.localizacao as l "
							+ "where l.uf like :estado order by l.idLocalizacao desc");*/
			Query consulta = em
					.createQuery("Select p from Localizacao as l inner join l.circunstancia as c "
							+ "inner join c.avistamento as a "
							+ "inner join a.pessoaProcurada as p "
							+ "where l.uf like :estado order by l.idLocalizacao desc");
			consulta.setParameter("estado", estado);
			@SuppressWarnings("unchecked")
			List<PessoaProcurada> lista = consulta.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			if (!lista.isEmpty()) {
				System.out.println("Encontrando " + getTypeClass().getName());
				return lista;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<PessoaProcurada> pesquisarPessoaPorCidade(String cidade,
			EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select p from Localizacao as l inner join l.circunstancia as c "
							+ "inner join c.avistamento as a "
							+ "inner join a.pessoaProcurada as p "
							+ "where l.cidade like :cidade order by l.idLocalizacao desc");
			consulta.setParameter("cidade", cidade);
			@SuppressWarnings("unchecked")
			List<PessoaProcurada> lista = consulta.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			if (!lista.isEmpty()) {
				System.out.println("Encontrando " + getTypeClass().getName());
				return lista;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
