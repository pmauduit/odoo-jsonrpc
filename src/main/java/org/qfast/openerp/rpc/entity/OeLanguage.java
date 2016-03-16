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

import org.qfast.openerp.rpc.OeConst.Direction;
import org.qfast.openerp.rpc.boundary.OeLanguageService;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author Ahmed El-mawaziny
 */
public class OeLanguage extends AbstractOeEntity<OeLanguageService> {

    public static final String _CODE = "code", _DATE_FORMAT = "date_format", _TIME_FORMAT = "time_format",
            _DIRECTION = "direction", _ISO_CODE = "iso_code", _ACTIVE = "active", _THOUSANDS_SEP = "thousands_sep",
            _TRANSLATABLE = "translatable", _DECIMAL_POINT = "decimal_point", _GROUPING = "grouping";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _CODE, _DATE_FORMAT, _TIME_FORMAT, _DIRECTION, _ISO_CODE, _ACTIVE,
            _THOUSANDS_SEP, _TRANSLATABLE, _DECIMAL_POINT, _GROUPING};

    private static final long serialVersionUID = -2806061495597715017L;

    private String dateFormat;
    private String timeFormat;
    private String direction;
    private String thousandsSep;
    private String decimalPoint;
    private String isoCode;
    private String code;
    private boolean active;
    private boolean translatable;
    private Object[] grouping;

    public OeLanguage() {
    }

    public OeLanguage(OeLanguageService oe) {
        super.oe = oe;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Locale getLocale() {
        return OeUtil.getLocale(code);
    }

    public void setLocale(Locale locale) {
        this.code = locale.toString();
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Direction getDirection() {
        return Direction.value(direction);
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getThousandsSep() {
        return thousandsSep;
    }

    public void setThousandsSep(String thousandsSep) {
        this.thousandsSep = thousandsSep;
    }

    public String getDecimalPoint() {
        return decimalPoint;
    }

    public void setDecimalPoint(String decimalPoint) {
        this.decimalPoint = decimalPoint;
    }

    public boolean isTranslatable() {
        return translatable;
    }

    public void setTranslatable(boolean translatable) {
        this.translatable = translatable;
    }

    public Object[] getGrouping() {
        return grouping;
    }

    public void setGrouping(Object[] grouping) {
        this.grouping = grouping;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.id, ((OeLanguage) obj).id);
    }

    @Override
    public String toString() {
        return "OeLanguage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", dateFormat='" + dateFormat + '\'' +
                ", timeFormat='" + timeFormat + '\'' +
                ", direction='" + direction + '\'' +
                ", thousandsSep='" + thousandsSep + '\'' +
                ", decimalPoint='" + decimalPoint + '\'' +
                ", isoCode='" + isoCode + '\'' +
                ", code='" + code + '\'' +
                ", active=" + active +
                ", translatable=" + translatable +
                ", grouping=" + Arrays.toString(grouping) +
                '}';
    }
}
