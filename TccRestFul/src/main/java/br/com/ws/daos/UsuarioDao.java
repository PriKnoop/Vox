package br.com.ws.daos;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import pojos.Usuario;

public class UsuarioDao extends GenericDao<Long, Usuario> {

	private static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("tcc");

	public UsuarioDao(EntityManager entityManager) {
		super(entityManager);
	}

	public Usuario verificarSeLoginExiste(String login, EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select u from Usuario as u where u.login like :login");
			consulta.setParameter("login", login);
			@SuppressWarnings("unchecked")
			List<Usuario> lista = consulta.getResultList();
			if (lista.isEmpty()) {
				return null;
			}
			if (!lista.isEmpty()) {
				Usuario usuarioRetornado = lista.get(0);
				System.out.println("Encontrando " + getTypeClass().getName());
				return usuarioRetornado;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Usuario autenticarLoginSenha(String login, String senha, EntityManager em) {
		try {
			Query consulta = em
					.createQuery("Select u from Usuario as u where u.login like :login AND u.senha like :senha");
			consulta.setParameter("login", login);
			consulta.setParameter("senha", senha);
			@SuppressWarnings("unchecked")
			List<Usuario> lista = consulta.getResultList();
			if (!lista.isEmpty()) {
				Usuario usuarioRetornado = lista.get(0);
				System.out.println("Encontrando " + getTypeClass().getName());
				return usuarioRetornado;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void clean() {
		System.gc();
	}

	// Já possui save, update, delete, findById e findAll
}
