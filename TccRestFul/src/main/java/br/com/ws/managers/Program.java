package br.com.ws.managers;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.ws.daos.*;
import br.com.ws.enuns.*;
import br.com.ws.pojos.*;


public class Program {

	private static EntityManagerFactory factory;

	
	public static void main(String[] args) {

		//factory = Persistence.createEntityManagerFactory("tcc");
		//EntityManager manager = factory.createEntityManager();

		//Program p = new Program();
		//UsuarioDao usuarioDao = new UsuarioDao(manager);
		
		//Usuario usuarioCadastrado = p.addUsuario("Priscila", "prik" , "123", "C:///foto.png");
		//Usuario usuarioCadastrado = usuarioDao.adicionar(usuario);
		
		/*PessoaProcurada pessoaProcuradaCadastrada = p.addPessoaProcurada("João", Genero.MASCULINO,
				TipoPessoaProcurada.DESAPARECIDO, null, Etnia.PARDA, Olhos.CASTANHOS, TipoFisico.MAGRO, CabeloCor.GRISALHO, 
				CabeloTipo.LISOS, usuarioCadastrado);
		
		p.addInfoContato("parente@email.com", TipoInfoContato.EMAIL, null, pessoaProcuradaCadastrada);
		p.addInfoContato("priscila@email.com", TipoInfoContato.EMAIL, usuarioCadastrado, null);
		
		p.addFoto("C://fotojoao.png", pessoaProcuradaCadastrada);
		
		Circunstancia circunstanciaDesaparecimento = p.addCircunstancia(new Date(), 
				"Desapareceu após sair pra procurar o cachorro", pessoaProcuradaCadastrada, null);
		p.addLocalizacao("Pela 118", "Esteio", "RS", null, null, circunstanciaDesaparecimento);
		
		Avistamento avistamentoCadastrado = p.addAvistamento(usuarioCadastrado, pessoaProcuradaCadastrada);
		Circunstancia circunstanciaAvistamento = p.addCircunstancia(new Date(), 
				"Foi visto perto do IFSul", null, avistamentoCadastrado);
		p.addLocalizacao("Perto da escola Ifsul", "Sapucaia", "RS", 9.343f, 2.394f, circunstanciaAvistamento);*/
		
		factory.close();
	}


	

	

	

	

	

	
}
