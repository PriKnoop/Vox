package daos;

import javax.persistence.EntityManager;

import entitys.*;

public class InfoContatoDao extends GenericDao<Long, InfoContato> {
		
	public InfoContatoDao(EntityManager entityManager){
		super(entityManager);
	}

}
