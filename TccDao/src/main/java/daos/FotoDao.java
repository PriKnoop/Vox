package daos;

import javax.persistence.EntityManager;

import entitys.*;

public class FotoDao extends GenericDao<Long, Foto> {
		
	public FotoDao(EntityManager entityManager){
		super(entityManager);
	}

}
