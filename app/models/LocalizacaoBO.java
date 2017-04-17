package models;

import java.io.Serializable;
import javax.persistence.*;

import common.annotations.JsonExclude;
import controllers.CRUD;
import play.data.validation.Required;
import play.db.jpa.Model;

import java.sql.Timestamp;


/**
 * The persistent class for the tb_localizacao database table.
 * 
 */
@Entity
@Table(name="tb_localizacao")
public class LocalizacaoBO extends Model {
	@Required
	@Column(name="ds_datehora")
	private Timestamp dsDatehora;
	
	@Required
	@Column(name="ds_localizazao")
	private String dsLocalizazao;

	//bi-directional many-to-one association to TbOnibus
	@JsonExclude
	@ManyToOne
	@JoinColumn(name="id_onibus")
	private OnibusBO tbOnibus;

	public LocalizacaoBO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDsDatehora() {
		return this.dsDatehora;
	}

	public void setDsDatehora(Timestamp dsDatehora) {
		this.dsDatehora = dsDatehora;
	}

	public String getDsLocalizazao() {
		return this.dsLocalizazao;
	}

	public void setDsLocalizazao(String dsLocalizazao) {
		this.dsLocalizazao = dsLocalizazao;
	}

	public OnibusBO getTbOnibus() {
		return this.tbOnibus;
	}

	public void setTbOnibus(OnibusBO tbOnibus) {
		this.tbOnibus = tbOnibus;
	}

}