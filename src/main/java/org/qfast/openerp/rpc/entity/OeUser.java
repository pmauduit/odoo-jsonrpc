/*
 * Copyright 2014 QFast Ahmed El-mawaziny.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qfast.openerp.rpc.entity;

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.qfast.openerp.rpc.boundary.OeUserService;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public class OeUser extends AbstractOeEntity<OeUserService> {
    /*
     has_image, street, customer, city, __last_update, state_id, create_date, 
     supplier, signature, login_date, parent_id, partner_id, 
     alias_force_thread_id, alias_id, image, notify_email, display_name, comment,
     message_is_follower, action_id, property_account_receivable, create_uid, 
     invoice_ids, date, write_date, user_ids, title, alias_contact, signup_token,
     opt_out, alias_domain, contract_ids, signup_url, country_id, ref, tz, 
     message_unread, ean13, child_ids, phone, total_invoiced, employee_ids, 
     alias_user_id, display_employees_suggestions, type, password, 
     alias_parent_thread_id, function, calendar_last_notif_ack, credit_limit, 
     contracts_count, display_groups_suggestions, login, credit, alias_defaults, 
     signup_valid, bank_ids, write_uid, image_medium, fax, signup_expiration, 
     signup_type, category_id, company_ids, alias_name, message_ids, email, 
     active, groups_id, mobile, company_id, property_supplier_payment_term, 
     debit_limit, last_reconciliation_date, alias_model_id, state, vat, 
     journal_item_count, image_small, vat_subjected, lang, alias_parent_model_id,
     id, message_follower_ids, property_account_position, share, ref_companies, 
     name, birthdate, user_id, debit, contact_address, message_last_post, 
     employee, property_payment_term, street2, zip, tz_offset, website, 
     use_parent_address, property_account_payable, color, is_company, 
     new_password, property_product_pricelist, message_summary, 
     commercial_partner_id
     */

    public static final String _LOGIN = "login", _PASSWORD = "password",
            _LOGIN_DATE = "login_date", _ACTIVE = "active", _LANG = "lang";

    private Integer id;
    @SerializedName(_LOGIN)
    private String username;
    private String password;
    private String newPassword;
    private String passwordCrypt;
    private String image;
    private String imageMedium;
    private String imageSmall;
    private String displayName;
    private String name;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if ((language == null && locale != null)
                || (language != null && !OeUtil.equals(language.getLocale(), locale))) {
            language = oe.findLangaugeByCode(locale.toString());
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return obj != null
                && getClass() != obj.getClass()
                && OeUtil.equals(this.id, ((OeUser) obj).id);
    }

    @Override
    public String toString() {
        return "OeUser{" + "id=" + id
                + ", username=" + username
                + ", password=" + password
                + ", newPassword=" + newPassword
                + ", passwordCrypt=" + passwordCrypt
                + ", image=" + image
                + ", imageMedium=" + imageMedium
                + ", imageSmall=" + imageSmall
                + ", displayName=" + displayName
                + ", name=" + name
                + ", signature=" + signature
                + ", tzOffset=" + tzOffset
                + ", tz=" + tz
                + ", email=" + email
                + ", lang=" + lang
                + ", userId=" + userId
                + ", groupsId=" + Arrays.toString(groupsId)
                + ", partnerId=" + Arrays.toString(partnerId)
                + ", parentId=" + Arrays.toString(parentId)
                + ", companyId=" + Arrays.toString(companyId)
                + ", loginDate=" + loginDate
                + ", menus=" + menus
                + ", locale=" + locale
                + ", language=" + language
                + ", supplier=" + supplier
                + ", customer=" + customer
                + ", employee=" + employee
                + ", hasImage=" + hasImage
                + ", active=" + active + '}';
    }

    private static final long serialVersionUID = 4881734215550044357L;
}
