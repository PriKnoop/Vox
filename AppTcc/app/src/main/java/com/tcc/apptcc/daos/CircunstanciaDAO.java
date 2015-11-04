package com.tcc.apptcc.daos;

import android.util.Log;

import com.google.gson.Gson;
import com.tcc.apptcc.adapters.IPConfig;
import com.tcc.apptcc.pojos.Circunstancia;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Pri on 02/11/2015.
 */
public class CircunstanciaDAO {
    private static final String ip = new IPConfig().getIP();
    private static final String URL_PADRAO = ip +":8080/WSRest/rest/circunstancia";

    public Circunstancia chamaMetodoAdicionarCircunstancia(Circunstancia circunstancia) {

        String URL = URL_PADRAO + "";

        try {
            if (circunstancia.getDetalhes() != null){

                Gson gson = new Gson();
                String json = gson.toJson(circunstancia);
                Log.i("DEBUG", json.toString());

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(
                        new HttpComponentsClientHttpRequestFactory());
                Circunstancia circunstanciaRetornada = restTemplate.postForObject(URL, circunstancia, Circunstancia.class );

                return circunstanciaRetornada;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Circunstancia chamaMetodoPesquisarCircunstanciaPorId(Long id) {

        String URL = URL_PADRAO + "/{id}";

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(
                    new HttpComponentsClientHttpRequestFactory());

            Circunstancia circunstanciaRetornada = restTemplate.getForObject(URL, Circunstancia.class, id);
            return circunstanciaRetornada;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


