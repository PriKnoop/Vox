package com.tcc.apptcc.daos;

import android.util.Log;

import com.google.gson.Gson;
import com.tcc.apptcc.adapters.IPConfig;
import com.tcc.apptcc.adapters.Json;
import com.tcc.apptcc.pojos.Avistamento;
import com.tcc.apptcc.pojos.PessoaProcurada;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    public List<Avistamento> chamaMetodoPesquisarAvistamentoPorPessoa(Long idPessoaProcurada) {

        String URL = URL_PADRAO + "/pesquisaPorPessoa/" + idPessoaProcurada;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(
                new HttpComponentsClientHttpRequestFactory());

        ArrayList<Avistamento> listaAvistamentosRetornados = new ArrayList<Avistamento>();
        Gson gson = new Gson();

        try {

            JSONObject retornoJson = Json.get(URL);
            if(retornoJson != null){
                JSONArray array = retornoJson.getJSONArray("avistamento");
                for (int i = 0; i < array.length(); i++) {
                    listaAvistamentosRetornados.add(gson.fromJson(array.get(i).toString(), Avistamento.class));
                }
            } else {
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listaAvistamentosRetornados;
    }

    public Avistamento chamaMetodoPesquisarUltimoAvistamentoPorPessoa(Long idPessoaProcurada) {
        String URL = URL_PADRAO + "/pesquisaUltimoAvistamentoPorPessoa/" + idPessoaProcurada;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(
                new HttpComponentsClientHttpRequestFactory());
        Gson gson = new Gson();
        Avistamento avistamentoRetornado = new Avistamento();
        try {

            JSONObject retornoJson = Json.get(URL);
            if(retornoJson != null){
                avistamentoRetornado = gson.fromJson(retornoJson.toString(), Avistamento.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return avistamentoRetornado;
    }
}
