package br.com.ws.enuns;

public enum Etnia {
	CAUCASIANA("Caucasiana"), NEGRA("Negra"), INDIGENA("Indigena"), PARDA(
			"Parda"), MULATA("Mulata"), CABOCLA("Cabocla"), CAFUZA("Cafuza"), OUTRA(
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
