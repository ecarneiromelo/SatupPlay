package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import common.annotations.FieldOrder;
import models.base.BaseModel;
import play.data.binding.NoBinding;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.data.validation.Unique;

/**
 * The persistent class for the tb_system_parameter database table.
 *
 */
@Entity
@Table(name = "tb_system_parameter")
public class SystemParameterBO extends BaseModel {

    private static final long serialVersionUID = -3614748301907709781L;
    @NoBinding
    @Id
    private Long id;
    @Required
    @Unique
    @FieldOrder(1)
    @MaxSize(150)
    private String name;
    @Required
    @FieldOrder(2)
    @MaxSize(300)
    private String description;
    @FieldOrder(3)
    @MaxSize(300)
    @Column(name = "PARAM_VALUE")
    private String value;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public SystemParameterBO() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Transients.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ...
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ...
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Get/Set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public Long getId() {
        return this.id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(final String value) {
        this.value = value;
    }
}