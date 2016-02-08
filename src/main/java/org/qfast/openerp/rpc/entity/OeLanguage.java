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

import java.util.Locale;
import org.qfast.openerp.rpc.OeConst.Direction;
import org.qfast.openerp.rpc.boundary.OeLanguageService;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public class OeLanguage extends AbstractOeEntity<OeLanguageService> {

    public static final String _CODE = "code", _DATE_FORMAT = "date_format",
            _TIME_FORMAT = "time_format", _DIRECTION = "direction",
            _ISO_CODE = "iso_code";

    private Integer id;
    private String name;
    private String dateFormat;
    private String timeFormat;
    private String direction;
    private String isoCode;
    private String code;

    public OeLanguage() {
    }

    public OeLanguage(OeLanguageService oe) {
        super.oe = oe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OeLanguage other = (OeLanguage) obj;
        return OeUtil.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "OeLanguage{" + "id=" + id
                + ", name=" + name
                + ", dataFormat=" + dateFormat
                + ", timeFormat=" + timeFormat
                + ", direction=" + direction
                + ", code=" + code
                + ", isoCode=" + isoCode + '}';
    }
    private static final long serialVersionUID = -2806061495597715017L;
}
