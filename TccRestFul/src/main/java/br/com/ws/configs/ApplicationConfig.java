package br.com.ws.configs;


import java.util.HashSet;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import br.com.ws.services.AvistamentoWS;
import br.com.ws.services.CircunstanciaWS;
import br.com.ws.services.FotoWS;
import br.com.ws.services.InfoContatoWS;
import br.com.ws.services.LocalizacaoWS;
import br.com.ws.services.PessoaProcuradaWS;
import br.com.ws.services.UsuarioWS;

//http://localhost:8080/TccRestFul/rest/application.wadl
@ApplicationPath("rest")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new HashSet<Class<?>>();
		addRestResourceClasses(resources);
		return resources;
	}

	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(UsuarioWS.class);
		resources.add(PessoaProcuradaWS.class);
		resources.add(AvistamentoWS.class);
		resources.add(CircunstanciaWS.class);
		resources.add(FotoWS.class);
		resources.add(InfoContatoWS.class);
		resources.add(LocalizacaoWS.class);



	}

}
