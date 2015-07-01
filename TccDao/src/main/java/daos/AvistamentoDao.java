package daos;

import javax.persistence.EntityManager;

import entitys.*;
public class AvistamentoDao extends GenericDao<Long, Avistamento> {
		
	public AvistamentoDao(EntityManager entityManager){
		super(entityManager);
	}

}