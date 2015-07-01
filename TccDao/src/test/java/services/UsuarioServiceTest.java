package services;

import static org.junit.Assert.*;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import managers.SimpleEntityManager;

import org.junit.Test;

import daos.UsuarioDao;
import entitys.Usuario;
/**
 * @author Priscila
 * Junho, 2015
 * Classe de testes da UsuarioDao;
 */
public class UsuarioServiceTest {
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("tcc");
	private SimpleEntityManager simpleEntityManager = new SimpleEntityManager(factory);
	private UsuarioService usuarioService = new UsuarioService();
	
    /**
     * Teste que adiciona um usuário;
     */
	@Test
	public void adicionarUsuario() {
		Usuario usuario = new Usuario("TesteAdicionar1", "t1" , "123", "C:///foto.png");
		Usuario usuarioCadastrado = usuarioService.adicionar(usuario);
		
        Usuario result = (Usuario) usuarioService.encontrarPorId(usuarioCadastrado.getIdUsuario());
		assertNotNull(result);
	}
	
	/**
	 * Teste que atualiza um usuário;
	 */
	@Test 
	public void atualizarUsuario(){
		Usuario usuarioCadastrado = new Usuario("TesteAtualizar", "t1" , "123", "C:///foto.png");
		usuarioService.adicionar(usuarioCadastrado);
		
        Usuario usuarioAtualizado = (Usuario) usuarioService.encontrarPorId(usuarioCadastrado.getIdUsuario());
        usuarioAtualizado.setNome("TesteUsuarioAtualizado");
        usuarioService.atualizar(usuarioAtualizado);
		
        Usuario result = (Usuario) usuarioService.encontrarPorId(usuarioCadastrado.getIdUsuario());		
		assertEquals(result.getNome(), usuarioAtualizado.getNome());
	}	
	@Test
	/**
	 * Teste que deleta usuário
	 */
	public void deletarUsuário(){
		Usuario usuarioCadastrado = new Usuario("TesteUsuario1", "t1" , "123", "C:///foto.png");
		usuarioService.adicionar(usuarioCadastrado);
		
        Usuario usuarioDeletado = (Usuario) usuarioService.encontrarPorId(usuarioCadastrado.getIdUsuario());
        usuarioService.deletar(usuarioDeletado);
		
        Usuario result = (Usuario) usuarioService.encontrarPorId(usuarioCadastrado.getIdUsuario());
		
		assertNull(result);
	}
	@Test
	/**
	 * Teste que encontra usuário
	 */
	public void encontrarUsuario(){
		Usuario usuarioCadastrado = new Usuario("TesteUsuario1", "t1" , "123", "C:///foto.png");
		usuarioCadastrado = usuarioService.adicionar(usuarioCadastrado);
		
        Usuario result = (Usuario) usuarioService.encontrarPorId(usuarioCadastrado.getIdUsuario());
		
		assertNotNull(result);
	}
}


