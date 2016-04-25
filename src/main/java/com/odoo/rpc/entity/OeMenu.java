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
import com.odoo.rpc.boundary.OeMenuService;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.util.OeUtil;

import java.util.Arrays;

/**
 * @author Ahmed El-mawaziny
 */
public class OeMenu extends AbstractOeEntity<OeMenuService> implements Comparable<OeMenu> {

    public static final String _SEQUENCE = "sequence", _GROUPS_ID = "groups_id", _GROUPS_ID_ID = _GROUPS_ID + ".id",
            _PARENT_ID = "parent_id", _PARENT_ID_ID = _PARENT_ID + ".id", _CHILD_ID = "child_id", _ACTION = "action",
            _WEB_ICON_DATA = "web_icon_data", _WEB_ICON_HOVER = "web_icon_hover", _ICON_PICT = "icon_pict",
            _WEB_ICON_HOVER_DATA = "web_icon_hover_data", _COMPLETE_NAME = "complete_name", _ICON = "icon",
            _MAIL_GROUP_ID = "mail_group_id", _NEED_ACTION_ENABLED = "needaction_enabled",
            _PARENT_RIGHT = "parent_right", _WEB_ICON = "web_icon", _PARENT_LEFT = "parent_left";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _ACTION, _SEQUENCE, _PARENT_ID, _CHILD_ID, _WEB_ICON_DATA, _WEB_ICON_HOVER,
            _ICON_PICT, _WEB_ICON_HOVER_DATA, _COMPLETE_NAME, _ICON, _MAIL_GROUP_ID, _NEED_ACTION_ENABLED,
            _PARENT_RIGHT, _WEB_ICON, _PARENT_LEFT, _GROUPS_ID};

    private static final long serialVersionUID = -3622021175633536095L;

    private Object[] parentId;
    private OeMenu parentMenu;
    private Integer sequence;
    private OeMenu[] children;
    private String action;
    private String icon;
    private String webIcon;
    private String webIconData;
    private String webIconHover;
    private String webIconHoverDate;
    private String iconPict;
    private String completeName;
    private Object[] mailGroupId;
    private Integer[] groupsId;
    @SerializedName(_NEED_ACTION_ENABLED)
    private boolean needActionEnabled;
    private Integer parentRight;
    private Integer parentLeft;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getWebIcon() {
        return webIcon;
    }

    public void setWebIcon(String webIcon) {
        this.webIcon = webIcon;
    }

    public String getWebIconData() {
        return webIconData;
    }

    public void setWebIconData(String webIconData) {
        this.webIconData = webIconData;
    }

    public String getWebIconHover() {
        return webIconHover;
    }

    public void setWebIconHover(String webIconHover) {
        this.webIconHover = webIconHover;
    }

    public String getWebIconHoverDate() {
        return webIconHoverDate;
    }

    public void setWebIconHoverDate(String webIconHoverDate) {
        this.webIconHoverDate = webIconHoverDate;
    }

    public String getIconPict() {
        return iconPict;
    }

    public void setIconPict(String iconPict) {
        this.iconPict = iconPict;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public Object[] getMailGroupId() {
        return mailGroupId;
    }

    public void setMailGroupId(Object[] mailGroupId) {
        this.mailGroupId = mailGroupId;
    }

    public boolean isNeedActionEnabled() {
        return needActionEnabled;
    }

    public void setNeedActionEnabled(boolean needActionEnabled) {
        this.needActionEnabled = needActionEnabled;
    }

    public Integer getParentRight() {
        return parentRight;
    }

    public void setParentRight(Integer parentRight) {
        this.parentRight = parentRight;
    }

    public Integer getParentLeft() {
        return parentLeft;
    }

    public void setParentLeft(Integer parentLeft) {
        this.parentLeft = parentLeft;
    }

    public Integer[] getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(Integer[] groupsId) {
        this.groupsId = groupsId;
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
                ", parentId=" + Arrays.toString(parentId) +
                ", parentMenu=" + parentMenu +
                ", sequence=" + sequence +
                ", children=" + Arrays.toString(children) +
                ", action='" + action + '\'' +
                ", icon='" + icon + '\'' +
                ", webIcon='" + webIcon + '\'' +
                ", webIconData='" + webIconData + '\'' +
                ", webIconHover='" + webIconHover + '\'' +
                ", webIconHoverDate='" + webIconHoverDate + '\'' +
                ", iconPict='" + iconPict + '\'' +
                ", completeName='" + completeName + '\'' +
                ", mailGroupId=" + Arrays.toString(mailGroupId) +
                ", needActionEnabled=" + needActionEnabled +
                ", parentRight=" + parentRight +
                ", parentLeft=" + parentLeft +
                '}';
    }
}
