package models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import common.annotations.JsonExclude;
import common.utils.DateUtil;
import common.utils.DateUtil.DatePattern;
import models.base.BaseModel;
import play.data.validation.Required;

/**
 * The persistent class for the tb_onibus database table.
 *
 */
@Entity
@Table(name = "tb_onibus")
public class OnibusBO extends BaseModel {

    @Id
    @GeneratedValue
    private Long id;
    @Required
    @Column(name = "ds_numero")
    private String dsNumero;
    @Required
    @Column(name = "ds_placa")
    private String dsPlaca;
    // bi-directional many-to-one association to TbLocalizacao
    @JsonExclude
    @OneToMany(mappedBy = "tbOnibus")
    private List<LocalizacaoBO> tbLocalizacaos;
    @JsonExclude
    @ManyToOne
    @JoinColumn(name = "id_linha")
    private LinhaBO tbLinha;
    @Transient
    private String lastLocation;
    @Transient
    private String lastTimeLocation;


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public OnibusBO() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Transients.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void loadLastLocation() {
    	LocalizacaoBO location = LocalizacaoBO.findLastDsPositionByOnibus(this);
    	if(location != null){
    		this.lastLocation = location.getDsLocalizazao();
    		this.lastTimeLocation = DateUtil.parseString(location.getDsDatehora(),DatePattern.SLASH_DD_MM_YYYY_HH_MM);
    	}
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data/Access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static List<OnibusBO> findByLinha(final LinhaBO linha) {
        return find("tbLinha = ?1", linha).fetch();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // get/set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDsNumero() {
        return this.dsNumero;
    }
    public void setDsNumero(String dsNumero) {
        this.dsNumero = dsNumero;
    }
    public String getDsPlaca() {
        return this.dsPlaca;
    }
    public void setDsPlaca(String dsPlaca) {
        this.dsPlaca = dsPlaca;
    }
    public List<LocalizacaoBO> getTbLocalizacaos() {
        return this.tbLocalizacaos;
    }
    public void setTbLocalizacaos(List<LocalizacaoBO> tbLocalizacaos) {
        this.tbLocalizacaos = tbLocalizacaos;
    }
    public LocalizacaoBO addTbLocalizacao(LocalizacaoBO tbLocalizacao) {
        getTbLocalizacaos().add(tbLocalizacao);
        tbLocalizacao.setTbOnibus(this);
        return tbLocalizacao;
    }
    public LocalizacaoBO removeTbLocalizacao(LocalizacaoBO tbLocalizacao) {
        getTbLocalizacaos().remove(tbLocalizacao);
        tbLocalizacao.setTbOnibus(null);
        return tbLocalizacao;
    }
    public LinhaBO getTbLinha() {
        return tbLinha;
    }
    public void setTbLinha(LinhaBO tbLinha) {
        this.tbLinha = tbLinha;
    }
}