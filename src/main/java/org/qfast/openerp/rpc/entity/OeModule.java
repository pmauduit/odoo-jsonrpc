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

import org.qfast.openerp.rpc.boundary.OeModuleService;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Arrays;

/**
 * @author Ahmed El-mawaziny
 */
public class OeModule extends AbstractOeEntity<OeModuleService> {

    public static final String _SHORT_DESC = "shortdesc", _SUMMARY = "summary", _ICON = "icon", _STATE = "state",
            _AUTHOR = "author";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _SHORT_DESC, _SUMMARY, _ICON, _STATE, _AUTHOR};

    private static final long serialVersionUID = 1263824647955242480L;

    private String icon;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
        return OeUtil.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.id, ((OeModule) obj).id);
    }

    @Override
    public String toString() {
        return "OeModule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", author='" + author + '\'' +
                ", icon='" + icon + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", summary='" + summary + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
