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

@Entity
@Table(name = "db_linha", schema = "public")
@SequenceGenerator(name = "db_linha_id_linha_seq", sequenceName = "public.db_linha_id_linha_seq")
public class Linha {
	@Id
	@GeneratedValue
	@Column(name = "id_linha", nullable = false, unique = true, length = 200)
	private Integer id_linha;

	@Column(name = "ds_nome", nullable = false, unique = true, length = 10)
	private String ds_nome;

	@Column(name = "ds_linha", nullable = false, unique = true)
	private String ds_linha;

	@Column(name = "ds_sentido", nullable = false, unique = true)
	private Boolean ds_sentido;

	@Column(name = "ds_valor", nullable = false)
	private BigDecimal ds_valor;

	public Integer getId_linha() {
		return id_linha;
	}

	public void setId_linha(Integer id_linha) {
		this.id_linha = id_linha;
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

	public BigDecimal getDs_valor() {
		return ds_valor;
	}

	public void setDs_valor(BigDecimal ds_valor) {
		this.ds_valor = ds_valor;
	}

}
