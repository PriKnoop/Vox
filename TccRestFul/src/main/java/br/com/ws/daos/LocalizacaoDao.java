package br.com.ws.daos;

import javax.persistence.EntityManager;

import pojos.Localizacao;
public class LocalizacaoDao extends GenericDao<Long, Localizacao> {
		
	public LocalizacaoDao(EntityManager entityManager){
		super(entityManager);
	}

}