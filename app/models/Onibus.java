package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import play.db.jpa.Model;

@Entity
@Table(name = "db_onibus", schema = "public")
@SequenceGenerator(name = "db_onibus_id_onibus_seq", sequenceName = "public.db_onibus_id_onibus_seq")
public class Onibus extends Model {

	@Id
	@GeneratedValue
	@Column(name = "id_onibus", unique = true, nullable = false)
	private Integer id_onibus;

	@Column(name = "ds_numero", nullable = false, length = 16)
	private String ds_numero;

	@Column(name = "ds_placa", nullable = false, unique = true, length = 8)
	private String ds_placa;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER )
	private List<Localizacao> listLocalizacao;
	
	public Integer getId_onibus() {
		return id_onibus;
	}

	public void setId_onibus(Integer id_onibus) {
		this.id_onibus = id_onibus;
	}

	public String getDs_numero() {
		return ds_numero;
	}

	public void setDs_numero(String ds_numero) {
		this.ds_numero = ds_numero;
	}

	public String getDs_placa() {
		return ds_placa;
	}

	public void setDs_placa(String ds_placa) {
		this.ds_placa = ds_placa;
	}

}
