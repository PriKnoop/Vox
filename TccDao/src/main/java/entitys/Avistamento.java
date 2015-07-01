package entitys;

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
 * Entidade Avistamento;
 */

@Entity
public class Avistamento {
	@Id
	@GeneratedValue
	@Column(name="avistamento_ID")
	private Long idAvistamento;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name = "usuario_ID")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name = "pessoa_procurada_ID")
	private PessoaProcurada pessoaProcurada;
	
	@OneToOne (mappedBy="avistamento", fetch = FetchType.LAZY)
	@Cascade(CascadeType.PERSIST)
	private Circunstancia circunstancia;
	
	public Avistamento(){}

	public Avistamento(Usuario usuario, PessoaProcurada pessoaProcurada) {
		super();
		this.usuario = usuario;
		this.pessoaProcurada = pessoaProcurada;
	}

	public Long getIdAvistamento() {
		return idAvistamento;
	}

	public void setIdAvistamento(Long idAvistamento) {
		this.idAvistamento = idAvistamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PessoaProcurada getPessoaProcurada() {
		return pessoaProcurada;
	}

	public void setPessoaProcurada(PessoaProcurada pessoaProcurada) {
		this.pessoaProcurada = pessoaProcurada;
	}
	
}
