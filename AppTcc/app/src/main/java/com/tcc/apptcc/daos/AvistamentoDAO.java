package com.tcc.apptcc.daos;

import android.util.Log;

import com.google.gson.Gson;
import com.tcc.apptcc.adapters.IPConfig;
import com.tcc.apptcc.pojos.Avistamento;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Pri on 02/11/2015.
 */
public class AvistamentoDAO {
    private static final String ip = new IPConfig().getIP();
    private static final String URL_PADRAO = ip +":8080/WSRest/rest/avistamento";

    public Avistamento chamaMetodoAdicionarAvistamento(Avistamento avistamento) {

        String URL = URL_PADRAO + "";

        try {
            if (avistamento.getPessoaProcurada() != null){

                Gson gson = new Gson();
                String json = gson.toJson(avistamento);
                Log.i("DEBUG", json.toString());

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(
                        new HttpComponentsClientHttpRequestFactory());
                Avistamento avistamentoRetornado = restTemplate.postForObject(URL, avistamento, Avistamento.class );

                return avistamentoRetornado;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Avistamento chamaMetodoPesquisarAvistamentoPorId(Long id) {

        String URL = URL_PADRAO + "/{id}";

        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(
                    new HttpComponentsClientHttpRequestFactory());

            Avistamento avistamentoRetornado = restTemplate.getForObject(URL, Avistamento.class, id);
            return avistamentoRetornado;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
