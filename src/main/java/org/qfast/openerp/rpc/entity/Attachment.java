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

import com.google.gson.annotations.SerializedName;
import org.apache.http.client.utils.URIBuilder;
import org.qfast.openerp.rpc.OeConst;
import org.qfast.openerp.rpc.boundary.AttachmentService;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public class Attachment extends AbstractOeEntity<AttachmentService> {

    public static final String _ID = OeConst._COL_ID, _FILE_NAME = "datas_fname", _NAME = OeConst._COL_NAME,
            _DATA = "data", _MODEL = "res_model", _FILE_SIZE = "file_size";
    public static final String[] COLUMNS = new String[]{_ID, _NAME, _DATA};
    private static final long serialVersionUID = -3111917687766566032L;
    private Integer id;
    private String name;
    @SerializedName(_DATA)
    private String data;

    public Attachment() {
    }

    public Attachment(AttachmentService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUrl() {
        OeExecutor executor = oe.getExecutor();
        return new URIBuilder().setScheme(executor.getProtocol())
                .setHost(executor.getHost())
                .setPort(executor.getPort())
                .setPath("/web/binary/saveas")
                .setParameter("model", oe.getName())
                .setParameter("field", _DATA)
                .setParameter("filename_field", _FILE_NAME)
                .setParameter(_ID, String.valueOf(id))
                .setParameter("session_id", executor.getSessionId())
                .toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() != obj.getClass() && OeUtil.equals(this.id, ((Attachment) obj).id);
    }

    @Override
    public String toString() {
        return "Attachment{" + "id=" + id + ", name=" + name + ", data=" + data + '}';
    }
}
