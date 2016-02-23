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
import org.qfast.openerp.rpc.boundary.OeGroupService;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Set;

/**
 * @author Ahmed El-mawaziny
 */
public class OeGroup extends AbstractOeEntity<OeGroupService> {

    public static final String _ID = OeConst._COL_ID, _NAME = OeConst._COL_NAME, _USERS_ID = "users",
            _USERS_ID_ID = _USERS_ID + ".id";
    public static final String[] COLUMNS = new String[]{_ID, _NAME};
    private static final long serialVersionUID = -1559525299933043517L;
    private Long id;
    private String name;
    private Set<OeMenu> menus;

    public OeGroup() {
    }

    public OeGroup(OeGroupService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

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

    public Set<OeMenu> getMenus() throws OeRpcException {
        if (id != null && menus == null) {
            menus = oe.findMenusByGroupId(id);
        }
        return menus;
    }

    public void setMenus(Set<OeMenu> menus) {
        this.menus = menus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.id, ((OeGroup) obj).id);
    }

    @Override
    public String toString() {
        return "OeGroup{" + "id=" + id + ", name=" + name + '}';
    }
}
