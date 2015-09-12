package br.com.ws.daos;

import javax.persistence.EntityManager;

import pojos.InfoContato;
public class InfoContatoDao extends GenericDao<Long, InfoContato> {
		
	public InfoContatoDao(EntityManager entityManager){
		super(entityManager);
	}

}
