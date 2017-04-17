package models;

import static common.constants.SystemConstants.MAX_LOGIN_ATTEMPTS;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import common.annotations.FieldOrder;
import common.constants.DomainConstants.UserStatus;
import models.base.BaseModel;
import play.data.binding.NoBinding;
import play.data.validation.Email;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Password;
import play.data.validation.Required;
import play.data.validation.Unique;

/**
 * The persistent class for the tb_user database table.
 *
 */
@Entity
@Table(name = "tb_user")
public class UserBO extends BaseModel {

    private static final long serialVersionUID = 7802193918931763355L;
    @Id
    @GeneratedValue
    private Long id;
    @FieldOrder(1)
    @Required
    @MaxSize(100)
    private String name;
    @FieldOrder(2)
    @Required
    @Unique
    @Email
    @MaxSize(100)
    private String email;
    @FieldOrder(3)
    @Password
    @MaxSize(256)
    @MinSize(6)
    private String pass;
    @FieldOrder(4)
    @Transient
    @MaxSize(256)
    @MinSize(6)
    private String newPass;
    @FieldOrder(5)
    @Transient
    @MaxSize(256)
    @MinSize(6)
    private String checkPass;
    @NoBinding
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
    @NoBinding
    @Column(name = "LOGIN_ATTEMPTS", columnDefinition = "numeric(2,0)")
    private Short loginAttempts = 0;
 
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Constructors.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public UserBO() {
        super();
        this.setStatus(UserStatus.ACTIVE);
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Transients.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ...
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Data access
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ...
    public static UserBO findByEmail(final String email) {
        return find("email = ?1", email).first();
    }
    public void updateAccessAttempts(final boolean authenticated) {
        if (authenticated) {
            this.setLoginAttempts((short) 0);
        } else {
            
            final Long attemptNumberParam = MAX_LOGIN_ATTEMPTS;
            if (attemptNumberParam <= 0) {
                return;
            }
            Short attemptNumber = this.getLoginAttempts();
            if (attemptNumber < attemptNumberParam) {
                this.setLoginAttempts(++attemptNumber);
            } else {
                this.setStatus(UserStatus.BLOCKED);
            }
        }
        this.save();
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Get/Set.
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public Long getId() {
        return this.id;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public UserStatus getStatus() {
        return this.status;
    }
    public void setStatus(final UserStatus status) {
        this.status = status;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(final String email) {
        this.email = email;
    }
    public Short getLoginAttempts() {
        return this.loginAttempts;
    }
    public void setLoginAttempts(final Short loginAttempts) {
        this.loginAttempts = loginAttempts;
    }
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public String getPass() {
        return this.pass;
    }
    public void setPass(final String pass) {
        this.pass = pass;
    }
    public String getNewPass() {
        return this.newPass;
    }
    public void setNewPass(final String newPass) {
        this.newPass = newPass;
    }
    public String getCheckPass() {
        return this.checkPass;
    }
    public void setCheckPass(final String checkPass) {
        this.checkPass = checkPass;
    }
    public boolean checkPasswords() {
        return this.pass.equals(this.checkPass);
    }
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // * @see play.db.jpa.JPABase#toString()
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    @Override
    public String toString() {
        return this.getName();
    }
}