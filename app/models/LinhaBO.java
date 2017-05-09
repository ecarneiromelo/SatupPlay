package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import common.annotations.JsonExclude;
import models.base.BaseModel;
import play.data.validation.Required;

/**
 * The persistent class for the tb_linha database table.
 *
 */
@Entity
@Table(name = "tb_linha")
public class LinhaBO extends BaseModel {

    @Id
    @GeneratedValue
    private Long id;
    @Required
    @Column(name = "ds_linha")
    private String dsLinha;
    @Required
    @Column(name = "ds_nome")
    private String dsNome;
    @Required
    @Column(name = "ds_sentido")
    private Boolean dsSentido;
    @Required
    @Column(name = "ds_valor")
    private double dsValor;
    @JsonExclude
    @OneToMany(mappedBy = "tbLinha")
    private List<OnibusBO> tbOnibus;

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
    public List<OnibusBO> getTbOnibus() {
        return tbOnibus;
    }
    public void setTbOnibus(List<OnibusBO> tbOnibus) {
        this.tbOnibus = tbOnibus;
    }
}