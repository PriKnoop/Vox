package br.com.ws.managers;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * @author Priscila
 * 
 *         Junho, 2015
 * 
 *         Essa classe faz o gerenciamento dos comandos da Entity;
 * 
 */
public class SimpleEntityManager {
	private EntityManager entityManager;
	private EntityManagerFactory factory;

	public SimpleEntityManager(EntityManagerFactory factory) {
		this.factory = factory;
		this.entityManager = factory.createEntityManager();
	}

	public SimpleEntityManager(String persistenceUnitName) {
		factory = Persistence.createEntityManagerFactory(persistenceUnitName);
		this.entityManager = factory.createEntityManager();
	}

	public void begin() {
		entityManager.getTransaction().begin();
	}

	public void commit() {
		entityManager.getTransaction().commit();
	}

	public void close() {
		entityManager.close();
	}

	public void commitAndClose() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	public void rollBack() {
		entityManager.getTransaction().rollback();
	}

	public <T> T persist(T object) {
    	entityManager.persist(object);
        return object;
    }
 
    public <T> T merge(T object) {
        entityManager.merge(object);
        return object;
    }
 
    public <T> void remove(T object) {
        entityManager.remove(object);
    }
    
    public <T> T findById(Class<T> type, Long id) {

		T object =  entityManager.find(type, id);
        return object;
    }
 
	@SuppressWarnings("unchecked")
	public <T> List<T> FindAll() {
        Query consulta =  entityManager.createQuery("select * from " + getTypeClass().getName());
		return consulta.getResultList();
                
    }
 
    private Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
}
