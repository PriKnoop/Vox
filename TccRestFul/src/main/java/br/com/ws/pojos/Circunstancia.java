package br.com.ws.pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Priscila Junho, 2015 Entidade Circunstancia;
 */
@XmlRootElement
@Entity
@XmlSeeAlso({Localizacao.class})
public class Circunstancia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "circunstancia_ID")
	private Long idCircunstancia;
	private Date data;
	
	@Column(length = 300)
	private String detalhes;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pessoa_procurada_ID")
	private PessoaProcurada pessoaProcurada;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "avistamento_ID")
	private Avistamento avistamento;

	public Circunstancia() {
	}

	public Circunstancia(Date data, String detalhes,
			PessoaProcurada pessoaProcurada, Avistamento avistamento) {
		super();
		this.data = data;
		this.detalhes = detalhes;
		this.pessoaProcurada = pessoaProcurada;
		this.avistamento = avistamento;
	}

	@XmlElement
	public Long getIdCircunstancia() {
		return idCircunstancia;
	}

	public void setIdCircunstancia(Long idCircunstancia) {
		this.idCircunstancia = idCircunstancia;
	}
	@XmlElement
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	@XmlElement
	public String getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	@XmlElement
	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}

	@XmlElement
	public Avistamento getAvistamento() {
		return avistamento;
	}

	public void setAvistamento(Avistamento avistamento) {
		this.avistamento = avistamento;
	}

}
