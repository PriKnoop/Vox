package com.tcc.apptcc.pojos;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade Foto;
 */

public class Foto {

	private Long idFoto;
	private String url;
	private PessoaProcurada pessoaProcurada;
	
	public Foto(){}
	
	public Foto(String url, PessoaProcurada pessoaProcurada) {
		this.url = url;
		this.pessoaProcurada = pessoaProcurada;
	}

	public Long getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(Long idFoto) {
		this.idFoto = idFoto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}
}
