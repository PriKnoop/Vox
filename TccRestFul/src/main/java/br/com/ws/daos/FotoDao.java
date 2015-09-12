package br.com.ws.daos;

import javax.persistence.EntityManager;

import pojos.Foto;
public class FotoDao extends GenericDao<Long, Foto> {
		
	public FotoDao(EntityManager entityManager){
		super(entityManager);
	}

}
