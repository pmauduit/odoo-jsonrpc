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

package com.odoo.rpc.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.odoo.rpc.entity.OeVersion;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.util.OeJsonObject;
import org.apache.http.client.utils.URIBuilder;

import java.io.Serializable;

import static com.odoo.rpc.json.util.HttpClient.postWithParams;
import static com.odoo.rpc.json.util.OeJEndPoint.WebClient.VERSION_INFO;

/**
 * OeServerVersion to read and parse Odoo version
 *
 * @author Ahmed El-mawaziny
 * @see OeVersion
 * @since 1.0
 */
public class OeServerVersion implements Serializable {

    private static final long serialVersionUID = -5241159354557231546L;
    private static volatile OeServerVersion instance;
    private final URIBuilder url;
    private final String scheme;
    private final String host;
    private final int port;
    private final OeVersion version;

    private OeServerVersion(String scheme, String host, int port) throws OeRpcException {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.url = new URIBuilder().setScheme(scheme).setHost(host).setPort(port);
        version = getServerVersion();
    }

    /**
     * get singleton instance of {@link OeServerVersion}
     *
     * @param scheme http or https
     * @param host   host name or ip address
     * @param port   port number
     * @return singleton instance of {@link OeServerVersion}
     * @throws OeRpcException
     */
    public static OeServerVersion getInstance(String scheme, String host, int port) throws OeRpcException {
        if (instance == null) {
            synchronized (OeServerVersion.class) {
                if (instance == null) {
                    instance = new OeServerVersion(scheme, host, port);
                }
            }
        }
        return instance;
    }

    public OeVersion getVersion() {
        return version;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    /**
     * connect and read Odoo version then process version json object
     *
     * @return OeVersion
     * @throws OeRpcException
     */
    private OeVersion getServerVersion() throws OeRpcException {
        String reqUrl = url.setPath(VERSION_INFO.getPath()).toString();
        JsonObject response = postWithParams(reqUrl);

        JsonObject result = new OeJsonObject(response).getAsJsonObject("result");
        String serverSerie = result.get("server_serie").getAsString();
        String serverVersion = result.get("server_version").getAsString();
        JsonArray serverVersionInfo = result.getAsJsonArray("server_version_info");
        String versionType = serverVersionInfo.get(3).getAsString();
        int versionNumber = serverVersionInfo.get(0).getAsInt();
        int versionTypeNumber = serverVersionInfo.get(4).getAsInt();
        int subVersion;
        if (serverVersionInfo.get(1).getAsJsonPrimitive().isString()) {
            subVersion = Integer.parseInt(serverVersionInfo.get(1).getAsString().split("\\.")[1].split("\\D+")[0]);
        } else {
            subVersion = serverVersionInfo.get(1).getAsInt();
        }

        return new OeVersion(serverSerie, serverVersion, versionType, versionNumber, versionTypeNumber, subVersion);
    }
}
