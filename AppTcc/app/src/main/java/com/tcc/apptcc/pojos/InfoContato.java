package com.tcc.apptcc.pojos;


import com.tcc.apptcc.enuns.TipoInfoContato;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade InfoContato;
 */
public class InfoContato {
	private Long idInfoContato;
	private String descricao;
	private TipoInfoContato tipoInfoContato;

	private Usuario usuario;

	private PessoaProcurada pessoaProcurada;

	public InfoContato(){}
	
	public InfoContato(String descricao,
			TipoInfoContato tipoInfoContato, Usuario usuario,
			PessoaProcurada pessoaProcurada) {
		super();
		this.descricao = descricao;
		this.tipoInfoContato = tipoInfoContato;
		this.usuario = usuario;
		this.pessoaProcurada = pessoaProcurada;
	}

	public Long getIdInfoContato() {
		return idInfoContato;
	}

	public void setIdInfoContato(Long idInfoContato) {
		this.idInfoContato = idInfoContato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoInfoContato getTipoInfoContato() {
		return tipoInfoContato;
	}

	public void setTipoInfoContato(TipoInfoContato tipoInfoContato) {
		this.tipoInfoContato = tipoInfoContato;
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
