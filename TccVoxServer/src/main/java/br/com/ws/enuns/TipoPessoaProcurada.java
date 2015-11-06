package br.com.ws.enuns;

public enum TipoPessoaProcurada {
	DESAPARECIDO("Desaparecido"), MORADOR("Morador de rua"), ABRIGADO(
			"Abrigado"), OUTRO("Outro");

	public String descricao;

	private TipoPessoaProcurada(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}
}
