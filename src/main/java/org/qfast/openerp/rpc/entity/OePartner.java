/*
 * Copyright 2016 QFast Ahmed El-mawaziny
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

import org.qfast.openerp.rpc.OeConst;
import org.qfast.openerp.rpc.boundary.OePartnerService;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Date;

/**
 * @author Ahmed El-mawaziny
 */
public class OePartner extends AbstractOeEntity<OePartnerService> {

    public static final String _ID = OeConst._COL_ID, _NAME = OeConst._COL_NAME, _PARTNER_CODE = "partner_code",
            _ACCOUNT_ID = "account_id", _IMAGE_SMALL = "image_small", _FIRST_NAME = "first_name",
            _SECOND_NAME = "second_name", _SURNAME = "surname", _EMAIL = "email", _DISPLAY_NAME = "display_name",
            _YEARLY_LIMIT = "yearly_limit", _MONTHLY_LIMIT = "monthly_limit", _LATITUDE = "latitude",
            _LONGITUDE = "longitude", _SERVICE_PROVIDER = "service_provider", _EMPLOYEE = "employee",
            _VALID_FROM = "valid_from", _VALID_TO = "valid_to", _PASSWORD = "partner_password";
    public static final String[] COLUMNS = new String[]{_ID, _NAME, _PARTNER_CODE, _ACCOUNT_ID, _IMAGE_SMALL,
            _FIRST_NAME, _SECOND_NAME, _SURNAME, _EMAIL, _DISPLAY_NAME, _YEARLY_LIMIT, _MONTHLY_LIMIT, _LATITUDE,
            _LONGITUDE, _SERVICE_PROVIDER, _EMPLOYEE, _VALID_FROM, _VALID_TO, _PASSWORD};
    private static final long serialVersionUID = -4327707202428848696L;
    private Long id;
    private String partnerCode;
    private String accountId;
    private String imageSmall;
    private String name;
    private String firstName;
    private String secondName;
    private String surname;
    private String email;
    private String displayName;
    private Float yearlyLimit;
    private Float monthlyLimit;
    private Float latitude;
    private Float longitude;
    private Boolean serviceProvider;
    private Boolean employee;
    private Date validFrom;
    private Date validTo;
    private String partnerPassword;

    public OePartner() {
    }

    public OePartner(OePartnerService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(Boolean serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Float getYearlyLimit() {
        return yearlyLimit;
    }

    public void setYearlyLimit(Float yearlyLimit) {
        this.yearlyLimit = yearlyLimit;
    }

    public Float getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(Float monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public String getPartnerPassword() {
        return partnerPassword;
    }

    public void setPartnerPassword(String partnerPassword) {
        this.partnerPassword = partnerPassword;
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
        return "OePartner{" + "id=" + id
                + ", partnerCode=" + partnerCode
                + ", accountId=" + accountId
                + ", imageSmall=" + imageSmall
                + ", name=" + name
                + ", firstName=" + firstName
                + ", secondName=" + secondName
                + ", surname=" + surname
                + ", email=" + email
                + ", displayName=" + displayName
                + ", yearlyLimit=" + yearlyLimit
                + ", monthlyLimit=" + monthlyLimit
                + ", latitude=" + latitude
                + ", longitude=" + longitude
                + ", serviceProvider=" + serviceProvider
                + ", employee=" + employee
                + ", password=" + partnerPassword
                + ", validFrom=" + validFrom
                + ", validTo=" + validTo + '}';
    }

}
