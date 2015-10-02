package br.com.ws.managers;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

public class GenericDao<PK, T> {
    private EntityManager entityManager;
 
    public GenericDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
 
    public T adicionar(T entity) {
    	entityManager.persist(entity);
		System.out.println("Salvando " + getTypeClass().getName());
        return entity;
    }
 
    public T atualizar(T entity) {
        entityManager.merge(entity);
        System.out.println("Atualizando " + getTypeClass().getName());
        return entity;
    }
 
    public void deletar(T entity) {
        entityManager.remove(entity);
        System.out.println("Deletando " + getTypeClass().getName());
    }
    
    public T encontrarPorId(Class<T> type, Long id) {

		T object =  entityManager.find(type, id);
        System.out.println("Encontrando " + getTypeClass().getName());
        return object;
    }
 
	public List<T> findAll() {
        return entityManager.createQuery(("FROM " + getTypeClass().getName()))
                .getResultList();
    }
 
    protected Class<?> getTypeClass() {
        Class<?> clazz = (Class<?>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
        return clazz;
    }
}