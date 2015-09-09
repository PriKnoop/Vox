package br.com.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.ParameterStyle;
import javax.jws.soap.SOAPBinding.Style;

import br.com.dao.PessoaDAO;
import br.com.pojo.Pessoa;

//Cada classe WS nova deve ser adicionada como um endpoint no arquivo src/main/webapp/WEB-INF/sun-jaxws.xml
//Por exemplo, digamos que queiram criar outro WS chamado ProdutoWS, deve se adicionar a seguinte linha no arquivo:
//<endpoint name="ProdutoWS" implementation="br.com.ws.ProdutoWS" url-pattern="/ProdutoWS" ></endpoint>

@WebService
@SOAPBinding(style=Style.RPC)
public class PessoaWS {
		
	@WebMethod(operationName="carregaPessoa")
	public Pessoa carregaPessoa(@WebParam(name="id") Long id) {		
		return PessoaDAO.getInstance().carregaPessoa(id);
		
	}

	@WebMethod(operationName="addPessoa")
	public void addPessoa(@WebParam(name="texto") String texto) {
		PessoaDAO.getInstance().addPessoa(texto);
	}
	
}
