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
import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import org.qfast.openerp.rpc.OeConst.OeFieldType;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public class OeField implements Serializable {

    public static final String _TYPE = "type", _VIEWS = "views",
            _LABEL = "string", _READONLY = "readonly", _HELP = "help",
            _STORE = "store", _RELATION = "relation", _SELECTION = "selection",
            _REQUIRED = "required", _DOMAIN = "domain",
            _COMPANY_DEPENDENT = "company_dependent", _SIZE = "size",
            _DIGITS = "digits";

    private String type;
    private Map<String, OeView> views;
    @SerializedName("string")
    private String label;
    private boolean readOnly;
    private String help;
    private boolean store;
    private String relation;
    private Object[] selection;
    private boolean required;
    private Object[] domain;
    private boolean companyDependent;
    private Integer size;
    private Object[] digits;
    private boolean translate;
    private boolean searchable;

    public OeField() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public OeFieldType getOeFieldType() {
        return OeFieldType.value(type);
    }

    public Map<String, OeView> getViews() {
        return views;
    }

    public void setViews(Map<String, OeView> views) {
        this.views = views;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Object[] getSelection() {
        return selection;
    }

    public void setSelection(Object[] selection) {
        this.selection = selection;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    public boolean isTranslate() {
        return translate;
    }

    public void setTranslate(boolean translate) {
        this.translate = translate;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }
    
    public Object[] getDomain() {
        return domain;
    }

    public void setDomain(Object[] domain) {
        this.domain = domain;
    }

    public void setDomain(String domain) {
        this.domain = OeUtil.convertTuplesStringToArray(domain);
    }

    public boolean isCompanyDependent() {
        return companyDependent;
    }

    public void setCompanyDependent(boolean companyDependent) {
        this.companyDependent = companyDependent;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Object[] getDigits() {
        return digits;
    }

    public void setDigits(Object[] digits) {
        this.digits = digits;
    }

    @Override
    public String toString() {
        return "OeField{type=" + type
                + ", views=" + views
                + ", label=" + label
                + ", readOnly=" + readOnly
                + ", help=" + help
                + ", store=" + store
                + ", relation=" + relation
                + ", selection=" + Arrays.deepToString(selection)
                + ", required=" + required
                + ", domain=" + Arrays.deepToString(domain)
                + ", companyDependent=" + companyDependent
                + ", size=" + size
                + ", digits=" + Arrays.deepToString(digits) + '}';
    }
    private static final long serialVersionUID = -8700005699990251758L;
}
