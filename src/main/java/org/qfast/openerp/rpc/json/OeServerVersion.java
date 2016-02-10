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

import org.json.JSONObject;
import org.qfast.openerp.rpc.OeConst;
import org.qfast.openerp.rpc.entity.OeVersion;
import org.qfast.openerp.rpc.exception.OeRpcException;

import java.io.Serializable;

import static org.qfast.openerp.rpc.util.OeUtil.getCallWith;
import static org.qfast.openerp.rpc.util.OeUtil.postRequest;

/**
 * @author Ahmed El-mawaziny
 */
public class OeServerVersion implements Serializable {

    private static final long serialVersionUID = -5241159354557231546L;
    private final String url;
    private final String protocol;
    private final String host;
    private final int port;
    private final JSONObject emptyObject = new JSONObject();

    public OeServerVersion(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.url = protocol + "://" + host + ":" + port + "/web";
    }

    public OeServerVersion(OeDatabase oeDatabase) {
        this(oeDatabase.getProtocol(), oeDatabase.getHost(), oeDatabase.getPort());
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
        String reqUrl = url + "/" + OeConst.JsonWebClient.VERSION_INFO.getPath();
        JSONObject response = postRequest(reqUrl, getCallWith(emptyObject));
        OeRpcException.checkJsonResponse(response);
        return new OeVersion(response.getJSONObject("result"));
    }
}
