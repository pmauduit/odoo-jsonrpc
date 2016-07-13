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
import com.odoo.rpc.OeConst.OeModel;
import com.odoo.rpc.entity.OeVersion;
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.util.OeJsonObject;
import com.odoo.rpc.util.OeUtil;
import org.apache.http.client.utils.URIBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.odoo.rpc.OeConst.JsonDataSet.CALL_KW;
import static com.odoo.rpc.OeConst.JsonDataSet.SEARCH_READ;
import static com.odoo.rpc.OeConst.JsonSession.AUTHENTICATE;
import static com.odoo.rpc.OeConst.JsonSession.DESTROY;
import static com.odoo.rpc.OeConst.OeFun.CREATE;
import static com.odoo.rpc.OeConst.OeFun.SEARCH_COUNT;
import static com.odoo.rpc.OeConst.OeFun.UNLINK;
import static com.odoo.rpc.OeConst.OeFun.WRITE;
import static com.odoo.rpc.json.util.HttpClient.postWithParams;
import static com.odoo.rpc.json.util.OeJsonUtil.convertJsonArrayToMapArray;
import static com.odoo.rpc.json.util.OeJsonUtil.convertJsonToMap;
import static com.odoo.rpc.json.util.OeJsonUtil.margeJsonObject;
import static com.odoo.rpc.json.util.OeJsonUtil.parseAsJsonArray;
import static com.odoo.rpc.json.util.OeJsonUtil.parseAsJsonElement;
import static com.odoo.rpc.json.util.OeJsonUtil.parseAsJsonObject;

/**
 * @author Ahmed El-mawaziny
 */
public class OeExecutor implements Serializable {

    public static final Logger LOG = Logger.getLogger(OeExecutor.class.getName());
    private static final long serialVersionUID = 7528230097059877102L;
    private static volatile OeExecutor instance;
    private final String scheme;
    private final String host;
    private final int port;
    private final String password;
    private final String username;
    private final String database;
    private final Long userId;
    private final URIBuilder url;
    private final String sessionId;
    private OeVersion version;
    private JsonObject jsonContext;
    private Map<String, Object> context;

    private OeExecutor(String scheme, String host, int port, String database, String username, String password)
            throws OeRpcException {
        this.database = database;
        this.username = username;
        this.password = password;
        this.scheme = scheme;
        this.port = port;
        this.host = host;
        this.url = new URIBuilder().setScheme(scheme).setHost(host).setPort(port);
        JsonObject loginResult = doLogin();
        this.jsonContext = getJsonContextFromLogin(loginResult);
        this.context = convertJsonToMap(jsonContext);
        this.sessionId = loginResult.get("session_id").getAsString();
        this.userId = loginResult.get("uid").getAsLong();
    }

    private OeExecutor(String host, int port, String database, String username, String password) throws OeRpcException {
        this("http", host, port, database, username, password);
    }

    private OeExecutor(String host, String database, String username, String password) throws OeRpcException {
        this("http", host, 8069, database, username, password);
    }

    public static OeExecutor getInstance(String scheme, String host, int port, String database, String username,
                                         String password) throws OeRpcException {
        if (instance == null) {
            synchronized (OeExecutor.class) {
                if (instance == null) {
                    instance = new OeExecutor(scheme, host, port, database, username, password);
                }
            }
        }
        return instance;
    }

    public synchronized static OeExecutor getNewInstance(String scheme, String host, int port, String database,
                                                         String username, String password) throws OeRpcException {
        instance = new OeExecutor(scheme, host, port, database, username, password);
        return instance;
    }

    public static OeExecutor getInstance(String host, int port, String database, String username, String password)
            throws OeRpcException {
        if (instance == null) {
            synchronized (OeExecutor.class) {
                if (instance == null) {
                    instance = new OeExecutor(host, port, database, username, password);
                }
            }
        }
        return instance;
    }

    public synchronized static OeExecutor getNewInstance(String host, int port, String database, String username,
                                                         String password) throws OeRpcException {
        instance = new OeExecutor(host, port, database, username, password);
        return instance;
    }

    public static OeExecutor getInstance(String host, String database, String username, String password)
            throws OeRpcException {
        if (instance == null) {
            synchronized (OeExecutor.class) {
                if (instance == null) {
                    instance = new OeExecutor(host, database, username, password);
                }
            }
        }
        return instance;
    }

