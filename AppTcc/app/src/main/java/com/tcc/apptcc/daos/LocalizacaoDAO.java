package com.tcc.apptcc.daos;

import android.util.Log;

import com.google.gson.Gson;
import com.tcc.apptcc.adapters.IPConfig;
import com.tcc.apptcc.pojos.Localizacao;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Pri on 02/11/2015.
 */
public class LocalizacaoDAO {
    private static final String ip = new IPConfig().getIP();
    private static final String URL_PADRAO = ip +":8080/WSRest/rest/localizacao";

    public Localizacao chamaMetodoAdicionarLocalizacao(Localizacao localizacao) {

        String URL = URL_PADRAO + "";

        try {
            if (localizacao.getDescricao() != null){

                Gson gson = new Gson();
                String json = gson.toJson(localizacao);
                Log.i("DEBUG", json.toString());

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(
                        new HttpComponentsClientHttpRequestFactory());
                Localizacao localizacaoRetornada = restTemplate.postForObject(URL, localizacao, Localizacao.class );

                return localizacaoRetornada;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Localizacao chamaMetodoPesquisarLocalizacaoPorId(Long id) {

        String URL = URL_PADRAO + "/{id}";

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(
                    new HttpComponentsClientHttpRequestFactory());

            Localizacao localizacaoRetornada = restTemplate.getForObject(URL, Localizacao.class, id);
            return localizacaoRetornada;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

