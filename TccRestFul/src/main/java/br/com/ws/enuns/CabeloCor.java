package br.com.ws.enuns;

public enum CabeloCor {
	LOIRO("Loiro"), PRETO("Preto"), CASTANHO("Castanho"), RUIVO("Ruivo"), GRISALHO(
			"Grisalho"), BRANCO("Branco"), OUTRO("Outro");

	public String descricao;

	private CabeloCor(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
