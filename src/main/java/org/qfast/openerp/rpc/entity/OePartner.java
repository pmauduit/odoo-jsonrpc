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

import org.qfast.openerp.rpc.boundary.OePartnerService;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Arrays;

/**
 * @author Ahmed El-mawaziny
 */
public class OePartner extends AbstractOeEntity<OePartnerService> {

    public static final String _PARTNER_CODE = "partner_code", _ACCOUNT_ID = "account_id", _IMAGE_SMALL = "image_small",
            _FIRST_NAME = "first_name", _SECOND_NAME = "second_name", _SURNAME = "surname", _EMAIL = "email",
            _EMPLOYEE = "employee";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _PARTNER_CODE, _ACCOUNT_ID, _IMAGE_SMALL, _FIRST_NAME, _SECOND_NAME, _SURNAME,
            _EMAIL, _EMPLOYEE};

    private static final long serialVersionUID = -4327707202428848696L;

    private String partnerCode;
    private String accountId;
    private String imageSmall;
    private String firstName;
    private String secondName;
    private String surname;
    private String email;
    private String displayName;
    private Boolean employee;

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
                ", accountId='" + accountId + '\'' +
                ", partnerCode='" + partnerCode + '\'' +
                ", imageSmall='" + imageSmall + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", employee=" + employee +
                '}';
    }
}
