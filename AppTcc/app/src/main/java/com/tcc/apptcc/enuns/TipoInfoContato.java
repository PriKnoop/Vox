package com.tcc.apptcc.enuns;

public enum TipoInfoContato {
	EMAIL("E-mail"), CELULAR("Celular"), RESIDENCIAL("Residencial"), FACEBOOK(
			"Facebook");

	public String descricao;

	private TipoInfoContato(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
