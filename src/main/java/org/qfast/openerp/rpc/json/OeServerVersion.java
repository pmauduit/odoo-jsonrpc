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

package org.qfast.openerp.rpc.json;

import com.google.gson.JsonObject;
import org.apache.http.client.utils.URIBuilder;
import org.qfast.openerp.rpc.entity.OeVersion;
import org.qfast.openerp.rpc.exception.OeRpcException;

import java.io.Serializable;

import static org.qfast.openerp.rpc.OeConst.JsonWebClient.VERSION_INFO;
import static org.qfast.openerp.rpc.util.OeUtil.getCallWith;
import static org.qfast.openerp.rpc.util.OeUtil.postRequest;

/**
 * @author Ahmed El-mawaziny
 */
public class OeServerVersion implements Serializable {

    private static final long serialVersionUID = -5241159354557231546L;
    private static volatile OeServerVersion instance;
    private final URIBuilder url;
    private final String protocol;
    private final String host;
    private final int port;
    private final JsonObject emptyObject = new JsonObject();

    private OeServerVersion(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.url = new URIBuilder().setScheme(protocol).setHost(host).setPort(port);
    }

    private OeServerVersion(OeDatabase oeDatabase) {
        this(oeDatabase.getProtocol(), oeDatabase.getHost(), oeDatabase.getPort());
    }

    public static OeServerVersion getInstance(String protocol, String host, int port) {
        if (instance == null) {
            synchronized (OeServerVersion.class) {
                if (instance == null) {
                    instance = new OeServerVersion(protocol, host, port);
                }
            }
        }
        return instance;
    }

    public synchronized static OeServerVersion getNewInstance(String protocol, String host, int port) {
        instance = new OeServerVersion(protocol, host, port);
        return instance;
    }

    public static OeServerVersion getInstance(OeDatabase oeDatabase) {
        if (instance == null) {
            synchronized (OeServerVersion.class) {
                if (instance == null) {
                    instance = new OeServerVersion(oeDatabase);
                }
            }
        }
        return instance;
    }

    public synchronized static OeServerVersion getNewInstance(OeDatabase oeDatabase) {
        instance = new OeServerVersion(oeDatabase);
        return instance;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public OeVersion getServerVersion() throws OeRpcException {
        String reqUrl = url.setPath(VERSION_INFO.getPath()).toString();
        JsonObject response = postRequest(reqUrl, getCallWith(emptyObject));
        OeRpcException.checkJsonResponse(response);
        return new OeVersion(response.getAsJsonObject("result"));
    }
}
