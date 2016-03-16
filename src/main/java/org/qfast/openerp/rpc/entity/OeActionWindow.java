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

import org.qfast.openerp.rpc.OeConst.OeModel;
import org.qfast.openerp.rpc.OeConst.OeViewMode;
import org.qfast.openerp.rpc.boundary.OeActionWindowService;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Ahmed El-mawaziny
 */
public class OeActionWindow extends AbstractOeEntity<OeActionWindowService> {

    public static final String _TYPE = "type", _HELP = "help", _RES_MODEL = "res_model", _CONTEXT = "context",
            _USAGE = "usage", _DOMAIN = "domain", _VIEW_ID = "view_id", _SEARCH_VIEW_ID = "search_view_id",
            _AUTO_REFRESH = "auto_refresh", _VIEW_MODE = "view_mode", _MULTI = "multi", _TARGET = "target",
            _AUTO_SEARCH = "auto_search", _FILTER = "filter", _SRC_MODEL = "src_model", _LIMIT = "limit",
            _VIEW_TYPE = "view_type", _RES_ID = "res_id";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _TYPE, _HELP, _RES_MODEL, _CONTEXT, _USAGE, _DOMAIN, _VIEW_ID, _SEARCH_VIEW_ID,
            _AUTO_REFRESH, _VIEW_MODE, _MULTI, _TARGET, _AUTO_SEARCH, _FILTER, _SRC_MODEL,
            _LIMIT, _VIEW_TYPE, _RES_ID};

    private static final long serialVersionUID = -6747437395841876695L;

    private String type;
    private String help;
    private String resModel;
    private String context;
    private String usage;
    private Object[] domain;
    private Object[] viewId;
    private Object[] searchViewId;
    private Boolean autoRefresh;
    private String viewMode;
    private boolean multi;
    private String target;
    private boolean autoSearch;
    private boolean filter;
    private String srcModel;
    private Integer limit;
    private String viewType;
    private Long resId;

    public OeActionWindow() {
    }

    public OeActionWindow(OeActionWindowService oe) {
        super.oe = oe;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getResModel() {
        return resModel;
    }

    public void setResModel(String resModel) {
        this.resModel = resModel;
    }

    public OeModel getResOeModel() {
        return OeModel.value(resModel);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Map<String, Object> getActionContext() {
        return OeUtil.convertStringToMap(context);
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Object[] getDomain() {
        return domain;
    }

    public void setDomain(Object[] domain) {
        this.domain = domain;
    }

    public Object[] getViewId() {
        return viewId;
    }

    public void setViewId(Object[] viewId) {
        this.viewId = viewId;
    }

    public Object[] getSearchViewId() {
        return searchViewId;
    }

    public void setSearchViewId(Object[] searchViewId) {
        this.searchViewId = searchViewId;
    }

    public boolean isAutoRefresh() {
        return autoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        this.autoRefresh = autoRefresh;
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    public List<OeViewMode> getOeViewModes() {
        if (!OeUtil.isNULL(viewMode)) {
            String[] viewModeStrSplit = viewMode.split(",");
            List<OeViewMode> oeViewModes = new ArrayList<OeViewMode>(viewModeStrSplit.length);
            for (String vm : viewModeStrSplit) {
                oeViewModes.add(OeViewMode.value(vm));
            }
            return oeViewModes;
        }
        return null;
    }

    public boolean isMulti() {
        return multi;
    }

    public void setMulti(boolean multi) {
        this.multi = multi;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public boolean isAutoSearch() {
        return autoSearch;
    }

    public void setAutoSearch(boolean autoSearch) {
        this.autoSearch = autoSearch;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public String getSrcModel() {
        return srcModel;
    }

    public void setSrcModel(String srcModel) {
        this.srcModel = srcModel;
    }

    public OeModel getSrcOeModel() {
        return OeModel.value(srcModel);
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public OeViewMode getOeViewType() {
        return OeViewMode.value(viewType);
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.id, ((OeActionWindow) obj).id);
    }

    @Override
    public String toString() {
        return "OeActionWindow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", autoRefresh=" + autoRefresh +
                ", type='" + type + '\'' +
                ", help='" + help + '\'' +
                ", resModel='" + resModel + '\'' +
                ", context='" + context + '\'' +
                ", usage='" + usage + '\'' +
                ", domain=" + Arrays.toString(domain) +
                ", viewId=" + viewId +
                ", searchViewId=" + Arrays.toString(searchViewId) +
                ", viewMode='" + viewMode + '\'' +
                ", multi=" + multi +
                ", target='" + target + '\'' +
                ", autoSearch=" + autoSearch +
                ", filter=" + filter +
                ", srcModel='" + srcModel + '\'' +
                ", limit=" + limit +
                ", viewType='" + viewType + '\'' +
                ", resId=" + resId +
                '}';
    }
}
