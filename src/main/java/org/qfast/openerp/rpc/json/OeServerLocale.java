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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.utils.URIBuilder;
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
    private static volatile OeServerLocale instance;
    private final String protocol;
    private final String host;
    private final int port;
    private final URIBuilder url;
    private final JsonObject emptyObject = new JsonObject();
    private final Object[] languages;

    private OeServerLocale(String protocol, String host, int port) throws OeRpcException {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.url = new URIBuilder().setScheme(protocol).setHost(host).setPort(port);
        languages = doListLang();
    }

    private OeServerLocale(OeDatabase oeDatabase) throws OeRpcException {
        this(oeDatabase.getProtocol(), oeDatabase.getHost(), oeDatabase.getPort());
    }

    public static OeServerLocale getInstance(String protocol, String host, int port) throws OeRpcException {
        if (instance == null) {
            synchronized (OeServerLocale.class) {
                if (instance == null) {
                    instance = new OeServerLocale(protocol, host, port);
                }
            }
        }
        return instance;
    }

    public synchronized static OeServerLocale getNewInstance(String protocol, String host, int port) throws OeRpcException {
        instance = new OeServerLocale(protocol, host, port);
        return instance;
    }

    public static OeServerLocale getInstance(OeDatabase oeDatabase) throws OeRpcException {
        if (instance == null) {
            synchronized (OeServerLocale.class) {
                if (instance == null) {
                    instance = new OeServerLocale(oeDatabase);
                }
            }
        }
        return instance;
    }

    public synchronized static OeServerLocale getNewInstance(OeDatabase oeDatabase) throws OeRpcException {
        instance = new OeServerLocale(oeDatabase);
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

    private Object[] doListLang() throws OeRpcException {
        try {
            String reqUrl = url.setPath(GET_LANG_LIST.toString()).toString();
            JsonObject response = postRequest(reqUrl, getCallWith(emptyObject));
            OeRpcException.checkJsonResponse(response);
            JsonArray result = response.getAsJsonArray("result");
            Object[] langs = new Object[result.size()];
            for (int i = 0; i < result.size(); i++) {
                langs[i] = OeUtil.convertJsonArray(result.get(i).getAsJsonArray(), Object[].class);
            }
            return langs;
        } catch (Exception e) {
            throw new OeRpcException(e);
        }
    }

    public Object[] getLanguages() {
        return languages;
    }

    public List<OeLocale> getOeLocales() throws OeRpcException {
        try {
            List<OeLocale> locales = new ArrayList<OeLocale>(languages.length);
            for (Object langObj : languages) {
                Object[] lang = (Object[]) langObj;
                locales.add(new OeLocale(lang[0].toString(), lang[1].toString()));
            }
            return locales;
        } catch (Exception e) {
            throw new OeRpcException(e);
        }
    }
}
