package models;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "tb_linha", schema = "public")
public class LinhaBO extends Model {
	@Id
	@GeneratedValue
	@Column( nullable = false, unique = true)
	private Long id;

	@Column(name = "ds_nome", nullable = false, unique = true, length = 10)
	private String ds_nome;

	@Column(name = "ds_linha", nullable = false, unique = true)
	private String ds_linha;

	@Column(name = "ds_sentido", nullable = false, unique = true)
	private Boolean ds_sentido;

	@Column(name = "ds_valor", nullable = false)
	private BigDecimal ds_valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDs_nome() {
		return ds_nome;
	}

	public void setDs_nome(String ds_nome) {
		this.ds_nome = ds_nome;
	}

	public String getDs_linha() {
		return ds_linha;
	}

	public void setDs_linha(String ds_linha) {
		this.ds_linha = ds_linha;
	}

	public Boolean getDs_sentido() {
		return ds_sentido;
	}

	public void setDs_sentido(Boolean ds_sentido) {
		this.ds_sentido = ds_sentido;
	}

}
	