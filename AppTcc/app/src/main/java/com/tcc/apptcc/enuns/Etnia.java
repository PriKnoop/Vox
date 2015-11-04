package com.tcc.apptcc.enuns;

public enum Etnia {
	CAUCASIANA("Caucasiana"), NEGRA("Negra"), INDIGENA("Indigena"), PARDA(
			"Parda"), OUTRA(
			"Outra");

	public String descricao;

	private Etnia(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
