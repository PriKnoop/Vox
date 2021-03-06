package com.tcc.apptcc.daos;

import android.util.Log;

import com.google.gson.Gson;
import com.tcc.apptcc.pojos.*;
import com.tcc.apptcc.adapters.*;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Pri on 21/09/2015.
 */
public class UsuarioDAO {

    //Serializando de Usuario para Json
    //Usuario usuario = new Usuario();
    //Gson gson = new Gson();
    //String json = gson.toJson(usuario);

    //Deserializando de Json para Usuario
    //Usuario usuarioObject = gson.fromJson(json, Usuario.class);

    //genymotion = 10.0.3.2
    //private static final String URL_PADRAO = "http://192.168.0.2:8080/WSRest/rest/usuario";

    private static final String ip = new IPConfig().getIP();

    private static final String URL_PADRAO = ip +":8080/WSRest/rest/usuario";

    public Usuario chamaMetodoAdicionarUsuario(Usuario usuario) {

        String URL = URL_PADRAO + "";

        try {
            if (usuario.getLogin() != null){

                //usuario.setFotoPessoalEncoded(null);
                Gson gson = new Gson();
                String json = gson.toJson(usuario);
                Log.i("DEBUG", json.toString());

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                restTemplate.setRequestFactory(
                        new HttpComponentsClientHttpRequestFactory());

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON );

                HttpEntity<Usuario> request= new HttpEntity(json, headers);

                //Mudar retorno pra JSON
               // Usuario usuarioRetornado = restTemplate.postForObject(URL, request, Usuario.class );
                Usuario usuarioRetornado = restTemplate.postForObject(URL, usuario, Usuario.class );


                //ResponseEntity<Usuario> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, request, Usuario.class);
                //Usuario usuarioRetornado = restTemplate.postForObject(URL,json, Usuario.class);
                return usuarioRetornado;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Usuario chamaMetodoPesquisarUsuarioPorId(Long id){
        String URL = URL_PADRAO + "/"+id;
        Gson gson = new Gson();
        String response = Json.get(URL).toString();
        Usuario usuario = gson.fromJson(response, Usuario.class);
        return usuario;
    }

    //public Usuario chamaMetodoAutenticarLoginSenha(String login, String senha) {
    public Usuario chamaMetodoAutenticarLoginSenha(String... params) {

        String URL = URL_PADRAO + "/autenticarLoginSenha/{login}/{senha}";

        try {
            RestTemplate restTemplate = new RestTemplate();
            //utiliza a biblioteca do jackson para converter os dados recebidos e enviados

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //ver o que faz
            restTemplate.setRequestFactory(
                    new HttpComponentsClientHttpRequestFactory());
            //url que será acessada, tipo do retorno e parametro que o método do ws determinda

            Usuario usuarioRetornado = restTemplate.getForObject(URL, Usuario.class, params);

            return usuarioRetornado;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }



    public Boolean chamaMetodoVerificarLogin(String login) {

        String URL = URL_PADRAO + "/verificarLogin/{login}";

        try {
            RestTemplate restTemplate = new RestTemplate();
            //utiliza a biblioteca do jackson para converter os dados recebidos e enviados

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            //ver o que faz
            restTemplate.setRequestFactory(
                    new HttpComponentsClientHttpRequestFactory());
            //url que será acessada, tipo do retorno e parametro que o método do ws determinda

            Usuario usuarioRetornado = restTemplate.getForObject(URL, Usuario.class, login);
            if(usuarioRetornado == null){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }


       /* if (login != null && senha != null) {
            String URL = URL_PADRAO + "/autenticarLoginSenha/" + login + "/" + senha;
            Gson gson = new Gson();
            JSONObject json = Json.get(URL);
            if(json!=null) {
                String response = Json.get(URL).toString();
                Usuario usuario = gson.fromJson(response, Usuario.class);
                return usuario;
            }else {
                return null;
            }

        } else {
            return null;
        }
    }*/

/*    public Usuario chamaMetodoAdicionarUsuario(String login, String senha) {
        if (login != null && senha != null) {
            Gson gson = new Gson();

            String URL = URL_PADRAO + "/adicionar/" + login + "/" + senha;
            JSONObject json = Json.get(URL);
            if(json!=null) {
                String response = Json.get(URL).toString();
                Usuario usuarioRetornado = gson.fromJson(response, Usuario.class);
                return usuarioRetornado;
            }else {
                return null;
            }

        } else {
            return null;
        }
    }*/
}