    public synchronized static OeExecutor getNewInstance(String host, String database, String username, String password)
            throws OeRpcException {
        instance = new OeExecutor(host, database, username, password);
        return instance;
    }

    private JsonObject doLogin() throws OeRpcException {
        String reqUrl = url.setPath(AUTHENTICATE.getPath()).setParameter("session_id", sessionId).toString();
        JsonObject params = new JsonObject();
        params.addProperty("db", database);
        params.addProperty("login", username);
        params.addProperty("password", password);
        JsonObject response = postWithParams(reqUrl, params);
        return new OeJsonObject(response).getAsJsonObject("result");
    }

    public void logout() {
        String reqUrl = url.setPath(DESTROY.getPath()).setParameter("session_id", sessionId).toString();
        JsonObject params = new JsonObject();
        try {
            if (isV70()) {
                params.addProperty("session_id", sessionId);
            }
            params.add("context", jsonContext);
            JsonObject response = postWithParams(reqUrl, params);
            OeRpcException.checkJsonResponse(response);
        } catch (OeRpcException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        instance = null;
    }

    private JsonObject getJsonContextFromLogin(JsonObject result) {
        if (result != null) {
            String contextKeyName = "user_context";
            if (!result.has("user_context") && result.has("context")) {
                contextKeyName = "context";
            }
            return result.getAsJsonObject(contextKeyName);
        }
        return null;
    }

    public boolean isV70() throws OeRpcException {
        version = getOeVersion();
        return version.getVersionNumber() == 7 && version.getSubVersion() == 0;
    }

    public OeVersion getOeVersion() throws OeRpcException {
        this.version = OeServerVersion.getInstance(scheme, host, port).getVersion();
        return version;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.jsonContext = parseAsJsonObject(context);
        this.context = context;
    }

    public JsonObject getJsonContext() {
        return jsonContext;
    }

    public void setJsonContext(JsonObject jsonContext) {
        this.context = convertJsonToMap(jsonContext);
        this.jsonContext = jsonContext;
    }

    public void updateContext(Map<String, Object> params) {
        updateJsonContext(params);
        context.putAll(params);
    }

    public void updateContext(String key, Object value) {
        jsonContext.add(key, parseAsJsonElement(value));
        context.put(key, value);
    }

    public void updateJsonContext(Map<String, Object> params) {
        for (String key : params.keySet()) {
            jsonContext.add(key, parseAsJsonElement(params.get(key)));
        }
    }

    public void updateJsonContext(JsonObject params) {
        margeJsonObject(params, jsonContext);
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

    public String getDatabase() {
        return database;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUrl() {
        return url.toString();
    }

    public Map<String, Object>[] searchReadMap(String model, List<Object> domain, String... columns)
            throws OeRpcException {
        return searchRead(model, domain, (String) null, columns);
    }

    public Map<String, Object>[] searchRead(String model, List<Object> domain, Integer limit, String... columns)
            throws OeRpcException {
        return searchRead(model, domain, null, limit, columns);
    }

    public Map<String, Object>[] searchRead(String model, List<Object> domain, String order, String... columns)
            throws OeRpcException {
        return searchRead(model, domain, null, null, order, columns);
    }

    public Map<String, Object>[] searchRead(String model, List<Object> domain, Integer offset, Integer limit,
                                            String... columns) throws OeRpcException {
        return searchRead(model, domain, offset, limit, null, columns);
    }

    public Map<String, Object>[] searchRead(String model, List<Object> domain, Integer offset, Integer limit,
                                            String order, String... columns) throws OeRpcException {
        return searchReadMap(model, domain, offset, limit, order, context, columns);
    }

    public JsonArray searchRead(String model, List<Object> domain, Integer offset, Integer limit, String order,
                                Map<String, Object> context, String... columns) throws OeRpcException {
        return parseAsJsonArray(searchReadStr(model, domain, offset, limit, order, context, columns));
    }

    public JsonArray searchRead(String model, List<Object> domain, String... columns) throws OeRpcException {
        return parseAsJsonArray(searchReadStr(model, domain, null, null, null, context, columns));
    }

    public Map<String, Object>[] searchReadMap(String model, List<Object> domain, Integer offset, Integer limit,
                                               String order, Map<String, Object> context, String... columns)
            throws OeRpcException {
        return convertJsonArrayToMapArray(searchRead(model, domain, offset, limit, order, context, columns));
    }

    public String searchReadStr(String model, List<Object> domain, Integer offset, Integer limit, String order,
                                Map<String, Object> context, String... columns) throws OeRpcException {

        String reqUrl = url.setPath(SEARCH_READ.getPath()).setParameter("session_id", sessionId).toString();
        JsonObject params = new JsonObject();
        params.addProperty("model", model);

        if (columns != null && columns.length != 0) {
            params.add("fields", parseAsJsonArray(columns));
        } else {
            params.add("fields", new JsonArray());
        }

        if (domain != null) {
            params.add("domain", parseAsJsonArray(domain));
        } else {
            params.add("domain", new JsonArray());
        }

        if (context != null) {
            params.add("context", parseAsJsonObject(context));
        } else {
            params.add("context", new JsonObject());
        }

        params.addProperty("offset", offset);
        params.addProperty("limit", limit);

        if (order != null) {
            params.addProperty("sort", order);
        } else {
            params.addProperty("sort", "");
        }

        if (isV70()) {
            params.addProperty("session_id", sessionId);
        }

        JsonObject response = postWithParams(reqUrl, params);
        return new OeJsonObject(response).getAsJsonObject("result").getAsJsonArray("records").toString();
    }

    public Long count(OeModel model, List<Object> domain) throws OeRpcException {
        return count(model.getName(), domain);
    }

    public Long count(String model, List<Object> domain) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(parseAsJsonElement(domain));
        Object result = execute(model, SEARCH_COUNT.getName(), args);
        if (!OeUtil.isNULL(result)) {
            return Long.parseLong(result.toString());
        }
        return 0L;
    }

    public Long create(OeModel model, Map<String, Object> values) throws OeRpcException {
        return create(model.getName(), values);
    }

    public Long create(String model, Map<String, Object> values) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(parseAsJsonElement(values));
        args.add(jsonContext);
        Object result = execute(model, CREATE.getName(), args);
        if (!OeUtil.isNULL(result)) {
            return Long.parseLong(result.toString());
        }
        return 0L;
    }

    public Boolean write(String model, Object id, Map<String, Object> values) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(parseAsJsonElement(id));
        args.add(parseAsJsonElement(values));
        Object result = execute(model, WRITE.getName(), args);
        return !OeUtil.isNULL(result) && Boolean.parseBoolean(result.toString());
    }

