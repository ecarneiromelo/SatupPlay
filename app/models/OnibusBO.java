package models;

import java.io.Serializable;
import javax.persistence.*;

import controllers.CRUD;

import java.util.List;


/**
 * The persistent class for the tb_onibus database table.
 * 
 */
@Entity
@Table(name="tb_onibus")
public class OnibusBO extends CRUD implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="ds_numero")
	private String dsNumero;

	@Column(name="ds_placa")
	private String dsPlaca;

	//bi-directional many-to-one association to TbLocalizacao
	@OneToMany(mappedBy="tbOnibus")
	private List<LocalizacaoBO> tbLocalizacaos;

	public OnibusBO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDsNumero() {
		return this.dsNumero;
	}

	public void setDsNumero(String dsNumero) {
		this.dsNumero = dsNumero;
	}

	public String getDsPlaca() {
		return this.dsPlaca;
	}

	public void setDsPlaca(String dsPlaca) {
		this.dsPlaca = dsPlaca;
	}

	public List<LocalizacaoBO> getTbLocalizacaos() {
		return this.tbLocalizacaos;
	}

	public void setTbLocalizacaos(List<LocalizacaoBO> tbLocalizacaos) {
		this.tbLocalizacaos = tbLocalizacaos;
	}

	public LocalizacaoBO addTbLocalizacao(LocalizacaoBO tbLocalizacao) {
		getTbLocalizacaos().add(tbLocalizacao);
		tbLocalizacao.setTbOnibus(this);

		return tbLocalizacao;
	}

	public LocalizacaoBO removeTbLocalizacao(LocalizacaoBO tbLocalizacao) {
		getTbLocalizacaos().remove(tbLocalizacao);
		tbLocalizacao.setTbOnibus(null);

		return tbLocalizacao;
	}

}