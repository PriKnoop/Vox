package br.com.ws.enuns;

public enum Genero {

	MASCULINO("Masculino"), FEMININO("Feminino"), OUTRO("Outro");

	public String descricao;

	private Genero(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
