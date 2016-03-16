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
import org.qfast.openerp.rpc.boundary.OeActionClientService;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Ahmed El-mawaziny
 */
public class OeActionClient extends AbstractOeEntity<OeActionClientService> {

    public static final String _TYPE = "type", _HELP = "help", _RES_MODEL = "res_model", _CONTEXT = "context",
            _TAG = "tag", _PARAMS = "params", _PARAMS_STORE = "params_store", _USAGE = "usage";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _CREATE_DATE, _CREATE_UID, _WRITE_DATE, _WRITE_UID,
            _DISPLAY_NAME, _LAST_UPDATE, _HELP, _RES_MODEL, _CONTEXT, _TYPE, _TAG, _PARAMS, _PARAMS_STORE, _USAGE};

    private static final long serialVersionUID = -1963097797139212178L;

    private String help;
    private String resModel;
    private Map<String, Object> params;
    private Map<String, Object> context;
    private String type;
    private String tag;
    private String usage;

    public OeActionClient() {
    }

    public OeActionClient(OeActionClientService oe) {
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

    public OeModel getOeModel() {
        return OeModel.value(resModel);
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public void setContext(String context) {
        this.context = OeUtil.convertStringToMap(context);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && OeUtil.equals(this.id, ((OeActionClient) obj).id);
    }

    @Override
    public String toString() {
        return "OeActionClient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", createDate=" + createDate +
                ", lastUpdate=" + lastUpdate +
                ", writeDate=" + writeDate +
                ", createUid=" + Arrays.toString(createUid) +
                ", writeUid=" + Arrays.toString(writeUid) +
                ", context=" + context +
                ", help='" + help + '\'' +
                ", resModel='" + resModel + '\'' +
                ", params=" + params +
                ", type=" + type + '\'' +
                ", tag='" + tag + '\'' +
                ", usage='" + usage + '\'' +
                '}';
    }
}
