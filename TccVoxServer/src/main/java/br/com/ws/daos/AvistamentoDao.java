package br.com.ws.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ws.pojos.Avistamento;

public class AvistamentoDao extends GenericDao<Long, Avistamento> {

	public AvistamentoDao(EntityManager entityManager) {
		super(entityManager);
	}

	public List<Avistamento> pesquisarAvistamentoPorIdPessoaProcurada(Long idPessoaProcurada,
			EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select a from Avistamento as a where a.pessoaProcurada.idPessoaProcurada = :idPessoaProcurada "
							+ "order by a.idAvistamento desc");
//			Query consulta = em
//					.createQuery("Select a from Circunstancia c inner join c.avistamento as a "
//							+ "inner join a.pessoaProcurada as p inner join c.localizacao as l "
//							+ "where p.idPessoaProcurada = :id order by a.idAvistamento desc");
			consulta.setParameter("idPessoaProcurada", idPessoaProcurada);
			@SuppressWarnings("unchecked")
			List<Avistamento> lista = consulta.getResultList();
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
	

	public List<Avistamento> pesquisarAvistamentoPorUsuario(Long idUsuario,
			EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select a from Avistamento as a where a.usuario.idUsuario = :idUsuario "
							+ "order by a.idAvistamento desc");
			consulta.setParameter("idUsuario", idUsuario);

			@SuppressWarnings("unchecked")
			List<Avistamento> lista = consulta.getResultList();
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