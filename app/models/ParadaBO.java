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

import play.db.jpa.Model;

@Entity
@Table(name = "db_parada" , schema = "public")
public class ParadaBO extends Model {

	@Id
	@Column(unique = true, nullable = false)	
	private Long id ;
	
	@Column(name = "ds_numero", unique = true, nullable = false, length = 20)
	private String ds_numero;
	
	@Column(name = "ds_nome", nullable = false, length = 30)
	private String ds_nome;
	
	@Column(name = "ds_posicao" , unique = true, nullable = false, length = 23)
	private String ds_posicao;
	
	@ManyToMany
	private List <LinhaBO> linhas ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<LinhaBO> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<LinhaBO> linhas) {
		this.linhas = linhas;
	}
	
	
}
