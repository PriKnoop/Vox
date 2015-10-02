package com.tcc.apptcc.pojos;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade Localizacao;
 */


public class Localizacao {

	private Long idLocalizacao;
	private String descricao;
	private String cidade; 
	private String uf;
	private Float latitude;
	private Float longitude;

	private Circunstancia circunstancia;
	
	public Localizacao(){}
	
	public Localizacao(String descricao, String cidade, String uf,
			Float latitude, Float longitude, Circunstancia circunstancia) {
		super();
		this.descricao = descricao;
		this.cidade = cidade;
		this.uf = uf;
		this.latitude = latitude;
		this.longitude = longitude;
		this.circunstancia = circunstancia;
	}



	public Long getIdLocalizacao() {
		return idLocalizacao;
	}
	public void setIdLocalizacao(Long idLocalizacao) {
		this.idLocalizacao = idLocalizacao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Circunstancia getCircunstancia() {
		return circunstancia;
	}
	public void setCircunstancia(Circunstancia circunstancia) {
		this.circunstancia = circunstancia;
	}
	

}
