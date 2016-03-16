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

import org.qfast.openerp.rpc.boundary.OeMenuService;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Arrays;

/**
 * @author Ahmed El-mawaziny
 */
public class OeMenu extends AbstractOeEntity<OeMenuService> implements Comparable<OeMenu> {

    public static final String _SEQUENCE = "sequence", _GROUPS_ID = "groups_id", _GROUPS_ID_ID = _GROUPS_ID + ".id",
            _PARENT_ID = "parent_id", _PARENT_ID_ID = _PARENT_ID + ".id", _CHILDREN = "children", _ACTION = "action";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _ACTION, _SEQUENCE, _PARENT_ID, _CHILDREN};

    private static final long serialVersionUID = -3622021175633536095L;

    private Object[] parentId;
    private OeMenu parentMenu;
    private Integer sequence;
    private OeMenu[] children;
    private String action;

    public OeMenu() {
    }

    public OeMenu(OeMenuService service) {
        oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public OeMenu getParentMenu() throws OeRpcException {
        if (parentId != null && parentMenu == null) {
            parentMenu = oe.findById(getParentMenuId());
        }
        return parentMenu;
    }

    public void setParentMenu(OeMenu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public Object[] getParentId() {
        return parentId;
    }

    public void setParentId(Object[] parentId) {
        this.parentId = parentId;
    }

    public Long getParentMenuId() {
        if (parentId != null) {
            return ((Double) parentId[0]).longValue();
        }
        return null;
    }

    public String getParentMenuName() {
        if (parentId != null) {
            return String.valueOf(parentId[1]);
        }
        return null;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public OeMenu[] getChildren() {
        return children;
    }

    public void setChildren(OeMenu[] children) {
        this.children = children;
    }

    public OeAction getAction() {
        if (action != null) {
            String[] actionArr = action.split(",");
            return new OeAction(Long.parseLong(actionArr[1]), actionArr[0]);
        }
        return null;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass()
                && OeUtil.equals(this.id, ((OeMenu) obj).id);
    }

    @Override
    public int compareTo(OeMenu o) {
        int compare = sequence.compareTo(o.sequence);
        if (compare == 0) {
            compare = name.compareTo(o.name);
            if (compare == 0) {
                compare = id.compareTo(o.id);
            }
        }
        return compare;
    }

    @Override
    public String toString() {
        return "OeMenu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", action='" + action + '\'' +
                ", parentId=" + Arrays.toString(parentId) +
                ", parentMenu=" + parentMenu +
                ", sequence=" + sequence +
                ", children=" + Arrays.toString(children) +
                '}';
    }
}
