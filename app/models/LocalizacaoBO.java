package models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "db_localizacao")
public class LocalizacaoBO extends Model{

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "ds_localizacao", nullable = false)
	private String ds_localizacao;

	@Column(name = "ds_datahora")
	private Timestamp ds_dataHora;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER)
	@JoinColumns({
	@JoinColumn(name = "id_localizacao", referencedColumnName= "id_localizacao", nullable = false),
	@JoinColumn(name="id_onibus", referencedColumnName="id_onibus", nullable = false)})
    private OnibusBO onibus;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OnibusBO getOnibus() {
		return onibus;
	}

	public void setOnibus(OnibusBO onibus) {
		this.onibus = onibus;
	}

	public String getDs_localizacao() {
		return ds_localizacao;
	}

	public void setDs_localizacao(String ds_localizacao) {
		this.ds_localizacao = ds_localizacao;
	}

	public Timestamp getDs_dataHora() {
		return ds_dataHora;
	}

	public void setDs_dataHora(Timestamp ds_dataHora) {
		this.ds_dataHora = ds_dataHora;
	}
}

