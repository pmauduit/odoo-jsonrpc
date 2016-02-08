/*
 * Copyright 2015 QFast Ahmed El-mawaziny.
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

import org.qfast.openerp.rpc.boundary.AttachmentService;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public class Attachment extends AbstractOeEntity<AttachmentService> {

    public static final String _FILE_NAME = "datas_fname", _NAME = "name",
            _DATA = "datas", _MODEL = "res_model", _FILE_SIZE = "file_size";

    private Integer id;
    private String name;
    private String datas;

    public Attachment() {
    }

    public Attachment(AttachmentService service) {
        super.oe = service;
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

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public String getUrl() {
        OeExecutor executor = oe.getExecutor();
        String url = executor.getProtocol()
                + "://" + executor.getHost()
                + ":" + executor.getPort()
                + "/web/binary/saveas?model=ir.attachment&field=datas&filename_field=datas_fname&id=" + id
                + "&session_id=" + executor.getSessionId();
        return url;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null
                && getClass() != obj.getClass()
                && OeUtil.equals(this.id, ((Attachment) obj).id);
    }

    @Override
    public String toString() {
        return "Attachment{" + "id=" + id + ", name=" + name + ", datas=" + datas + '}';
    }
    private static final long serialVersionUID = -3111917687766566032L;
}
