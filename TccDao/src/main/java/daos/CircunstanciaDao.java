package daos;

import javax.persistence.EntityManager;

import entitys.*;

public class CircunstanciaDao extends GenericDao<Long, Circunstancia> {
		
	public CircunstanciaDao(EntityManager entityManager){
		super(entityManager);
	}

}