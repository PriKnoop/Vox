package entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Priscila
 * Junho, 2015
 * Entidade Foto;
 */

@Entity
public class Foto {
	@Id
	@GeneratedValue
	@Column(name="foto_ID")
	private Long idFoto;
	private String url;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pessoa_procurada_ID")
	private PessoaProcurada pessoaProcurada;
	
	public Foto(){}
	
	public Foto(String url, PessoaProcurada pessoaProcurada) {
		this.url = url;
		this.pessoaProcurada = pessoaProcurada;
	}

	public Long getIdFoto() {
		return idFoto;
	}

	public void setIdFoto(Long idFoto) {
		this.idFoto = idFoto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}
}
