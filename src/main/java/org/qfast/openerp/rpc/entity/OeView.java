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

import com.google.gson.annotations.SerializedName;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import org.qfast.openerp.rpc.OeConst.OeModel;
import org.qfast.openerp.rpc.OeConst.OeViewMode;
import org.qfast.openerp.rpc.boundary.OeViewService;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public class OeView extends AbstractOeEntity<OeViewService>
        implements Comparable<OeView> {

    public static final String _CREATE_DATE = "create_date",
            _WRITE_DATE = "write_date", _ARCH = "arch",
            _FIELD_PARENT = "field_parent", _INHERIT_ID = "inherit_id",
            _MODEL_DATA_ID = "model_data_id", _PRIORITY = "priority",
            _APPLICATION = "application", _MODE = "mode", _MODEL = "model",
            _TYPE = "type", _VIEW_ID = "view_id", _FIELDS = "fields";

    private Integer id;
    private Integer viewId;
    private String name;
    private Date createDate;
    private Date writeDate;
    private String arch;
    private String fieldParent;
    private Object[] inheritId;
    private Integer modelDataId;
    private Integer priority;
    private String application;
    private String mode;
    private String model;
    private String viewMode;
    @SerializedName("fields")
    private Map<String, OeField> oeFields;

    public OeView() {
    }

    public OeView(OeViewService service) {
        super.oe = service;
    }

    public Integer getId() {
        if (id == null) {
            id = viewId;
        }
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
        this.viewId = id;
    }

    public Integer getViewId() {
        if (viewId == null) {
            viewId = id;
        }
        return viewId;
    }

    public void setViewId(Integer viewId) {
        this.viewId = viewId;
        this.id = viewId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "OeView{" + "id=" + id
                + ", viewId=" + viewId
                + ", name=" + name
                + ", createDate=" + createDate
                + ", writeDate=" + writeDate
                + ", arch=" + arch
                + ", fieldParent=" + fieldParent
                + ", inheritId=" + Arrays.deepToString(inheritId)
                + ", modelDataId=" + modelDataId
                + ", priority=" + priority
                + ", application=" + application
                + ", mode=" + mode
                + ", model=" + model
                + ", type=" + viewMode
                + ", oeFields=" + oeFields + '}';
    }
    private static final long serialVersionUID = 8484756380717630025L;
}
