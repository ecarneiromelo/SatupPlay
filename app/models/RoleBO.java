package models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import models.base.BaseModel;

/**
 * The persistent class for the tb_dmn_role database table.
 *
 */
@Entity
@Table(name = "tb_dmn_role")
public class RoleBO extends BaseModel {

    private static final long serialVersionUID = -4657580474458952955L;
    @Id
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(name = "tag_description", length = 300)
    private String tagDescription;
    @Column(name = "tag_name", length = 100)
    private String tagName;
    // bi-directional many-to-many association to MenuBO
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rl_role_menu", joinColumns = {
            @JoinColumn(name = "id_role", nullable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "id_menu", nullable = false)
    })
    private List<MenuBO> lstMenus;
    // bi-directional many-to-many association to UserBO
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserBO> lstUsers;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public RoleBO() {
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
    public String getTagDescription() {
        return this.tagDescription;
    }
    public void setTagDescription(final String tagDescription) {
        this.tagDescription = tagDescription;
    }
    public String getTagName() {
        return this.tagName;
    }
    public void setTagName(final String tagName) {
        this.tagName = tagName;
    }
    public List<MenuBO> getLstMenus() {
        return this.lstMenus;
    }
    public void setLstMenus(final List<MenuBO> lstMenus) {
        this.lstMenus = lstMenus;
    }
    public List<UserBO> getLstUsers() {
        return this.lstUsers;
    }
    public void setLstUsers(final List<UserBO> lstUsers) {
        this.lstUsers = lstUsers;
    }
}