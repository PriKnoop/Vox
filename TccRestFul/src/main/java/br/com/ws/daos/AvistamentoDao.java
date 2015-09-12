package br.com.ws.daos;

import javax.persistence.EntityManager;

import pojos.Avistamento;
public class AvistamentoDao extends GenericDao<Long, Avistamento> {
		
	public AvistamentoDao(EntityManager entityManager){
		super(entityManager);
	}

}