    public Boolean unlike(String model, Long... ids) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(parseAsJsonArray(ids));
        args.add(jsonContext);
        Object result = execute(model, UNLINK.getName(), args);
        return !OeUtil.isNULL(result) && Boolean.parseBoolean(result.toString());
    }

    public Map<String, Object> execute(String model, String method) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(jsonContext);
        return convertJsonToMap((JsonObject) execute(model, method, args));
    }

    public Object execute(String model, String method, Object args, boolean wrapArgs) throws OeRpcException {
        JsonArray argsArr = new JsonArray();
        if (wrapArgs) {
            argsArr.add(parseAsJsonArray(args));
        } else {
            argsArr = parseAsJsonArray(args);
        }
        return execute(model, method, argsArr);
    }

    public Object execute(String model, String method, JsonArray args) throws OeRpcException {
        return execute(model, method, args, new JsonObject());
    }

    public Object execute(String model, String method, Object[] args, Map<String, Object> kwargs) throws OeRpcException {
        JsonArray argsJson = parseAsJsonArray(args);
        JsonObject kwargsJson = parseAsJsonObject(kwargs);
        return execute(model, method, argsJson, kwargsJson);
    }

    public Object execute(String model, String method, JsonArray args, JsonObject kwargs) throws OeRpcException {
        String reqUrl = url.setPath(CALL_KW.getPath()).setParameter("session_id", sessionId).toString();
        JsonObject params = new JsonObject();
        params.addProperty("model", model);
        params.addProperty("method", method);
        params.add("args", args);
        params.add("kwargs", kwargs);
        if (isV70()) {
            params.addProperty("session_id", sessionId);
        }
        JsonObject response = postWithParams(reqUrl, params);

        return new OeJsonObject(response).get("result");
    }

    public Map<String, Object> execute(String fun, Map<String, Object> params) throws OeRpcException {
        return convertJsonToMap((JsonObject) execute(fun, parseAsJsonObject(params)));
    }

    public Object execute(String fun, JsonObject jsonObj) throws OeRpcException {
        String reqUrl = url.setPath(fun).setParameter("session_id", sessionId).toString();
        return postWithParams(reqUrl, jsonObj).get("result");
    }
}
