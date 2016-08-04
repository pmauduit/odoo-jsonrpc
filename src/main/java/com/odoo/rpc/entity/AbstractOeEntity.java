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

package com.odoo.rpc.entity;

import com.google.gson.annotations.SerializedName;
import com.odoo.rpc.boundary.AbstractOeService;
import com.odoo.rpc.boundary.OeUserService;

import java.io.Serializable;
import java.util.Date;

import static com.odoo.rpc.boundary.AbstractOeService.findById;
import static com.odoo.rpc.util.OeConst._COL_CREATE_DATE;
import static com.odoo.rpc.util.OeConst._COL_CREATE_UID;
import static com.odoo.rpc.util.OeConst._COL_DISPLAY_NAME;
import static com.odoo.rpc.util.OeConst._COL_ID;
import static com.odoo.rpc.util.OeConst._COL_LAST_UPDATE;
import static com.odoo.rpc.util.OeConst._COL_NAME;
import static com.odoo.rpc.util.OeConst._COL_WRITE_DATE;
import static com.odoo.rpc.util.OeConst._COL_WRITE_UID;

/**
 * @param <T> generic extends from {@link AbstractOeService}
 * @author Ahmed El-mawaziny
 */
public abstract class AbstractOeEntity<T extends AbstractOeService> implements Serializable {

    public static final String _ID = _COL_ID, _NAME = _COL_NAME, _CREATE_DATE = _COL_CREATE_DATE,
            _WRITE_UID = _COL_WRITE_UID, _CREATE_UID = _COL_CREATE_UID, _DISPLAY_NAME = _COL_DISPLAY_NAME,
            _LAST_UPDATE = _COL_LAST_UPDATE, _WRITE_DATE = _COL_WRITE_DATE;

    private static final long serialVersionUID = -832746926128259160L;

    protected Long id;
    protected String name;
    protected Object[] writeUid;
    protected Object[] createUid;
    protected Date createDate;
    protected Date writeDate;
    @SerializedName(_LAST_UPDATE)
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

    public OeUser getCreateUser() throws Exception {
        return findById(oe.getExecutor(), OeUser.class, OeUserService.class, ((Double) createUid[0]).longValue());
    }

    public OeUser getWriteUser() throws Exception {
        return findById(oe.getExecutor(), OeUser.class, OeUserService.class, ((Double) writeUid[0]).longValue());
    }
}
