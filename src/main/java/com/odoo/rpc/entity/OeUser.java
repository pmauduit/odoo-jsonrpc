/*
 * Copyright 2016 QFast
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.odoo.rpc.entity;

import com.google.gson.annotations.SerializedName;
import com.odoo.rpc.boundary.OeUserService;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.util.OeUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Ahmed El-mawaziny
 */
public class OeUser extends AbstractOeEntity<OeUserService> {

    public static final String _LOGIN = "login", _PASSWORD = "password", _PASSWORD_CRYPT = "password_crypt",
            _IMAGE = "image", _IMAGE_MEDIUM = "image_medium", _IMAGE_SMALL = "image_small", _LOGIN_DATE = "login_date",
            _ACTIVE = "active", _LANG = "lang", _SIGNATURE = "signature", _TZ_OFFSET = "tz_offset", _TZ = "tz",
            _EMAIL = "email", _USER_ID = "user_id", _GROUPS_ID = "groups_id", _PARTNER_ID = "partner_id",
            _COMPANY_ID = "company_id", _SUPPLIER = "supplier", _CUSTOMER = "customer", _HAS_IMAGE = "has_image";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _LOGIN, _PASSWORD, _PASSWORD_CRYPT, _IMAGE, _IMAGE_MEDIUM, _IMAGE_SMALL,
            _LOGIN_DATE, _ACTIVE, _LANG, _SIGNATURE, _TZ_OFFSET, _TZ, _EMAIL, _USER_ID, _GROUPS_ID, _PARTNER_ID,
            _COMPANY_ID, _SUPPLIER, _CUSTOMER, _HAS_IMAGE};

    private static final long serialVersionUID = 4881734215550044357L;

    @SerializedName(_LOGIN)
    private String username;
    private String password;
    private String passwordCrypt;
    private String image;
    private String imageMedium;
    private String imageSmall;
    private String signature;
    private String tzOffset;
    private String tz;
    private String email;
    private String lang;
    private Integer userId;
    private Integer[] groupsId;
    private Object[] partnerId;
    private Object[] parentId;
    private Object[] companyId;
    private Date loginDate;
    private List<OeMenu> menus;
    private Locale locale;
    private OeLanguage language;
    private boolean supplier;
    private boolean customer;
    private boolean employee;
    private boolean hasImage;
    private boolean active;

    public OeUser() {
    }

    public OeUser(OeUserService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public Locale getLocale() {
        if (locale == null) {
            locale = OeUtil.getLocale(lang);
        }
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public OeLanguage getLanguage() throws OeRpcException {
        getLocale();
        if ((language == null && locale != null) || (language != null && !OeUtil.equals(language.getLocale(), locale))) {
            language = oe.findLanguageByCode(locale.toString());
        }
        return language;
    }

    public void setLanguage(OeLanguage language) {
        this.language = language;
    }

    public List<OeMenu> getMenus() throws OeRpcException {
        if (id != null && menus == null) {
            menus = oe.findMenusByUserIdAllInOne(id);
        }
        return menus;
    }

    public void setMenus(List<OeMenu> menus) {
        this.menus = menus;
    }

    public String getPasswordCrypt() {
        return passwordCrypt;
    }

    public void setPasswordCrypt(String passwordCrypt) {
        this.passwordCrypt = passwordCrypt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTzOffset() {
        return tzOffset;
    }

    public void setTzOffset(String tzOffset) {
        this.tzOffset = tzOffset;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer[] getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(Integer[] groupsId) {
        this.groupsId = groupsId;
    }

    public Object[] getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Object[] partnerId) {
        this.partnerId = partnerId;
    }

    public Object[] getParentId() {
        return parentId;
    }

    public void setParentId(Object[] parentId) {
        this.parentId = parentId;
    }

    public Object[] getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object[] companyId) {
        this.companyId = companyId;
    }

    public boolean isSupplier() {
        return supplier;
    }

    public void setSupplier(boolean supplier) {
        this.supplier = supplier;
    }

    public boolean isCustomer() {
        return customer;
    }

    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

    public boolean isEmployee() {
        return employee;
    }

    public void setEmployee(boolean employee) {
        this.employee = employee;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() != obj.getClass() && OeUtil.equals(this.id, ((OeUser) obj).id);
    }

    @Override
    public String toString() {
        return "OeUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", active=" + active +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordCrypt='" + passwordCrypt + '\'' +
                ", image='" + image + '\'' +
                ", imageMedium='" + imageMedium + '\'' +
                ", imageSmall='" + imageSmall + '\'' +
                ", signature='" + signature + '\'' +
                ", tzOffset='" + tzOffset + '\'' +
                ", tz='" + tz + '\'' +
                ", email='" + email + '\'' +
                ", lang='" + lang + '\'' +
                ", userId=" + userId +
                ", groupsId=" + Arrays.toString(groupsId) +
                ", partnerId=" + Arrays.toString(partnerId) +
                ", parentId=" + Arrays.toString(parentId) +
                ", companyId=" + Arrays.toString(companyId) +
                ", loginDate=" + loginDate +
                ", menus=" + menus +
                ", locale=" + locale +
                ", language=" + language +
                ", supplier=" + supplier +
                ", customer=" + customer +
                ", employee=" + employee +
                ", hasImage=" + hasImage +
                '}';
    }
}
