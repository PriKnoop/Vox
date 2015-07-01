package entitys;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import enums.CabeloCor;
import enums.CabeloTipo;
import enums.Etnia;
import enums.Genero;
import enums.Olhos;
import enums.TipoFisico;
import enums.TipoPessoaProcurada;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade PessoaProcurada;
 */

@Entity
public class PessoaProcurada {
	@Id
	@GeneratedValue
	@Column(name="pessoa_procurada_ID")
	private Long idPessoaProcurada;
	
	private String nome;
	@Enumerated(EnumType.STRING) 
	private Genero genero ;
	@Enumerated(EnumType.STRING) 
	private TipoPessoaProcurada tipoPessoaProcurada; 
	private Date dataNascimento;
	@Enumerated(EnumType.STRING) 
	private Etnia etnia; 
	@Enumerated(EnumType.STRING) 
	private Olhos olhos; 
	@Enumerated(EnumType.STRING) 
	private TipoFisico tipoFisico ; 
	@Enumerated(EnumType.STRING) 
	private CabeloCor cabeloCor; 
	@Enumerated(EnumType.STRING) 
	private CabeloTipo cabeloTipo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_ID")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "pessoaProcurada")
	@Cascade(CascadeType.PERSIST)
	private List<InfoContato> infoContato;
	
	@OneToMany(mappedBy = "pessoaProcurada")
	@Cascade(CascadeType.PERSIST)
	private List<Foto> foto;
	
	@OneToMany(mappedBy = "pessoaProcurada")
	@Cascade(CascadeType.PERSIST)
	private List<Avistamento> avistamento;

	
	public PessoaProcurada() {
	}

	public PessoaProcurada(String nome, Genero genero, TipoPessoaProcurada tipoPessoaProcurada,
			Date dataNascimento, Etnia etnia, Olhos olhos,
			TipoFisico tipoFisico, CabeloCor cabeloCor, CabeloTipo cabeloTipo,
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
		this.usuario = usuario;
	}

	public Long getIdPessoaProcurada() {
		return idPessoaProcurada;
	}

	public void setIdPessoaProcurada(Long idPessoaProcurada) {
		this.idPessoaProcurada = idPessoaProcurada;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public TipoPessoaProcurada getTipoPessoaProcurada() {
		return tipoPessoaProcurada;
	}

	public void setTipoPessoaProcurada(TipoPessoaProcurada tipoPessoaProcurada) {
		this.tipoPessoaProcurada = tipoPessoaProcurada;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Etnia getEtnia() {
		return etnia;
	}

	public void setEtnia(Etnia etnia) {
		this.etnia = etnia;
	}

	public Olhos getOlhos() {
		return olhos;
	}

	public void setOlhos(Olhos olhos) {
		this.olhos = olhos;
	}

	public TipoFisico getTipoFisico() {
		return tipoFisico;
	}

	public void setTipoFisico(TipoFisico tipoFisico) {
		this.tipoFisico = tipoFisico;
	}

	public CabeloCor getCabeloCor() {
		return cabeloCor;
	}

	public void setCabeloCor(CabeloCor cabeloCor) {
		this.cabeloCor = cabeloCor;
	}

	public CabeloTipo getCabeloTipo() {
		return cabeloTipo;
	}

	public void setCabeloTipo(CabeloTipo cabeloTipo) {
		this.cabeloTipo = cabeloTipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<InfoContato> getInfoContato() {
		return infoContato;
	}

	public void setInfoContato(List<InfoContato> infoContato) {
		this.infoContato = infoContato;
	}

	public List<Foto> getFoto() {
		return foto;
	}

	public void setFoto(List<Foto> foto) {
		this.foto = foto;
	}
	

}