package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "db_onibus", schema = "public")
public class OnibusBO extends Model {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "ds_numero", nullable = false, length = 16)
	private String numero;

	@Column(name = "ds_placa", nullable = false, unique = true, length = 8)
	private String placa;

	@OneToMany(mappedBy ="onibus" ,cascade=CascadeType.ALL, fetch=FetchType.EAGER )
	private List<LocalizacaoBO> listLocalizacao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<LocalizacaoBO> getListLocalizacao() {
		return listLocalizacao;
	}

	public void setListLocalizacao(List<LocalizacaoBO> listLocalizacao) {
		this.listLocalizacao = listLocalizacao;
	}
}
