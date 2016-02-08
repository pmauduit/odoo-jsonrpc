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

import java.util.Arrays;
import org.qfast.openerp.rpc.boundary.OeMenuService;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public class OeMenu extends AbstractOeEntity<OeMenuService>
        implements Comparable<OeMenu> {

    public static final String _SEQUENCE = "sequence", _GROUPS_ID = "groups_id",
            _GROUPS_ID_ID = _GROUPS_ID + ".id", _PARENT_ID = "parent_id",
            _PARENT_ID_ID = _PARENT_ID + ".id", _CHILDREN = "children",
            _ACTION = "action";

    private Integer id;
    private String name;
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

    public Integer getParentMenuId() {
        if (parentId != null) {
            return ((Double) parentId[0]).intValue();
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
            return new OeAction(Integer.parseInt(actionArr[1]), actionArr[0]);
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
        return "Menu{" + "id=" + id
                + ", name=" + name
                + ", parentId=" + Arrays.toString(parentId)
                + ", perantMenu=" + parentMenu
                + ", children=" + Arrays.toString(children)
                + ", action=" + getAction()
                + ", sequence=" + sequence + "}\n";
    }
    private static final long serialVersionUID = -3622021175633536095L;
}
