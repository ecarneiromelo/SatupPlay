package models;

import java.io.Serializable;
import javax.persistence.*;

import controllers.CRUD;


/**
 * The persistent class for the tb_linha database table.
 * 
 */
@Entity
@Table(name="tb_linha")
public class LinhaBO extends CRUD implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="ds_linha")
	private String dsLinha;

	@Column(name="ds_nome")
	private String dsNome;

	@Column(name="ds_sentido")
	private Boolean dsSentido;

	@Column(name="ds_valor")
	private double dsValor;

	public LinhaBO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDsLinha() {
		return this.dsLinha;
	}

	public void setDsLinha(String dsLinha) {
		this.dsLinha = dsLinha;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public Boolean getDsSentido() {
		return this.dsSentido;
	}

	public void setDsSentido(Boolean dsSentido) {
		this.dsSentido = dsSentido;
	}

	public double getDsValor() {
		return this.dsValor;
	}

	public void setDsValor(double dsValor) {
		this.dsValor = dsValor;
	}

}