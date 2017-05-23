package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import common.annotations.JsonExclude;
import models.base.BaseModel;
import play.data.validation.Required;

/**
 * The persistent class for the tb_parada database table.
 *
 */
@Entity
@Table(name = "tb_parada")
public class ParadaBO extends BaseModel {

    @Id
    @GeneratedValue
    private Long id;
    @Required
    @Column(name = "ds_nome")
    private String dsNome;
    @Required
    @Column(name = "ds_numero")
    private String dsNumero;
    @Required
    @Column(name = "ds_posicao")
    private String dsPosicao;
    @JsonExclude
    @ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "rl_linha_parada", joinColumns = { @JoinColumn(name = "id_parada") }, inverseJoinColumns = { @JoinColumn(name = "id_linha") })
	private List<LinhaBO> lstTbLinha;
 // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data/Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static List<ParadaBO> findParadaByLinha(final Long id) {
        final StringBuilder builder = new StringBuilder();
        builder.append(" SELECT parada FROM ParadaBO parada ");
        builder.append(" INNER JOIN parada.lstTbLinha tbLinha");
        builder.append(" WHERE tbLinha.id = ?1 ");   
        return find(builder.toString(), id).fetch();
    }
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // get /set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public ParadaBO() {
        super();
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
    public List<LinhaBO> getlstTbLinha() {
		return lstTbLinha;
	}
	public void setlstTbLinha(List<LinhaBO> tbLinha) {
		this.lstTbLinha = tbLinha;
	}
	@Override
	public String toString() {
		return dsNumero +" - " + dsNome;
	}
	
}