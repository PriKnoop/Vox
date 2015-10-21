package br.com.ws.pojos;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Usuario implements Serializable {
	@Transient
	private static final long serialVersionUID = -308665436033317940L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_ID")
	private Long idUsuario;
	private String nome;
	private String login;
	private String senha;
	@Column(name = "foto_pessoal")
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

	@XmlElement
	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@XmlElement
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlElement
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@XmlElement
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@XmlElement
	public String getFotoPessoal() {
		return fotoPessoal;
	}

	public void setFotoPessoal(String fotoPessoal) {
		this.fotoPessoal = fotoPessoal;
	}
}
