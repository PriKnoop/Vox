package daos;

import javax.persistence.EntityManager;

import entitys.PessoaProcurada;

public class PessoaProcuradaDao extends GenericDao<Long, PessoaProcurada> {
		
	public PessoaProcuradaDao(EntityManager entityManager){
		super(entityManager);
	}

}
