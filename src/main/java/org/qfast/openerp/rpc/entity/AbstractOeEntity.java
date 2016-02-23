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

import com.google.gson.annotations.SerializedName;
import org.qfast.openerp.rpc.OeConst;
import org.qfast.openerp.rpc.boundary.AbstractOeService;

import java.io.Serializable;
import java.util.Date;

/**
 * @param <T>
 * @author Ahmed El-mawaziny
 */
public abstract class AbstractOeEntity<T extends AbstractOeService> implements Serializable {

    public static final String _ID = OeConst._COL_ID, _NAME = OeConst._COL_NAME, _CREATE_DATE = "create_date", _WRITE_UID = "write_uid", _CREATE_UID = "create_uid",
            _DISPLAY_NAME = "display_name", _LAST_UPDATE = "__last_update", _WRITE_DATE = "write_date";
    private static final long serialVersionUID = -832746926128259160L;
    protected Long id;
    protected String name;
    protected Object[] writeUid;
    protected Object[] createUid;
    protected Date createDate;
    protected Date writeDate;
    @SerializedName("__last_update")
    protected Date lastUpdate;
    protected String displayName;
    protected T oe;

    public T getOe() {
        return oe;
    }

    public void setOe(T oe) {
        this.oe = oe;
    }

    public abstract String[] getColumns();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object[] getWriteUid() {
        return writeUid;
    }

    public void setWriteUid(Object[] writeUid) {
        this.writeUid = writeUid;
    }

    public Object[] getCreateUid() {
        return createUid;
    }

    public void setCreateUid(Object[] createUid) {
        this.createUid = createUid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
