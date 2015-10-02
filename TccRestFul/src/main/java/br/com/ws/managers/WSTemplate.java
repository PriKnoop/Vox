package br.com.ws.managers;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class WSTemplate {

	protected static EntityManagerFactory factory = Persistence
			.createEntityManagerFactory("tcc");

	protected <T> T insert(T object) {
		EntityManager manager = factory.createEntityManager();
		try {
			manager.getTransaction().begin();
			manager.persist(object);
			System.out.println("Salvando " + getClass().getName());
			manager.getTransaction().commit();
			return object;
		} catch (Exception e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
			manager = null;
			clean();
		}
	}


	protected <T> T selectOneById(Class<T> type, Long ID) {
		SimpleEntityManager manager = new SimpleEntityManager(factory);	
		T object;
		try {
			manager.begin();
			object = manager.findById(type, ID);
			manager.commit();
			System.out.println("Mostrando " + getClass().getName());
			return object;
		} catch (Exception e) {
			manager.rollBack();
			return null;
		} finally {
			object = null;
			manager.close();
			manager = null;
			clean();
		}
	}
	
	
	public void clean() {
		System.gc();
	}
	

}
