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
import com.odoo.rpc.entity.OeLocale;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.util.OeJsonObject;
import com.odoo.rpc.json.util.OeJsonUtil;
import org.apache.http.client.utils.URIBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.odoo.rpc.OeConst.JsonSession.GET_LANG_LIST;
import static com.odoo.rpc.json.util.HttpClient.postWithParams;

/**
 * OeServerLocale class for listing Odoo locales as java locale
 *
 * @author Ahmed El-mawaziny
 * @see OeLocale
 * @since 1.0
 */
public class OeServerLocale implements Serializable {

    private static final long serialVersionUID = -2489079149616037822L;
    private static volatile OeServerLocale instance;
    private final String scheme;
    private final String host;
    private final int port;
    private final URIBuilder url;
    private final Object[] languages;

    /**
     * Constructor to build uri and list languages as array of object
     *
     * @param scheme http or https
     * @param host   host name or ip address
     * @param port   port number
     * @throws OeRpcException
     */
    private OeServerLocale(String scheme, String host, int port) throws OeRpcException {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.url = new URIBuilder().setScheme(scheme).setHost(host).setPort(port);
        languages = doListLang();
    }

    /**
     * Constructor to build uri and list languages as array of object
     *
     * @param scheme http or https
     * @param host   host name or ip address
     * @param port   port number
     * @throws OeRpcException
     */
    public static OeServerLocale getInstance(String scheme, String host, int port) throws OeRpcException {
        if (instance == null) {
            synchronized (OeServerLocale.class) {
                if (instance == null) {
                    instance = new OeServerLocale(scheme, host, port);
                }
            }
        }
        return instance;
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

    private Object[] doListLang() throws OeRpcException {
        String reqUrl = url.setPath(GET_LANG_LIST.getPath()).toString();
        JsonObject response = postWithParams(reqUrl);
        JsonArray result = new OeJsonObject(response).getAsJsonArray("result");
        Object[] langs = new Object[result.size()];
        for (int i = 0; i < result.size(); i++) {
            langs[i] = OeJsonUtil.convertJsonArray(result.get(i).getAsJsonArray(), Object[].class);
        }
        return langs;
    }

    public Object[] getLanguages() {
        return languages;
    }

    /**
     * Convert languages to {@link OeLocale}
     *
     * @return list of {@link OeLocale}
     * @throws OeRpcException
     */
    public List<OeLocale> getOeLocales() throws OeRpcException {
        List<OeLocale> locales = new ArrayList<OeLocale>(languages.length);
        for (Object langObj : languages) {
            Object[] lang = (Object[]) langObj;
            locales.add(new OeLocale(lang[0].toString(), lang[1].toString()));
        }
        return locales;
    }
}
