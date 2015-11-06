package br.com.ws.pojos;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.ws.enuns.CabeloCor;
import br.com.ws.enuns.CabeloTipo;
import br.com.ws.enuns.Etnia;
import br.com.ws.enuns.Genero;
import br.com.ws.enuns.Olhos;
import br.com.ws.enuns.TipoFisico;
import br.com.ws.enuns.TipoPessoaProcurada;

/**
 * @author Priscila Junho, 2015 Entidade PessoaProcurada;
 */
@XmlRootElement
@Entity
public class PessoaProcurada implements Serializable {
	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -1275083371720728290L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pessoa_procurada_ID")
	private Long idPessoaProcurada;

	private String nome;
	@Enumerated(EnumType.STRING)
	private Genero genero;
	@Enumerated(EnumType.STRING)
	private TipoPessoaProcurada tipoPessoaProcurada;

	private String dataNascimento;

	@Enumerated(EnumType.STRING)
	private Etnia etnia;
	@Enumerated(EnumType.STRING)
	private Olhos olhos;
	@Enumerated(EnumType.STRING)
	private TipoFisico tipoFisico;
	@Enumerated(EnumType.STRING)
	private CabeloCor cabeloCor;
	@Enumerated(EnumType.STRING)
	private CabeloTipo cabeloTipo;

	private double altura;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_usuario")
	private Usuario usuario;

	public PessoaProcurada() {
	}

	public PessoaProcurada(String nome, Genero genero,
			TipoPessoaProcurada tipoPessoaProcurada, String dataNascimento,
			Etnia etnia, Olhos olhos, TipoFisico tipoFisico,
			CabeloCor cabeloCor, CabeloTipo cabeloTipo, double altura,
			Usuario usuario) {
		super();
		this.nome = nome;
		this.genero = genero;
		this.tipoPessoaProcurada = tipoPessoaProcurada;
		this.dataNascimento = dataNascimento;
		this.etnia = etnia;
		this.olhos = olhos;
		this.tipoFisico = tipoFisico;
		this.cabeloCor = cabeloCor;
		this.cabeloTipo = cabeloTipo;
		this.altura = altura;
		this.usuario = usuario;
	}

	@XmlElement
	public Long getIdPessoaProcurada() {
		return idPessoaProcurada;
	}

	public void setIdPessoaProcurada(Long idPessoaProcurada) {
		this.idPessoaProcurada = idPessoaProcurada;
	}

	@XmlElement
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@XmlElement
	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	@XmlElement
	public TipoPessoaProcurada getTipoPessoaProcurada() {
		return tipoPessoaProcurada;
	}

	public void setTipoPessoaProcurada(TipoPessoaProcurada tipoPessoaProcurada) {
		this.tipoPessoaProcurada = tipoPessoaProcurada;
	}

	@XmlElement
	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@XmlElement
	public Etnia getEtnia() {
		return etnia;
	}

	public void setEtnia(Etnia etnia) {
		this.etnia = etnia;
	}

	@XmlElement
	public Olhos getOlhos() {
		return olhos;
	}

	public void setOlhos(Olhos olhos) {
		this.olhos = olhos;
	}

	@XmlElement
	public TipoFisico getTipoFisico() {
		return tipoFisico;
	}

	public void setTipoFisico(TipoFisico tipoFisico) {
		this.tipoFisico = tipoFisico;
	}

	@XmlElement
	public CabeloCor getCabeloCor() {
		return cabeloCor;
	}

	public void setCabeloCor(CabeloCor cabeloCor) {
		this.cabeloCor = cabeloCor;
	}

	@XmlElement
	public CabeloTipo getCabeloTipo() {
		return cabeloTipo;
	}

	public void setCabeloTipo(CabeloTipo cabeloTipo) {
		this.cabeloTipo = cabeloTipo;
	}

	@XmlElement
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@XmlElement
	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}
}