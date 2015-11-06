package br.com.ws.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.ws.pojos.Foto;

public class FotoDao extends GenericDao<Long, Foto> {

	public FotoDao(EntityManager entityManager) {
		super(entityManager);
	}

	public List<Foto> pesquisarFotoPorIdPessoaProcurada(Long id,
			EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select f from Foto as f where f.pessoaProcurada.idPessoaProcurada = :id");
			consulta.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<Foto> lista = consulta.getResultList();
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
