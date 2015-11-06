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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Priscila Junho, 2015 Entidade Foto;
 */
@XmlRootElement
@Entity
public class Foto implements Serializable {
	@Transient
	private static final long serialVersionUID = -1104749711373754541L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "foto_ID")
	private Long idFoto;
	private String url;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pessoa_procurada_ID")
	private PessoaProcurada pessoaProcurada;

	public Foto() {
	}

	public Foto(String url, PessoaProcurada pessoaProcurada) {
		this.url = url;
		this.pessoaProcurada = pessoaProcurada;
	}

	@XmlElement
	public Long getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(Long idFoto) {
		this.idFoto = idFoto;
	}

	@XmlElement
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlElement
	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}
}
