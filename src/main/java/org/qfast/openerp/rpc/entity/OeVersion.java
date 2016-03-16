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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.qfast.openerp.rpc.util.OeUtil;

import java.io.Serializable;

/**
 * @author Ahmed El-mawaziny
 */
public class OeVersion implements Serializable {

    private static final long serialVersionUID = -493333266795016076L;
    private final String serverSerie;
    private final String serverVersion;
    private final String versionType;
    private final int versionNumber;
    private final int versionTypeNumber;
    private final int subVersion;

    public OeVersion(String serverSerie, String serverVersion, String versionType, int versionNumber,
                     int versionTypeNumber, int subVersion) {
        this.serverSerie = serverSerie;
        this.serverVersion = serverVersion;
        this.versionType = versionType;
        this.versionNumber = versionNumber;
        this.versionTypeNumber = versionTypeNumber;
        this.subVersion = subVersion;
    }

    public OeVersion(JsonObject result) {
        this.serverSerie = result.get("server_serie").getAsString();
        this.serverVersion = result.get("server_version").getAsString();
        JsonArray serverVersionInfo = result.getAsJsonArray("server_version_info");
        this.versionType = serverVersionInfo.get(3).getAsString();
        this.versionNumber = serverVersionInfo.get(0).getAsInt();
        this.versionTypeNumber = serverVersionInfo.get(4).getAsInt();
        if (serverVersionInfo.get(1).getAsJsonPrimitive().isString()) {
            this.subVersion = Integer.parseInt(serverVersionInfo.get(1).getAsString().split("\\.")[1].split("\\D+")[0]);
        } else {
            this.subVersion = serverVersionInfo.get(1).getAsInt();
        }
    }

    public OeVersion(String serverVersion) {
        this.serverSerie = serverVersion;
        this.serverVersion = serverVersion;
        this.versionType = null;
        this.versionNumber = Integer.parseInt(serverVersion.split("\\.")[0].split("\\D+")[0]);
        this.subVersion = Integer.parseInt(serverVersion.split("\\.")[1].split("\\D+")[0]);
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
                && OeUtil.equals(this.serverVersion, ((OeVersion) obj).serverVersion);
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
}
