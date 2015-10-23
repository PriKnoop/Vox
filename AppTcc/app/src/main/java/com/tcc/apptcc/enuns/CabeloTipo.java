package com.tcc.apptcc.enuns;

public enum CabeloTipo {
	LISOS("Lisos"), ONDULADOS("Ondulados"), CACHEADOS("Cacheados"), CRESPOS(
			"Crespos"), OUTRO("Outro");

	public String descricao;

	private CabeloTipo(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
