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

import org.json.JSONArray;
import org.json.JSONObject;
import org.qfast.openerp.rpc.entity.OeLocale;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.qfast.openerp.rpc.OeConst.JsonSession.GET_LANG_LIST;
import static org.qfast.openerp.rpc.util.OeUtil.getCallWith;
import static org.qfast.openerp.rpc.util.OeUtil.postRequest;

/**
 * @author Ahmed El-mawaziny
 */
public class OeServerLocale implements Serializable {

    private static final long serialVersionUID = -2489079149616037822L;
    private final String protocol;
    private final String host;
    private final int port;
    private final String url;
    private final JSONObject emptyObject = new JSONObject();

    public OeServerLocale(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.url = protocol + "://" + host + ":" + port + "/web/session";
    }

    public OeServerLocale(OeDatabase oeDatabase) {
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

    public Object[] doListLang() throws OeRpcException {
        try {
            String reqUrl = url + "/" + GET_LANG_LIST.toString();
            JSONObject response = postRequest(reqUrl, getCallWith(emptyObject));
            OeRpcException.checkJsonResponse(response);
            JSONArray result = response.getJSONArray("result");
            Object[] langs = new Object[result.length()];
            for (int i = 0; i < result.length(); i++) {
                langs[i] = OeUtil.convertJsonArray(result.getJSONArray(i), Object[].class);
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
}
