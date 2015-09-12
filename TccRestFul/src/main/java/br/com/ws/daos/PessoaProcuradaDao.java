package br.com.ws.daos;

import javax.persistence.EntityManager;

import pojos.PessoaProcurada;
public class PessoaProcuradaDao extends GenericDao<Long, PessoaProcurada> {
		
	public PessoaProcuradaDao(EntityManager entityManager){
		super(entityManager);
	}

}
