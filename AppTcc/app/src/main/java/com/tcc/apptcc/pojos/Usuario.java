package com.tcc.apptcc.pojos;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Usuario implements Serializable {

    private Long idUsuario;
    private String nome;
    private String login;
    private String senha;
    private String fotoPessoal;
    public Usuario() {
    }

    public Usuario(String nome, String login, String senha, String fotoPessoal) {
        super();
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.fotoPessoal = fotoPessoal;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public String getFotoPessoal() {
        return fotoPessoal;
    }

    public void setFotoPessoal(String fotoPessoal) {
        this.fotoPessoal = fotoPessoal;
    }
}

