package com.tcc.apptcc.daos;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tcc.apptcc.adapters.IPConfig;
import com.tcc.apptcc.adapters.Json;
import com.tcc.apptcc.pojos.PessoaProcurada;
import com.tcc.apptcc.pojos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Pri on 23/10/2015.
 */
public class PessoaProcuradaDAO {

    private static final String ip = new IPConfig().getIP();

    private static final String URL_PADRAO = ip + ":8080/WSRest/rest/pessoa";

    public PessoaProcurada chamaMetodoAdicionarPessoaProcurada(PessoaProcurada pessoa) {

        String URL = URL_PADRAO + "";

        try {
            if (pessoa.getNome() != null) {

                Gson gson = new Gson();
                String json = gson.toJson(pessoa);
                Log.i("DEBUG", json.toString());

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(
                        new HttpComponentsClientHttpRequestFactory());
                PessoaProcurada pessoaRetornada = restTemplate.postForObject(URL, pessoa, PessoaProcurada.class);

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

    public List<PessoaProcurada> chamaMetodoPesquisarTodasPessoasCadastradas() {

        String URL = URL_PADRAO + "/pesquisarTodos";

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.setRequestFactory(
                    new HttpComponentsClientHttpRequestFactory());

            ArrayList<PessoaProcurada> listaPessoasRetornadas = new ArrayList<PessoaProcurada>();
            Gson gson = new Gson();
        JSONObject response = Json.get(URL);

        try {
                if (response != null){
                    JSONArray array = response.getJSONArray("pessoaProcurada");
                    if (array == null){
                        listaPessoasRetornadas.add(gson.fromJson(response.toString(), PessoaProcurada.class));
                    } else {
                        for (int i = 0; i < array.length(); i++) {
                            listaPessoasRetornadas.add(gson.fromJson(array.get(i).toString(), PessoaProcurada.class));
                        }
                    }
                } else {
                    return null;
                }

            } catch (JSONException e) {
                listaPessoasRetornadas.add(gson.fromJson(response.toString(), PessoaProcurada.class));
                e.printStackTrace();
            }
            return listaPessoasRetornadas;
    }

    public List<PessoaProcurada> chamaMetodoPesquisarPessoasPorUsuario(Long idUsuario) {

        String URL = URL_PADRAO + "/pesquisarPorUsuario/" + idUsuario;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.setRequestFactory(
                new HttpComponentsClientHttpRequestFactory());

        ArrayList<PessoaProcurada> listaPessoasRetornadas = new ArrayList<PessoaProcurada>();
        Gson gson = new Gson();
        JSONObject response = Json.get(URL);

        try {
            if (response != null){
                JSONArray array = response.getJSONArray("pessoaProcurada");
                if (array == null){
                    listaPessoasRetornadas.add(gson.fromJson(response.toString(), PessoaProcurada.class));
                } else {
                    for (int i = 0; i < array.length()-1; i++) {
                        listaPessoasRetornadas.add(gson.fromJson(array.get(i).toString(), PessoaProcurada.class));
                    }
                }
            } else {
                return null;
            }

        } catch (JSONException e) {
            listaPessoasRetornadas.add(gson.fromJson(response.toString(), PessoaProcurada.class));
            e.printStackTrace();
        }
        return listaPessoasRetornadas;
    }
}

