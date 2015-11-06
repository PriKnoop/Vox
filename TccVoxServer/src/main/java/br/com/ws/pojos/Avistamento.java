package br.com.ws.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @author Priscila Junho, 2015 Entidade Avistamento;
 */
@XmlRootElement
@Entity
public class Avistamento implements Serializable {

	@Transient
	private static final long serialVersionUID = 1810402129549263338L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "avistamento_ID")
	private Long idAvistamento;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "usuario_ID")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "pessoa_procurada_ID")
	private PessoaProcurada pessoaProcurada;

	public Avistamento() {
	}

	public Avistamento(Usuario usuario, PessoaProcurada pessoaProcurada) {
		super();
		this.usuario = usuario;
		this.pessoaProcurada = pessoaProcurada;
	}

	@XmlElement
	public Long getIdAvistamento() {
		return idAvistamento;
	}

	public void setIdAvistamento(Long idAvistamento) {
		this.idAvistamento = idAvistamento;
	}
	
	@XmlElement
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@XmlElement
	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}

}
