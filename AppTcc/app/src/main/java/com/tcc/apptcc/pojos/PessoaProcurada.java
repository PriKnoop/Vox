package com.tcc.apptcc.pojos;

import com.tcc.apptcc.enuns.*;

import java.util.Date;
import java.util.List;


/**
 * @author Priscila
 * Junho, 2015
 * Entidade PessoaProcurada;
 */


public class PessoaProcurada {

	private Long idPessoaProcurada;
	private String nome;
	private Genero genero ;
	private TipoPessoaProcurada tipoPessoaProcurada; 
	private Date dataNascimento;
	private Etnia etnia;
	private Olhos olhos;
	private TipoFisico tipoFisico ;
	private CabeloCor cabeloCor;
	private CabeloTipo cabeloTipo;
	private double altura;
	private Usuario usuario;

						   public PessoaProcurada() {
	}

	public PessoaProcurada(String nome, Genero genero, TipoPessoaProcurada tipoPessoaProcurada,
						   Date dataNascimento, Etnia etnia, Olhos olhos,
						   TipoFisico tipoFisico, CabeloCor cabeloCor, CabeloTipo cabeloTipo,
			Usuario usuario, Double altura) {
		super();
		this.nome = nome;
		this.genero = genero;
		this.tipoPessoaProcurada = tipoPessoaProcurada;
		this.dataNascimento = dataNascimento;
		this.etnia = etnia;
		this.olhos = olhos;
		this.tipoFisico = tipoFisico;
		this.cabeloCor = cabeloCor;
		this.cabeloTipo = cabeloTipo;
		this.usuario = usuario;
		this.altura = altura;
	}

	public Long getIdPessoaProcurada() {
		return idPessoaProcurada;
	}

	public void setIdPessoaProcurada(Long idPessoaProcurada) {
		this.idPessoaProcurada = idPessoaProcurada;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public TipoPessoaProcurada getTipoPessoaProcurada() {
		return tipoPessoaProcurada;
	}

	public void setTipoPessoaProcurada(TipoPessoaProcurada tipoPessoaProcurada) {
		this.tipoPessoaProcurada = tipoPessoaProcurada;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Etnia getEtnia() {
		return etnia;
	}

	public void setEtnia(Etnia etnia) {
		this.etnia = etnia;
	}

	public Olhos getOlhos() {
		return olhos;
	}

	public void setOlhos(Olhos olhos) {
		this.olhos = olhos;
	}

	public TipoFisico getTipoFisico() {
		return tipoFisico;
	}

	public void setTipoFisico(TipoFisico tipoFisico) {
		this.tipoFisico = tipoFisico;
	}

	public CabeloCor getCabeloCor() {
		return cabeloCor;
	}

	public void setCabeloCor(CabeloCor cabeloCor) {
		this.cabeloCor = cabeloCor;
	}

	public CabeloTipo getCabeloTipo() {
		return cabeloTipo;
	}

	public void setCabeloTipo(CabeloTipo cabeloTipo) {
		this.cabeloTipo = cabeloTipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}
}