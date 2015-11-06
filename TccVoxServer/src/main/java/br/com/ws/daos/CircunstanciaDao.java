package br.com.ws.daos;

import javax.persistence.EntityManager;

import br.com.ws.pojos.Circunstancia;

public class CircunstanciaDao extends GenericDao<Long, Circunstancia> {

	public CircunstanciaDao(EntityManager entityManager) {
		super(entityManager);
	}
}