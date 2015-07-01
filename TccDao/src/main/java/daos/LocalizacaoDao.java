package daos;

import javax.persistence.EntityManager;

import entitys.*;

public class LocalizacaoDao extends GenericDao<Long, Localizacao> {
		
	public LocalizacaoDao(EntityManager entityManager){
		super(entityManager);
	}

}