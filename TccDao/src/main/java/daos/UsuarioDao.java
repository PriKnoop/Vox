package daos;
import javax.persistence.EntityManager;

import entitys.Usuario;

public class UsuarioDao extends GenericDao<Long, Usuario> {
		
	public UsuarioDao(EntityManager entityManager){
		super(entityManager);
	}
	
	//J� possui save, update, delete, findById e findAll
}

