package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "db_parada" , schema = "public")
@SequenceGenerator( name = "db_parada_id_parada_seq", sequenceName = "public.db_parada_id_parada_seq")
public class Parada {

	@Id
	@Column(name = "id_parada", unique = true, nullable = false)	
	private Integer id_parada ;
	
	@Column(name = "ds_numero", unique = true, nullable = false, length = 20)
	private String ds_numero;
	
	@Column(name = "ds_nome", nullable = false, length = 30)
	private String ds_nome;
	
	@Column(name = "ds_posicao" , unique = true, nullable = false, length = 23)
	private String ds_posicao;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "tb_linha", schema = "public", joinColumns = { @JoinColumn(name = "id_linha", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "id_parada", nullable = false, updatable = false) })
	private List <Linha> linhas ;

	public Integer getId_parada() {
		return id_parada;
	}

	public void setId_parada(Integer id_parada) {
		this.id_parada = id_parada;
	}

	public String getDs_numero() {
		return ds_numero;
	}

	public void setDs_numero(String ds_numero) {
		this.ds_numero = ds_numero;
	}

	public String getDs_nome() {
		return ds_nome;
	}

	public void setDs_nome(String ds_nome) {
		this.ds_nome = ds_nome;
	}

	public String getDs_posicao() {
		return ds_posicao;
	}

	public void setDs_posicao(String ds_posicao) {
		this.ds_posicao = ds_posicao;
	}

	public List<Linha> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Linha> linhas) {
		this.linhas = linhas;
	}
	
	
}
