package br.com.ws.enuns;

public enum TipoFisico {
	MAGRO("Magro"), INTERMEDIARIO("Intermediário"), SOBREPESO("Sobrepeso"), OBESO(
			"Obeso"), OUTRO("Outro");

	public String descricao;

	private TipoFisico(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
