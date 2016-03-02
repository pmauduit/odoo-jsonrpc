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

import org.qfast.openerp.rpc.util.OeUtil;

import java.io.Serializable;
import java.util.Locale;

/**
 * @author Ahmed El-mawaziny
 */
public class OeLocale implements Serializable {

    private static final long serialVersionUID = -3338490817706642655L;

    private Locale locale;
    private String name;

    public OeLocale() {
    }

    public OeLocale(Locale locale, String name) {
        this.locale = locale;
        this.name = name;
    }

    public OeLocale(String locale, String name) {
        this.locale = OeUtil.getLocale(locale);
        this.name = name;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + OeUtil.hashCode(this.locale);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.locale, ((OeLocale) obj).locale);
    }

    @Override
    public String toString() {
        return locale + " - " + name;
    }
}
