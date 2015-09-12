package br.com.ws.daos;
import javax.persistence.EntityManager;

import pojos.Usuario;

public class UsuarioDao extends GenericDao<Long, Usuario> {
		
	public UsuarioDao(EntityManager entityManager){
		super(entityManager);
	}
	
	//Já possui save, update, delete, findById e findAll
}

