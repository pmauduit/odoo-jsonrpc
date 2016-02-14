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
import org.qfast.openerp.rpc.boundary.OeModuleService;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public class OeModule extends AbstractOeEntity<OeModuleService> {

    public static final String _ID = OeConst._COL_ID, _NAME = OeConst._COL_NAME, _SHORT_DESC = "shortdesc",
            _SUMMARY = "summary", _ICON = "icon", _STATE = "state", _AUTHOR = "author";
    public static final String[] COLUMNS = new String[]{_ID, _NAME, _SHORT_DESC, _SUMMARY, _ICON, _STATE, _AUTHOR};
    private static final long serialVersionUID = 1263824647955242480L;
    private Integer id;
    private String icon;
    private String name;
    private String shortDesc;
    private String summary;
    private String state;
    private String author;

    public OeModule() {
    }

    public OeModule(OeModuleService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.id, ((OeModule) obj).id);
    }

    @Override
    public String toString() {
        return "OEModule{" + "id=" + id
                + ", icon=" + icon
                + ", name=" + name
                + ", shortDesc=" + shortDesc
                + ", summary=" + summary
                + ", state=" + state
                + ", author=" + author + '}';
    }
}
