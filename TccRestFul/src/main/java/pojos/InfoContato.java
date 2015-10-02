package pojos;

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

import br.com.ws.enuns.TipoInfoContato;
/**
 * @author Priscila
 * Junho, 2015
 * Entidade InfoContato;
 */

@Entity
public class InfoContato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="info_contato_ID")
	private Long idInfoContato;
	private String descricao;
	@Enumerated(EnumType.STRING) 
	private TipoInfoContato tipoInfoContato;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name = "usuario_ID")
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=true)
	@JoinColumn(name = "pessoa_procurada_ID")
	private PessoaProcurada pessoaProcurada;

	public InfoContato(){}
	
	public InfoContato(String descricao,
			TipoInfoContato tipoInfoContato, Usuario usuario,
			PessoaProcurada pessoaProcurada) {
		super();
		this.descricao = descricao;
		this.tipoInfoContato = tipoInfoContato;
		this.usuario = usuario;
		this.pessoaProcurada = pessoaProcurada;
	}

	public Long getIdInfoContato() {
		return idInfoContato;
	}

	public void setIdInfoContato(Long idInfoContato) {
		this.idInfoContato = idInfoContato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoInfoContato getTipoInfoContato() {
		return tipoInfoContato;
	}

	public void setTipoInfoContato(TipoInfoContato tipoInfoContato) {
		this.tipoInfoContato = tipoInfoContato;
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
