package br.com.ws.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ws.pojos.InfoContato;

public class InfoContatoDao extends GenericDao<Long, InfoContato> {

	public InfoContatoDao(EntityManager entityManager) {
		super(entityManager);
	}

	public List<InfoContato> pesquisarInfoContatoPorIdPessoaProcurada(Long id,
			EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select i from InfoContato as i where i.pessoaProcurada.idPessoaProcurada = :id "
							+ "order by i.idInfoContato desc");
			consulta.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<InfoContato> lista = consulta.getResultList();
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

	public List<InfoContato> pesquisarInfoContatoPorUsuario(Long idUsuario,
			EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select i from InfoContato as i where i.usuario.idUsuario = :idUsuario "
							+ "order by i.idInfoContato desc");
			consulta.setParameter("idUsuario", idUsuario);

			@SuppressWarnings("unchecked")
			List<InfoContato> lista = consulta.getResultList();
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
