package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import common.annotations.JsonExclude;
import models.base.BaseModel;
import play.data.validation.Required;

/**
 * The persistent class for the tb_localizacao database table.
 *
 */
@Entity
@Table(name = "tb_localizacao")
public class LocalizacaoBO extends BaseModel {

    @Id
    @GeneratedValue
    private Long id;
    @Required
    @Column(name = "ds_datehora")
    private Timestamp dsDatehora;
    @Required
    @Column(name = "ds_localizazao")
    private String dsLocalizazao;
    // bi-directional many-to-one association to TbOnibus
    @JsonExclude
    @ManyToOne
    @JoinColumn(name = "id_onibus")
    private OnibusBO tbOnibus;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data/Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static String findLastDsPositionByOnibus(final OnibusBO onibus) {
        LocalizacaoBO obj = find("tbOnibus.id = ?1 ORDER BY dsDatehora DESC", onibus.getId()).first();
        if (obj != null) {
            return obj.getDsLocalizazao();
        }
        return null;
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // get/set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
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