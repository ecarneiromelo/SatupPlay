package models;

import java.io.Serializable;
import javax.persistence.*;

import controllers.CRUD;
import play.db.jpa.Model;


/**
 * The persistent class for the tb_parada database table.
 * 
 */
@Entity
@Table(name="tb_parada")
public class ParadaBO extends Model {

	@Column(name="ds_nome")
	private String dsNome;

	@Column(name="ds_numero")
	private String dsNumero;

	@Column(name="ds_posicao")
	private String dsPosicao;

	public ParadaBO() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsNumero() {
		return this.dsNumero;
	}

	public void setDsNumero(String dsNumero) {
		this.dsNumero = dsNumero;
	}

	public String getDsPosicao() {
		return this.dsPosicao;
	}

	public void setDsPosicao(String dsPosicao) {
		this.dsPosicao = dsPosicao;
	}

}