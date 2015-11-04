package com.tcc.apptcc.pojos;

import java.util.Date;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade Circunstancia;
 */

public class Circunstancia {
	private Long idCircunstancia;
	private String data;
	private String detalhes;

	private PessoaProcurada pessoaProcurada;

	private Avistamento avistamento;
	
	public Circunstancia(){}

	public Circunstancia(String data, String detalhes,
			PessoaProcurada pessoaProcurada, Avistamento avistamento) {
		super();
		this.data = data;
		this.detalhes = detalhes;
		this.pessoaProcurada = pessoaProcurada;
		this.avistamento = avistamento;
	}

	public Long getIdCircunstancia() {
		return idCircunstancia;
	}

	public void setIdCircunstancia(Long idCircunstancia) {
		this.idCircunstancia = idCircunstancia;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}

	public Avistamento getAvistamento() {
		return avistamento;
	}

	public void setAvistamento(Avistamento avistamento) {
		this.avistamento = avistamento;
	}
	
}
