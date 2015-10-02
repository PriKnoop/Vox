package com.tcc.apptcc.pojos;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade Avistamento;
 */

public class Avistamento {

	private Long idAvistamento;

	private Usuario usuario;

	private PessoaProcurada pessoaProcurada;

	private Circunstancia circunstancia;
	
	public Avistamento(){}

	public Avistamento(Usuario usuario, PessoaProcurada pessoaProcurada) {
		super();
		this.usuario = usuario;
		this.pessoaProcurada = pessoaProcurada;
	}

	public Long getIdAvistamento() {
		return idAvistamento;
	}

	public void setIdAvistamento(Long idAvistamento) {
		this.idAvistamento = idAvistamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}
	
}
