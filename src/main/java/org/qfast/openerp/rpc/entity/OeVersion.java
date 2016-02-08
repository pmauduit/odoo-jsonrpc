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

import java.io.Serializable;
import javax.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.qfast.openerp.rpc.util.OeUtil;

/**
 * @author Ahmed El-mawaziny
 */
public final class OeVersion implements Serializable {

    private final String serverSerie;
    private final String serverVersion;
    private final String versionType;
    private final int versionNumber;
    private final int versionTypeNumber;
    private final int subVersion;

    public OeVersion(String serverSerie, String serverVersion, String versionType,
            int versionNumber, int versionTypeNumber, int subVersion) {
        this.serverSerie = serverSerie;
        this.serverVersion = serverVersion;
        this.versionType = versionType;
        this.versionNumber = versionNumber;
        this.versionTypeNumber = versionTypeNumber;
        this.subVersion = subVersion;
    }

    public OeVersion(JSONObject result) {
        this.serverSerie = result.getString("server_serie");
        this.serverVersion = result.getString("server_version");
        JSONArray serverVersionInfo = result.getJSONArray("server_version_info");
        this.versionType = serverVersionInfo.getString(3);
        this.versionNumber = serverVersionInfo.getInt(0);
        this.versionTypeNumber = serverVersionInfo.getInt(4);
        if (serverVersionInfo.get(1) instanceof String) {
            this.subVersion = Integer.parseInt(serverVersionInfo.getString(1)
                    .split("\\.")[1].split("\\D+")[0]);
        } else {
            this.subVersion = serverVersionInfo.getInt(1);
        }
    }

    public OeVersion(JsonObject result) {
        this(new JSONObject(result.toString()));
    }

    public OeVersion(String serverVersion) {
        this.serverSerie = serverVersion;
        this.serverVersion = serverVersion;
        this.versionType = null;
        this.versionNumber = Integer.parseInt(serverVersion.split("\\.")[0]
                .split("\\D+")[0]);
        this.subVersion = Integer.parseInt(serverVersion.split("\\.")[1]
                .split("\\D+")[0]);
        this.versionTypeNumber = 0;
    }

    public String getServerSerie() {
        return serverSerie;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public String getVersionType() {
        return versionType;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public int getVersionTypeNumber() {
        return versionTypeNumber;
    }

    public int getSubVersion() {
        return subVersion;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + OeUtil.hashCode(this.serverVersion);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass()
                && OeUtil.equals(this.serverVersion,
                        ((OeVersion) obj).serverVersion);
    }

    @Override
    public String toString() {
        return "OeVersion{" + "serverSerie=" + serverSerie
                + ", serverVersion=" + serverVersion
                + ", versionType=" + versionType
                + ", versionNumber=" + versionNumber
                + ", versionTypeNumber=" + versionTypeNumber
                + ", subVersion=" + subVersion
                + '}';
    }
    private static final long serialVersionUID = -493333266795016076L;
}
