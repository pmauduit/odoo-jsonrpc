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
import org.qfast.openerp.rpc.boundary.AttachmentService;
import org.qfast.openerp.rpc.json.OeExecutor;
import org.qfast.openerp.rpc.util.OeUtil;

import java.util.Arrays;

/**
 * @author Ahmed El-mawaziny
 */
public class Attachment extends AbstractOeEntity<AttachmentService> {

    public static final String _FILE_NAME = "datas_fname", _DATA = "datas", _MODEL = "res_model",
            _FILE_SIZE = "file_size", _FILE_TYPE = "file_type", _RES_NAME = "res_name", _DB_DATAS = "db_datas",
            _COMPANY_ID = "company_id", _TYPE = "type", _STORE_FNAME = "store_fname", _DESCRIPTION = "description",
            _FILE_TYPE_ICON = "file_type_icon", _URL = "url", _RES_ID = "res_id", _DATAS_FNAME = "datas_fname";

    public static final String[] COLUMNS = new String[]{_ID, _NAME, _FILE_NAME, _DATA, _MODEL, _FILE_SIZE, _FILE_TYPE,
            _RES_NAME, _DB_DATAS, _COMPANY_ID, _TYPE, _STORE_FNAME, _DESCRIPTION, _FILE_TYPE_ICON, _URL, _RES_ID,
            _DATAS_FNAME};

    private static final long serialVersionUID = -3111917687766566032L;

    @SerializedName(_DATA)
    private String data;
    @SerializedName(_MODEL)
    private String model;
    private double fileSize;
    private String fileType;
    private String resName;
    @SerializedName(_DB_DATAS)
    private String dbData;
    private Object[] companyId;
    @SerializedName(_STORE_FNAME)
    private String storeFileName;
    private String description;
    private String fileTypeIcon;
    private String url;
    private Long resId;
    @SerializedName(_DATAS_FNAME)
    private String dataFileName;
    private String type;

    public Attachment() {
    }

    public Attachment(AttachmentService service) {
        super.oe = service;
    }

    @Override
    public String[] getColumns() {
        return COLUMNS;
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

    public void setUrl(String url) {
        this.url = url;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getFileSize() {
        return fileSize;
    }

    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + OeUtil.hashCode(this.id);
        return hash;
    }

    public Object[] getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Object[] companyId) {
        this.companyId = companyId;
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    public String getDbData() {
        return dbData;
    }

    public void setDbData(String dbData) {
        this.dbData = dbData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileTypeIcon() {
        return fileTypeIcon;
    }

    public void setFileTypeIcon(String fileTypeIcon) {
        this.fileTypeIcon = fileTypeIcon;
    }

    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getStoreFileName() {
        return storeFileName;
    }

    public void setStoreFileName(String storeFileName) {
        this.storeFileName = storeFileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() != obj.getClass() && OeUtil.equals(this.id, ((Attachment) obj).id);
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createDate='" + createDate + '\'' +
                ", lastUpdate='" + lastUpdate + '\'' +
                ", writeDate='" + writeDate + '\'' +
                ", createUid='" + Arrays.toString(createUid) + '\'' +
                ", writeUid='" + Arrays.toString(writeUid) + '\'' +
                ", companyId=" + Arrays.toString(companyId) +
                ", data='" + data + '\'' +
                ", model='" + model + '\'' +
                ", fileSize=" + fileSize +
                ", fileType='" + fileType + '\'' +
                ", resName='" + resName + '\'' +
                ", dbData='" + dbData + '\'' +
                ", displayName='" + displayName + '\'' +
                ", storeFileName='" + storeFileName + '\'' +
                ", description='" + description + '\'' +
                ", fileTypeIcon='" + fileTypeIcon + '\'' +
                ", url='" + url + '\'' +
                ", resId=" + resId +
                ", dataFileName='" + dataFileName + '\'' +
                ", type='" + type + '\'' +
                ", genUrl='" + getUrl() + '\'' +
                '}';
    }
}
