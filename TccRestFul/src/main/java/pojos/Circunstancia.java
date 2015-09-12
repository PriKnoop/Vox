package pojos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade Circunstancia;
 */

@Entity
public class Circunstancia {
	@Id
	@GeneratedValue
	@Column(name="circunstancia_ID")
	private Long idCircunstancia;
	private Date data;
	private String detalhes;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pessoa_procurada_ID")
	@Cascade(CascadeType.REMOVE)
	private PessoaProcurada pessoaProcurada;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "avistamento_ID")
	private Avistamento avistamento;
	
	public Circunstancia(){}

	public Circunstancia(Date data, String detalhes,
			PessoaProcurada pessoaProcurada, Avistamento avistamento) {
		super();
		this.data = data;
		this.detalhes = detalhes;
		this.pessoaProcurada = pessoaProcurada;
		this.avistamento = avistamento;
	}

	public Long getIdCircunstancia() {
		return idCircunstancia;
	}

	public void setIdCircunstancia(Long idCircunstancia) {
		this.idCircunstancia = idCircunstancia;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}

	public Avistamento getAvistamento() {
		return avistamento;
	}

	public void setAvistamento(Avistamento avistamento) {
		this.avistamento = avistamento;
	}
	
}
