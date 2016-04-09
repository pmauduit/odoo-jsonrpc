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

package org.qfast.openerp.rpc.entity;

import com.google.gson.annotations.SerializedName;
import org.qfast.openerp.rpc.boundary.OePartnerService;
import org.qfast.openerp.rpc.util.OeUtil;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Ahmed El-mawaziny
 */
public class OePartner extends AbstractOeEntity<OePartnerService> {

    public static final String _IMAGE_SMALL = "image_small", _EMAIL = "email", _EMPLOYEE = "employee",
            _CHILD_IDS = "child_ids", _PHONE = "phone", _HAS_IMAGE = "has_image", _STREET = "street",
            _TYPE = "type", _CUSTOMER = "customer", _FUNCTION = "function", _CITY = "city", _PARENT_ID = "parent_id",
            _CALENDAR_LAST_NOTIFICATION_ACK = "calendar_last_notif_ack", _STATE_ID = "state_id",
            _CREDIT_LIMIT = "credit_limit", _BANK_IDS = "bank_ids", _SIGNUP_VALID = "signup_valid", _IMAGE = "image",
            _IMAGE_MEDIUM = "image_medium", _FAX = "fax", _SIGNUP_TYPE = "signup_type", _CATEGORY_ID = "category_id",
            _SECTION_ID = "section_id", _NOTIFY_EMAIL = "notify_email", _MESSAGE_IDS = "message_ids", _ACTIVE = "active",
            _COMMENT = "comment", _MESSAGE_IS_FOLLOWER = "message_is_follower", _MOBILE = "mobile",
            _COMPANY_ID = "company_id", _VAT = "vat", _DATE = "date", _LANG = "lang", _USER_IDS = "user_ids",
            _MESSAGE_FOLLOWER_IDS = "message_follower_ids", _BIRTH_DATE = "birthdate", _USER_ID = "user_id",
            _MESSAGE_LAST_POST = "message_last_post", _CONTACT_ADDRESS = "contact_address", _opt_out = "opt_out",
            _SIGNUP_TOKEN = "signup_token", _STREET2 = "street2", _ZIP = "zip", _TZ_OFFSET = "tz_offset",
            _SIGNUP_URL = "signup_url", _WEBSITE = "website", _COUNTRY_ID = "country_id", _IS_COMPANY = "is_company",
            _USE_PARENT_ADDRESS = "use_parent_address", _REF = "ref", _TZ = "tz", _COLOR = "color", _TITLE = "title",
            _MESSAGE_UNREAD = "message_unread", _MESSAGE_SUMMARY = "message_summary", _SUPPLIER = "supplier",
            _COMMERCIAL_PARTNER_ID = "commercial_partner_id", _EAN13 = "ean13", _SIGNUP_EXPIRATION = "signup_expiration";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _IMAGE_SMALL, _EMAIL, _EMPLOYEE, _CHILD_IDS, _PHONE, _HAS_IMAGE, _STREET,
            _TYPE, _CUSTOMER, _FUNCTION, _CITY, _PARENT_ID, _CALENDAR_LAST_NOTIFICATION_ACK, _STATE_ID, _TITLE,
            _CREDIT_LIMIT, _BANK_IDS, _SIGNUP_VALID, _IMAGE, _IMAGE_MEDIUM, _FAX, _SIGNUP_TYPE, _CATEGORY_ID,
            _SECTION_ID, _NOTIFY_EMAIL, _MESSAGE_IDS, _ACTIVE, _COMMENT, _MESSAGE_IS_FOLLOWER, _MOBILE, _SUPPLIER,
            _COMPANY_ID, _VAT, _DATE, _LANG, _USER_IDS, _MESSAGE_FOLLOWER_IDS, _BIRTH_DATE, _USER_ID, _MESSAGE_LAST_POST,
            _CONTACT_ADDRESS, _opt_out, _SIGNUP_TOKEN, _STREET2, _ZIP, _TZ_OFFSET, _SIGNUP_URL, _WEBSITE, _COUNTRY_ID,
            _IS_COMPANY, _USE_PARENT_ADDRESS, _REF, _TZ, _COLOR, _MESSAGE_UNREAD, _MESSAGE_SUMMARY, _SIGNUP_EXPIRATION,
            _COMMERCIAL_PARTNER_ID, _EAN13};

    private static final long serialVersionUID = -4327707202428848696L;

    private String imageSmall;
    private String imageMedium;
    private String image;
    private String email;
    private Boolean employee;
    private Integer[] childIds;
    private Integer[] bankIds;
    private String phone;
    private Boolean hasImage;
    private String street;
    private String type;
    private Boolean customer;
    private String function;
    private String city;
    @SerializedName(_CALENDAR_LAST_NOTIFICATION_ACK)
    private Date calendarLastNotificationAck;
    private Integer stateId;
    private BigDecimal creditLimit;
    private Date signupValid;
    private Date signupExpiration;
    private Boolean supplier;
    private Integer parentId;
    private String fax;
    private String signupType;
    private Object[] categoryId;
    private Integer sectionId;
    private String notifyEmail;
    private Integer[] messageIds;
    private Boolean active;
    private String comment;
    private Boolean messageIsFollower;
    private String mobile;
    private Object[] companyId;
    private String vat;
    private Date date;
    private String lang;
    private Integer[] userIds;
    private String title;
    private Integer[] messageFollowerIds;
    @SerializedName(_BIRTH_DATE)
    private Date birthDate;
    private Integer userId;
    private Date messageLastPost;
    private String contactAddress;
    private String signupToken;
    private Boolean optOut;
    private String street2;
    private String zip;
    private String tzOffset;
    private String signupUrl;
    private String website;
    private Integer countryId;
    private Boolean useParentAddress;
    private String ref;
    private String tz;
    private Integer color;
    private Boolean isCompany;
    private Boolean messageUnread;
    private String messageSummary;
    private Object[] commercialPartnerId;
    private String ean13;

    public OePartner() {
    }

    public OePartner(OePartnerService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer[] getChildIds() {
        return childIds;
    }

    public void setChildIds(Integer[] childIds) {
        this.childIds = childIds;
    }

    public Integer[] getBankIds() {
        return bankIds;
    }

    public void setBankIds(Integer[] bankIds) {
        this.bankIds = bankIds;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCalendarLastNotificationAck() {
        return calendarLastNotificationAck;
    }

    public void setCalendarLastNotificationAck(Date calendarLastNotificationAck) {
        this.calendarLastNotificationAck = calendarLastNotificationAck;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Date getSignupValid() {
        return signupValid;
    }

    public void setSignupValid(Date signupValid) {
        this.signupValid = signupValid;
    }

    public Date getSignupExpiration() {
        return signupExpiration;
    }

    public void setSignupExpiration(Date signupExpiration) {
        this.signupExpiration = signupExpiration;
    }

    public Boolean getSupplier() {
        return supplier;
    }

    public void setSupplier(Boolean supplier) {
        this.supplier = supplier;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getSignupType() {
        return signupType;
    }

    public void setSignupType(String signupType) {
        this.signupType = signupType;
    }

    public Object[] getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object[] categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getNotifyEmail() {
        return notifyEmail;
    }

    public void setNotifyEmail(String notifyEmail) {
        this.notifyEmail = notifyEmail;
    }

    public Integer[] getMessageIds() {
        return messageIds;
    }

    public void setMessageIds(Integer[] messageIds) {
        this.messageIds = messageIds;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getMessageIsFollower() {
        return messageIsFollower;
    }

    public void setMessageIsFollower(Boolean messageIsFollower) {
        this.messageIsFollower = messageIsFollower;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Object[] getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object[] companyId) {
        this.companyId = companyId;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Integer[] userIds) {
        this.userIds = userIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer[] getMessageFollowerIds() {
        return messageFollowerIds;
    }

    public void setMessageFollowerIds(Integer[] messageFollowerIds) {
        this.messageFollowerIds = messageFollowerIds;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getMessageLastPost() {
        return messageLastPost;
    }

    public void setMessageLastPost(Date messageLastPost) {
        this.messageLastPost = messageLastPost;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public String getSignupToken() {
        return signupToken;
    }

    public void setSignupToken(String signupToken) {
        this.signupToken = signupToken;
    }

    public Boolean getOptOut() {
        return optOut;
    }

    public void setOptOut(Boolean optOut) {
        this.optOut = optOut;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTzOffset() {
        return tzOffset;
    }

    public void setTzOffset(String tzOffset) {
        this.tzOffset = tzOffset;
    }

    public String getSignupUrl() {
        return signupUrl;
    }

    public void setSignupUrl(String signupUrl) {
        this.signupUrl = signupUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Boolean getUseParentAddress() {
        return useParentAddress;
    }

    public void setUseParentAddress(Boolean useParentAddress) {
        this.useParentAddress = useParentAddress;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Boolean getCompany() {
        return isCompany;
    }

    public void setCompany(Boolean company) {
        isCompany = company;
    }

    public Boolean getMessageUnread() {
        return messageUnread;
    }

    public void setMessageUnread(Boolean messageUnread) {
        this.messageUnread = messageUnread;
    }

    public String getMessageSummary() {
        return messageSummary;
    }

    public void setMessageSummary(String messageSummary) {
        this.messageSummary = messageSummary;
    }

    public Object[] getCommercialPartnerId() {
        return commercialPartnerId;
    }

    public void setCommercialPartnerId(Object[] commercialPartnerId) {
        this.commercialPartnerId = commercialPartnerId;
    }

    public String getEan13() {
        return ean13;
    }

    public void setEan13(String ean13) {
        this.ean13 = ean13;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() != obj.getClass() && OeUtil.equals(this.id, ((OePartner) obj).id);
    }

    @Override
    public String toString() {
        return "OePartner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", imageSmall='" + imageSmall + '\'' +
                ", imageMedium='" + imageMedium + '\'' +
                ", image='" + image + '\'' +
                ", email='" + email + '\'' +
                ", employee=" + employee +
                ", childIds=" + Arrays.toString(childIds) +
                ", bankIds=" + Arrays.toString(bankIds) +
                ", phone='" + phone + '\'' +
                ", hasImage=" + hasImage +
                ", street='" + street + '\'' +
                ", type='" + type + '\'' +
                ", customer=" + customer +
                ", function='" + function + '\'' +
                ", city='" + city + '\'' +
                ", calendarLastNotificationAck=" + calendarLastNotificationAck +
                ", stateId=" + stateId +
                ", creditLimit=" + creditLimit +
                ", signupValid=" + signupValid +
                ", signupExpiration=" + signupExpiration +
                ", supplier=" + supplier +
                ", parentId=" + parentId +
                ", fax='" + fax + '\'' +
                ", signupType='" + signupType + '\'' +
                ", categoryId=" + Arrays.toString(categoryId) +
                ", sectionId=" + sectionId +
                ", notifyEmail='" + notifyEmail + '\'' +
                ", messageIds=" + Arrays.toString(messageIds) +
                ", active=" + active +
                ", comment='" + comment + '\'' +
                ", messageIsFollower=" + messageIsFollower +
                ", mobile='" + mobile + '\'' +
                ", companyId=" + Arrays.toString(companyId) +
                ", vat='" + vat + '\'' +
                ", date=" + date +
                ", lang='" + lang + '\'' +
                ", userIds=" + userIds +
                ", title='" + title + '\'' +
                ", messageFollowerIds=" + Arrays.toString(messageFollowerIds) +
                ", birthDate=" + birthDate +
                ", userId=" + userId +
                ", messageLastPost=" + messageLastPost +
                ", contactAddress='" + contactAddress + '\'' +
                ", signupToken='" + signupToken + '\'' +
                ", optOut=" + optOut +
                ", street2='" + street2 + '\'' +
                ", zip='" + zip + '\'' +
                ", tzOffset='" + tzOffset + '\'' +
                ", signupUrl='" + signupUrl + '\'' +
                ", website='" + website + '\'' +
                ", countryId=" + countryId +
                ", useParentAddress=" + useParentAddress +
                ", ref='" + ref + '\'' +
                ", tz='" + tz + '\'' +
                ", color=" + color +
                ", isCompany=" + isCompany +
                ", messageUnread=" + messageUnread +
                ", messageSummary='" + messageSummary + '\'' +
                ", commercialPartnerId=" + Arrays.toString(commercialPartnerId) +
                ", ean13='" + ean13 + '\'' +
                '}';
    }
}
