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
package org.qfast.openerp.rpc.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import static org.qfast.openerp.rpc.OeConst.JsonSession.GET_LANG_LIST;
import org.qfast.openerp.rpc.entity.OeLocale;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;
import static org.qfast.openerp.rpc.util.OeUtil.getCallWith;
import static org.qfast.openerp.rpc.util.OeUtil.postRequest;

/**
 * @author Ahmed El-mawaziny
 */
public class OeServerLocale implements Serializable {

    private final Client client;
    private final String protocol;
    private final String host;
    private final int port;
    private final String url;
    private final JsonObject emptyObject = Json.createObjectBuilder().build();

    public OeServerLocale(String protocol, String host, int port) {
        this.client = ClientBuilder.newClient();
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.url = protocol + "://" + host + ":" + port + "/web/session";
    }

    public OeServerLocale(OeDatabase oeDatabase) {
        this(oeDatabase.getProtocol(), oeDatabase.getHost(), oeDatabase.getPort());
    }

    public Client getClient() {
        return client;
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

    public Object[] doListLang() throws OeRpcException {
        try {
            String reqUrl = url + "/" + GET_LANG_LIST.toString();
            JsonObject response = postRequest(client, reqUrl, getCallWith(emptyObject));
            OeRpcException.checkJsonResponse(response);
            JsonArray result = response.getJsonArray("result");
            Object[] langs = new Object[result.size()];
            for (int i = 0; i < result.size(); i++) {
                langs[i] = OeUtil.convertJsonArray(result.getJsonArray(i),
                        Object[].class);
            }
            return langs;
        } catch (Exception e) {
            throw new OeRpcException(e);
        }
    }

    public List<OeLocale> getOeLocales() throws OeRpcException {
        try {
            Object[] doListLang = doListLang();
            List<OeLocale> locales = new ArrayList<OeLocale>(doListLang.length);
            for (Object langObj : doListLang) {
                Object[] lang = (Object[]) langObj;
                locales.add(new OeLocale(lang[0].toString(), lang[1].toString()));
            }
            return locales;
        } catch (Exception e) {
            throw new OeRpcException(e);
        }
    }
    private static final long serialVersionUID = -2489079149616037822L;
}
