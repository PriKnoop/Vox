package br.com.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.pojo.Pessoa;


public class PessoaDAO {
	
	private static EntityManagerFactory factory;
	private static PessoaDAO pessoaDAO;
	
	public static PessoaDAO getInstance() {
		if(pessoaDAO==null) {
			factory = Persistence.createEntityManagerFactory("jpa-example");
			pessoaDAO = new PessoaDAO();			
		}
		return pessoaDAO;
	}
	
	
	public Pessoa carregaPessoa(Long id) {

		EntityManager manager = factory.createEntityManager();

		Pessoa pessoa = manager.find(Pessoa.class, id);

		manager.close();

		return pessoa;
	}


	public void addPessoa(String pessoa) {
		//System.out.println(pessoa);
		EntityManager manager = factory.createEntityManager();

		Pessoa p = new Pessoa("Rodrigo", "99999");
		
		manager.getTransaction().begin();
		manager.persist(p);
		manager.getTransaction().commit();

		System.out.println("ID do aluno = " + p.getId());

		manager.close();
	}
	
	

}
