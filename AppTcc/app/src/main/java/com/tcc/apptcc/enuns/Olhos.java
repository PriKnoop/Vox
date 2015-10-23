package com.tcc.apptcc.enuns;

public enum Olhos {
	CASTANHOS("Castanhos"), AZUIS("Azuis"), VERDES("Verdes"), CINZAS("Cinzas"), AVELAS(
			"Avelãs"), AMBAR("Ambar"), VIOLETAS("Violetas"), OUTRA("Outra");

	public String descricao;

	private Olhos(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
