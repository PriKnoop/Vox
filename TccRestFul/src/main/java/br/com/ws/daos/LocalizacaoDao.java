package br.com.ws.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ws.pojos.Localizacao;

public class LocalizacaoDao extends GenericDao<Long, Localizacao> {

	public LocalizacaoDao(EntityManager entityManager) {
		super(entityManager);
	}

	public List<Localizacao> pesquisarLocalizacaoPorIdPessoaProcurada(Long id,
			EntityManager em) {
		try {
			/*Query consulta = em
					.createQuery("Select l from Circunstancia c inner join c.avistamento as a "
							+ "inner join a.pessoaProcurada as p inner join c.localizacao as l "
							+ "where p.idPessoaProcurada = :id order by l.idLocalizacao desc");*/
			Query consulta = em
					.createQuery("Select l from Localizacao as l inner join l.circunstancia as c "
							+ "inner join c.avistamento as a "
							+ "inner join a.pessoaProcurada as p "
							+ "where p.idPessoaProcurada = :id order by l.idLocalizacao desc");
			consulta.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<Localizacao> lista = consulta.getResultList();
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