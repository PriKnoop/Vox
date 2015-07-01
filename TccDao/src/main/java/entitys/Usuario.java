package entitys;

import java.util.List;





import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade Usuario;
 */

@Entity
public class Usuario {
	@Id
	@GeneratedValue
	@Column(name="usuario_ID")
	private Long idUsuario;
	private String nome;
	private String login;
	private String senha;
	@Column(name="foto_pessoal")
	private String fotoPessoal;
	@OneToMany(mappedBy = "usuario")
	private List<PessoaProcurada> pessoaProcurada;
	@OneToMany(mappedBy = "usuario")
	@Cascade(CascadeType.PERSIST)
	private List<InfoContato> infoContato;
	@OneToMany(mappedBy = "usuario")
	@Cascade(CascadeType.PERSIST)
	private List<Avistamento> avistamento;
	
	public Usuario() {
	}

	public Usuario(String nome, String login, String senha,
			String fotoPessoal) {
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
	
	public List<PessoaProcurada> getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(List<PessoaProcurada> pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avistamento == null) ? 0 : avistamento.hashCode());
		result = prime * result
				+ ((fotoPessoal == null) ? 0 : fotoPessoal.hashCode());
		result = prime * result
				+ ((idUsuario == null) ? 0 : idUsuario.hashCode());
		result = prime * result
				+ ((infoContato == null) ? 0 : infoContato.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((pessoaProcurada == null) ? 0 : pessoaProcurada.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (idUsuario == null) {
			if (other.idUsuario != null)
				return false;
		} else if (!idUsuario.equals(other.idUsuario))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
	
	
}
