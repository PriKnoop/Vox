package br.com.dao;

import br.com.pojo.Pessoa;

public class Teste {

	public static void main(String args[]) {
		Pessoa pessoa = new Pessoa("Rodrigo", "010.356.830-16");
		
		PessoaDAO pessoaDAO = new PessoaDAO();
		
		//pessoaDAO.addPessoa(pessoa);
		
		
	}
	
}
