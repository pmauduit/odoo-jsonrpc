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
import org.qfast.openerp.rpc.OeConst.OeModel;
import org.qfast.openerp.rpc.OeConst.OeViewMode;
import org.qfast.openerp.rpc.boundary.OeViewService;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Ahmed El-mawaziny
 */
public class OeView extends AbstractOeEntity<OeViewService> implements Comparable<OeView> {

    public static final String _ARCH = "arch", _FIELD_PARENT = "field_parent", _INHERIT_ID = "inherit_id",
            _MODEL_DATA_ID = "model_data_id", _PRIORITY = "priority", _APPLICATION = "application", _MODE = "mode",
            _MODEL = "model", _TYPE = "type", _VIEW_ID = "view_id", _FIELDS = "fields";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _ARCH, _FIELD_PARENT, _INHERIT_ID, _MODEL_DATA_ID, _PRIORITY, _APPLICATION,
            _MODE, _MODEL, _TYPE, _VIEW_ID, _FIELDS};

    private static final long serialVersionUID = 8484756380717630025L;

    private Long viewId;
    private String arch;
    private String fieldParent;
    private Object[] inheritId;
    private Integer modelDataId;
    private Integer priority;
    private String application;
    private String mode;
    private String model;
    private String viewMode;
    @SerializedName(_FIELDS)
    private Map<String, OeField> oeFields;

    public OeView() {
    }

    public OeView(OeViewService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
    }

    @Override
    public Long getId() {
        if (id == null) {
            id = viewId;
        }
        return id;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
        this.viewId = id;
    }

    public Long getViewId() {
        if (viewId == null) {
            viewId = id;
        }
        return viewId;
    }

    public void setViewId(Long viewId) {
        this.viewId = viewId;
        this.id = viewId;
    }

    public String getArch() {
        return arch;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public String getFieldParent() {
        return fieldParent;
    }

    public void setFieldParent(String fieldParent) {
        this.fieldParent = fieldParent;
    }

    public Object[] getInheritId() {
        return inheritId;
    }

    public void setInheritId(Object[] inheritId) {
        this.inheritId = inheritId;
    }

    public Integer getModelDataId() {
        return modelDataId;
    }

    public void setModelDataId(Integer modelDataId) {
        this.modelDataId = modelDataId;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public OeModel getOeModel() {
        return OeModel.value(model);
    }

    public String getViewMode() {
        return viewMode;
    }

    public void setViewMode(String viewMode) {
        this.viewMode = viewMode;
    }

    public OeViewMode getOeViewMode() {
        return OeViewMode.value(viewMode);
    }

    public Map<String, OeField> getOeFields() {
        return oeFields;
    }

    public void setOeFields(Map<String, OeField> oeFields) {
        this.oeFields = oeFields;
    }

    @Override
    public int compareTo(OeView o) {
        int comparePriority = priority.compareTo(o.priority);
        if (comparePriority == 0) {
            return name.compareTo(o.name);
        }
        return comparePriority;
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
                && OeUtil.equals(this.id, ((OeView) obj).id);
    }

    @Override
    public String toString() {
        return "OeView{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", application='" + application + '\'' +
                ", viewId=" + viewId +
                ", createDate=" + createDate +
                ", writeDate=" + writeDate +
                ", arch='" + arch + '\'' +
                ", fieldParent='" + fieldParent + '\'' +
                ", inheritId=" + Arrays.toString(inheritId) +
                ", modelDataId=" + modelDataId +
                ", priority=" + priority +
                ", mode='" + mode + '\'' +
                ", model='" + model + '\'' +
                ", viewMode='" + viewMode + '\'' +
                ", oeFields=" + oeFields +
                '}';
    }
}
