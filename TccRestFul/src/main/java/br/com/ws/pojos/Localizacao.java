package br.com.ws.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Priscila Junho, 2015 Entidade Localizacao;
 */
@XmlRootElement
@Entity
public class Localizacao implements Serializable {
	@Transient
	private static final long serialVersionUID = -3414859419688358964L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "localizacao_ID")
	private Long idLocalizacao;
	
	@Column(length = 200)
	private String descricao;
	private String cidade;
	private String uf;
	private Float latitude;
	private Float longitude;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "circunstancia_ID")
	private Circunstancia circunstancia;

	public Localizacao() {
	}

	public Localizacao(String descricao, String cidade, String uf,
			Float latitude, Float longitude, Circunstancia circunstancia) {
		super();
		this.descricao = descricao;
		this.cidade = cidade;
		this.uf = uf;
		this.latitude = latitude;
		this.longitude = longitude;
		this.circunstancia = circunstancia;
	}

	@XmlElement
	public Long getIdLocalizacao() {
		return idLocalizacao;
	}

	public void setIdLocalizacao(Long idLocalizacao) {
		this.idLocalizacao = idLocalizacao;
	}

	@XmlElement
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@XmlElement
	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@XmlElement
	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@XmlElement
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	@XmlElement
	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
	@XmlElement
	public Circunstancia getCircunstancia() {
		return circunstancia;
	}

	public void setCircunstancia(Circunstancia circunstancia) {
		this.circunstancia = circunstancia;
	}
	
	
}
