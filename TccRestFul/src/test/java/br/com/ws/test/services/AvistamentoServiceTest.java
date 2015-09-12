package br.com.ws.test.services;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import pojos.*;
import br.com.ws.daos.*;
import br.com.ws.enuns.*;

/**
 * @author Priscila
 * Junho, 2015
 * Classe de testes da AvistamentoDao;
 */

public class AvistamentoServiceTest {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("tcc");
	EntityManager manager = factory.createEntityManager();
	UsuarioDao usuarioDao = new UsuarioDao(manager);
	PessoaProcuradaDao pessoaProcuradaDao = new PessoaProcuradaDao(manager);

	/*@Test
	public void adicionarAvistamentoEDeletar() {
		Usuario usuario = new Usuario("TesteAvistamento1", "t1" , "123", "C:///foto.png");
		Usuario usuarioCadastrado = usuarioDao.adicionar(usuario);
		PessoaProcurada pessoaProcurada = new PessoaProcurada("João", Genero.MASCULINO,
				TipoPessoaProcurada.DESAPARECIDO, null, Etnia.PARDA, Olhos.CASTANHOS, TipoFisico.MAGRO, CabeloCor.GRISALHO, 
				CabeloTipo.LISOS, usuarioCadastrado);
		PessoaProcurada pessoaProcuradaCadastrada = pessoaProcuradaDao.adicionar(pessoaProcurada);
		
	}*/

}
