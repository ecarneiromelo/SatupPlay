package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import models.base.BaseModel;

/**
 * The persistent class for the tb_dmn_menu database table.
 *
 */
@Entity
@Table(name = "tb_dmn_menu")
public class MenuBO extends BaseModel {

    private static final long serialVersionUID = 6576160205613787643L;
    @Id
    @Column(unique = true, nullable = false)
    private Long id;
    @Column(name = "menu_order")
    private Short menuOrder;
    @Column(name = "tag_description", length = 200)
    private String tagDescription;
    @Column(name = "tag_name", length = 200)
    private String tagName;
    @Column(length = 256)
    private String url;
    // bi-directional many-to-one association to MenuBO
    @ManyToOne
    @JoinColumn(name = "id_parent_menu")
    private MenuBO parentMenu;
    // bi-directional many-to-one association to MenuBO
    @OrderBy("menuOrder")
    @OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY, cascade = {
            CascadeType.ALL
    })
    private List<MenuBO> lstChildrenMenus;
    // bi-directional many-to-many association to RoleBO
    @ManyToMany(mappedBy = "lstMenus", fetch = FetchType.LAZY)
    private List<RoleBO> lstRoles;

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public MenuBO() {
        super();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Transients.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ...
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public static List<MenuBO> findParentMenusByRoleId(final Long roleId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(" SELECT menu FROM MenuBO menu ");
        builder.append(" JOIN menu.lstRoles roles");
        builder.append(" WHERE menu.parentMenu IS NULL ");
        builder.append(" AND roles.id = ?1 ");
        builder.append(" ORDER BY menu.menuOrder ASC");
        return find(builder.toString(), roleId).fetch();
    }
    public static long countMenuByUrlAndRoleId(final String url, final Long roleId) {
        final StringBuilder builder = new StringBuilder();
        builder.append(" SELECT COUNT(*) FROM MenuBO menu ");
        builder.append(" INNER JOIN menu.lstRoles roles ");
        builder.append(" WHERE menu.url = ?1 ");
        builder.append(" AND roles.id = ?2) ");
        return count(builder.toString(), url, roleId);
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Get/Set
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public Long getId() {
        return this.id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public Short getMenuOrder() {
        return this.menuOrder;
    }
    public void setMenuOrder(final Short menuOrder) {
        this.menuOrder = menuOrder;
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
    public String getUrl() {
        return this.url;
    }
    public void setUrl(final String url) {
        this.url = url;
    }
    public MenuBO getParentMenu() {
        return this.parentMenu;
    }
    public void setParentMenu(final MenuBO parentMenu) {
        this.parentMenu = parentMenu;
    }
    public List<MenuBO> getLstChildrenMenus() {
        return this.lstChildrenMenus;
    }
    public void setLstChildrenMenus(final List<MenuBO> lstChildrenMenus) {
        this.lstChildrenMenus = lstChildrenMenus;
    }
    public MenuBO addLstChildrenMenus(final MenuBO lstChildrenMenus) {
        this.getLstChildrenMenus().add(lstChildrenMenus);
        lstChildrenMenus.setParentMenu(this);
        return lstChildrenMenus;
    }
    public MenuBO removeLstChildrenMenus(final MenuBO lstChildrenMenus) {
        this.getLstChildrenMenus().remove(lstChildrenMenus);
        lstChildrenMenus.setParentMenu(null);
        return lstChildrenMenus;
    }
    public List<RoleBO> getLstRoles() {
        return this.lstRoles;
    }
    public void setLstRoles(final List<RoleBO> lstRoles) {
        this.lstRoles = lstRoles;
    }
}