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

import com.google.gson.annotations.SerializedName;
import org.qfast.openerp.rpc.boundary.OeGroupService;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Arrays;
import java.util.Set;

/**
 * @author Ahmed El-mawaziny
 */
public class OeGroup extends AbstractOeEntity<OeGroupService> {

    public static final String _USERS_ID = "users", _USERS_ID_ID = _USERS_ID + ".id", _MODEL_ACCESS = "model_access",
            _IS_PORTAL = "is_portal", _MENU_ACCESS = "menu_access", _SHARE = "share", _COMMENT = "comment",
            _TRANS_IMPLIED_IDS = "trans_implied_ids", _FULL_NAME = "full_name", _VIEW_ACCESS = "view_access",
            _RULE_GROUPS = "rule_groups", _IMPLIED_IDS = "implied_ids", _CATEGORY_ID = "category_id";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _USERS_ID, _MODEL_ACCESS, _IS_PORTAL, _MENU_ACCESS, _SHARE, _COMMENT,
            _TRANS_IMPLIED_IDS, _FULL_NAME, _RULE_GROUPS, _VIEW_ACCESS, _IMPLIED_IDS, _CATEGORY_ID};

    private static final long serialVersionUID = -1559525299933043517L;

    private Object[] modelAccess;
    private Integer[] users;
    private Integer[] menuAccess;
    private Integer[] viewAccess;
    private Boolean share;
    @SerializedName(_IS_PORTAL)
    private Boolean portal;
    private Integer[] transImpliedIds;
    private Integer[] impliedIds;
    private Integer[] ruleGroups;
    private String comment;
    private String fullName;
    private Object[] categoryId;
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

    public Object[] getModelAccess() {
        return modelAccess;
    }

    public void setModelAccess(Object[] modelAccess) {
        this.modelAccess = modelAccess;
    }

    public Integer[] getUsers() {
        return users;
    }

    public void setUsers(Integer[] users) {
        this.users = users;
    }

    public Integer[] getMenuAccess() {
        return menuAccess;
    }

    public void setMenuAccess(Integer[] menuAccess) {
        this.menuAccess = menuAccess;
    }

    public Integer[] getViewAccess() {
        return viewAccess;
    }

    public void setViewAccess(Integer[] viewAccess) {
        this.viewAccess = viewAccess;
    }

    public Boolean getShare() {
        return share;
    }

    public void setShare(Boolean share) {
        this.share = share;
    }

    public Boolean getPortal() {
        return portal;
    }

    public void setPortal(Boolean portal) {
        this.portal = portal;
    }

    public Integer[] getTransImpliedIds() {
        return transImpliedIds;
    }

    public void setTransImpliedIds(Integer[] transImpliedIds) {
        this.transImpliedIds = transImpliedIds;
    }

    public Integer[] getRuleGroups() {
        return ruleGroups;
    }

    public void setRuleGroups(Integer[] ruleGroups) {
        this.ruleGroups = ruleGroups;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer[] getImpliedIds() {
        return impliedIds;
    }

    public void setImpliedIds(Integer[] impliedIds) {
        this.impliedIds = impliedIds;
    }

    public Object[] getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object[] categoryId) {
        this.categoryId = categoryId;
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
        return "OeGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", modelAccess=" + Arrays.toString(modelAccess) +
                ", users=" + Arrays.toString(users) +
                ", menuAccess=" + Arrays.toString(menuAccess) +
                ", viewAccess=" + Arrays.toString(viewAccess) +
                ", share=" + share +
                ", portal=" + portal +
                ", transImpliedIds=" + Arrays.toString(transImpliedIds) +
                ", impliedIds=" + Arrays.toString(impliedIds) +
                ", ruleGroups=" + Arrays.toString(ruleGroups) +
                ", comment='" + comment + '\'' +
                ", fullName='" + fullName + '\'' +
                ", categoryId=" + Arrays.toString(categoryId) +
                ", menus=" + menus +
                '}';
    }
}
