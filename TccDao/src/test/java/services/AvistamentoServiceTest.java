package services;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import daos.PessoaProcuradaDao;
import daos.UsuarioDao;
import entitys.PessoaProcurada;
import entitys.Usuario;
import enums.*;

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

	@Test
	public void adicionarAvistamentoEDeletar() {
		Usuario usuario = new Usuario("TesteAvistamento1", "t1" , "123", "C:///foto.png");
		Usuario usuarioCadastrado = usuarioDao.adicionar(usuario);
		PessoaProcurada pessoaProcurada = new PessoaProcurada("João", Genero.MASCULINO,
				TipoPessoaProcurada.DESAPARECIDO, null, Etnia.PARDA, Olhos.CASTANHOS, TipoFisico.MAGRO, CabeloCor.GRISALHO, 
				CabeloTipo.LISOS, usuarioCadastrado);
		PessoaProcurada pessoaProcuradaCadastrada = pessoaProcuradaDao.adicionar(pessoaProcurada);
		
	}

}
