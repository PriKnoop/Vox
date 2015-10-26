package com.tcc.apptcc.daos;

import android.util.Log;

import com.google.gson.Gson;
import com.tcc.apptcc.adapters.IPConfig;
import com.tcc.apptcc.adapters.Json;
import com.tcc.apptcc.pojos.PessoaProcurada;
import com.tcc.apptcc.pojos.Usuario;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Pri on 23/10/2015.
 */
public class PessoaProcuradaDAO {

    private static final String ip = new IPConfig().getIP();

    private static final String URL_PADRAO = ip +":8080/WSRest/rest/pessoa";

    public PessoaProcurada chamaMetodoAdicionarPessoaProcurada(PessoaProcurada pessoa) {

        String URL = URL_PADRAO + "";

        try {
            if (pessoa.getNome() != null){

                Gson gson = new Gson();
                String json = gson.toJson(pessoa);
                Log.i("DEBUG", json.toString());

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(
                        new HttpComponentsClientHttpRequestFactory());
                PessoaProcurada pessoaRetornada = restTemplate.postForObject(URL, pessoa, PessoaProcurada.class );

                return pessoaRetornada;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

   /* public PessoaProcurada chamaMetodoPesquisarUPessoaPorId(Long id){
        String URL = URL_PADRAO + "/"+id;
        Gson gson = new Gson();
        String response = Json.get(URL).toString();
        Usuario usuario = gson.fromJson(response, Usuario.class);
        return usuario;
    }*/

    public PessoaProcurada chamaMetodoPesquisarPessoaPorId(Long id) {

        String URL = URL_PADRAO + "/{id}";

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(
                    new HttpComponentsClientHttpRequestFactory());

            PessoaProcurada pessoaRetornada = restTemplate.getForObject(URL, PessoaProcurada.class, id);
            return pessoaRetornada;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

