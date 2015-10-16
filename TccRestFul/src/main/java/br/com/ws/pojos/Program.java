package br.com.ws.pojos;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.ws.daos.UsuarioDao;
import br.com.ws.enuns.CabeloCor;
import br.com.ws.enuns.CabeloTipo;
import br.com.ws.enuns.Etnia;
import br.com.ws.enuns.Genero;
import br.com.ws.enuns.Olhos;
import br.com.ws.enuns.TipoFisico;
import br.com.ws.enuns.TipoInfoContato;
import br.com.ws.enuns.TipoPessoaProcurada;

public class Program {

	private static EntityManagerFactory factory;

	public static void main(String[] args) {

		/*
		 * factory = Persistence.createEntityManagerFactory("tcc");
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * Program p = new Program(); UsuarioDao usuarioDao = new
		 * UsuarioDao(manager);
		 * 
		 * Usuario usuario = new Usuario("Priscila", "prik" , "123",
		 * "C:///foto.png"); Usuario usuarioCadastrado =
		 * usuarioDao.adicionar(usuario);
		 * 
		 * PessoaProcurada pessoaProcuradaCadastrada =
		 * p.addPessoaProcurada("João", Genero.MASCULINO,
		 * TipoPessoaProcurada.DESAPARECIDO, null, Etnia.PARDA, Olhos.CASTANHOS,
		 * TipoFisico.MAGRO, CabeloCor.GRISALHO, CabeloTipo.LISOS,
		 * usuarioCadastrado);
		 * 
		 * p.addInfoContato("parente@email.com", TipoInfoContato.EMAIL, null,
		 * pessoaProcuradaCadastrada); p.addInfoContato("priscila@email.com",
		 * TipoInfoContato.EMAIL, usuarioCadastrado, null);
		 * 
		 * p.addFoto("C://fotojoao.png", pessoaProcuradaCadastrada);
		 * 
		 * Circunstancia circunstanciaDesaparecimento = p.addCircunstancia(new
		 * Date(), "Desapareceu após sair pra procurar o cachorro",
		 * pessoaProcuradaCadastrada, null); p.addLocalizacao("Pela 118",
		 * "Esteio", "RS", null, null, circunstanciaDesaparecimento);
		 * 
		 * Avistamento avistamentoCadastrado =
		 * p.addAvistamento(usuarioCadastrado, pessoaProcuradaCadastrada);
		 * Circunstancia circunstanciaAvistamento = p.addCircunstancia(new
		 * Date(), "Foi visto perto do IFSul", null, avistamentoCadastrado);
		 * p.addLocalizacao("Perto da escola Ifsul", "Sapucaia", "RS", 9.343f,
		 * 2.394f, circunstanciaAvistamento);
		 * 
		 * factory.close(); }
		 * 
		 * private Usuario addUsuario(String nome, String login, String senha,
		 * String fotoPessoal) { Usuario usuario = new Usuario(nome, login,
		 * senha, fotoPessoal);
		 * 
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * manager.getTransaction().begin(); manager.persist(usuario);
		 * manager.getTransaction().commit();
		 * 
		 * System.out.println("ID do usuario = " + usuario.getIdUsuario());
		 * 
		 * manager.close(); return usuario; }
		 * 
		 * private PessoaProcurada addPessoaProcurada(String nome, Genero
		 * genero, TipoPessoaProcurada tipo, Date dataNascimento, Etnia etnia,
		 * Olhos olhos, TipoFisico tipoFisico, CabeloCor cabeloCor, CabeloTipo
		 * CabeloTipo, Usuario usuario) { PessoaProcurada pessoaProcurada = new
		 * PessoaProcurada(nome, genero, tipo, dataNascimento, etnia, olhos,
		 * tipoFisico, cabeloCor, CabeloTipo, usuario);
		 * 
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * manager.getTransaction().begin(); manager.persist(pessoaProcurada);
		 * manager.getTransaction().commit();
		 * 
		 * System.out.println("ID da pessoa procurada = " +
		 * pessoaProcurada.getIdPessoaProcurada());
		 * 
		 * manager.close(); return pessoaProcurada; }
		 * 
		 * private InfoContato addInfoContato(String descricao, TipoInfoContato
		 * tipoInfoContato, Usuario usuario, PessoaProcurada pessoaProcurada) {
		 * InfoContato infoContato = new InfoContato(descricao, tipoInfoContato,
		 * usuario, pessoaProcurada);
		 * 
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * manager.getTransaction().begin(); manager.persist(infoContato);
		 * manager.getTransaction().commit();
		 * 
		 * System.out.println("ID do infoContato = " +
		 * infoContato.getIdInfoContato());
		 * 
		 * manager.close(); return infoContato; }
		 * 
		 * private Foto addFoto(String url, PessoaProcurada pessoaProcurada) {
		 * Foto foto = new Foto(url, pessoaProcurada);
		 * 
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * manager.getTransaction().begin(); manager.persist(foto);
		 * manager.getTransaction().commit();
		 * 
		 * System.out.println("ID da foto = " + foto.getIdFoto());
		 * 
		 * manager.close(); return foto; }
		 * 
		 * private Avistamento addAvistamento(Usuario usuario, PessoaProcurada
		 * pessoaProcurada) { Avistamento avistamento = new Avistamento(usuario,
		 * pessoaProcurada);
		 * 
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * manager.getTransaction().begin(); manager.persist(avistamento);
		 * manager.getTransaction().commit();
		 * 
		 * System.out.println("ID do avistamento = " +
		 * avistamento.getIdAvistamento());
		 * 
		 * manager.close(); return avistamento; }
		 * 
		 * private Circunstancia addCircunstancia(Date data, String detalhes,
		 * PessoaProcurada pessoaProcurada, Avistamento avistamento) {
		 * Circunstancia circunstancia = new Circunstancia(data, detalhes,
		 * pessoaProcurada, avistamento);
		 * 
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * manager.getTransaction().begin(); manager.persist(circunstancia);
		 * manager.getTransaction().commit();
		 * 
		 * System.out.println("ID da circunstancia = " +
		 * circunstancia.getIdCircunstancia());
		 * 
		 * manager.close(); return circunstancia; }
		 * 
		 * private Localizacao addLocalizacao(String descricao, String cidade,
		 * String uf, Float altitude, Float longitude, Circunstancia
		 * circunstancia) { Localizacao localizacao = new Localizacao(descricao,
		 * cidade, uf, altitude, longitude, circunstancia);
		 * 
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * manager.getTransaction().begin(); manager.persist(localizacao);
		 * manager.getTransaction().commit();
		 * 
		 * System.out.println("ID da localizacao = " +
		 * localizacao.getIdLocalizacao());
		 * 
		 * manager.close(); return localizacao; }
		 * 
		 * 
		 * private Usuario carregaUsuario(Long id) {
		 * 
		 * EntityManager manager = factory.createEntityManager();
		 * 
		 * Usuario usuario = manager.find(Usuario.class, id);
		 * 
		 * manager.close();
		 * 
		 * return usuario; }
		 * 
		 * // falta remove e merge; remover e atualizar
		 */
	}
}
