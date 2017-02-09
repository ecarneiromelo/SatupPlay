package models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "db_localizacao")
@SequenceGenerator(name = "db_localizacao_id_localizacao_seq", sequenceName = "public.db_localizacao_id_localizacao_seq")
public class Localizacao extends Model{

	@Id
	@GeneratedValue
	@Column(name = "id_localizacao", unique = true, nullable = false)
	private Integer id_localizacao;

	@Column(name = "ds_localizacao", nullable = false)
	private String ds_localizacao;

	@Column(name = "ds_datahora")
	private Timestamp ds_dataHora;
	
	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="id_onibus", nullable = false)
    private Onibus onibus;

	public Integer getId_localizacao() {
		return id_localizacao;
	}

	public void setId_localizacao(Integer id_localizacao) {
		this.id_localizacao = id_localizacao;
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

