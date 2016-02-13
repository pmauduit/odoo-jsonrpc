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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.utils.URIBuilder;
import org.qfast.openerp.rpc.OeConst.OeModel;
import org.qfast.openerp.rpc.entity.OeVersion;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static org.qfast.openerp.rpc.OeConst.JsonDataSet.CALL_KW;
import static org.qfast.openerp.rpc.OeConst.JsonDataSet.SEARCH_READ;
import static org.qfast.openerp.rpc.OeConst.JsonSession.AUTHENTICATE;
import static org.qfast.openerp.rpc.OeConst.JsonSession.DESTROY;
import static org.qfast.openerp.rpc.OeConst.OeFun.CREATE;
import static org.qfast.openerp.rpc.OeConst.OeFun.SEARCH_COUNT;
import static org.qfast.openerp.rpc.OeConst.OeFun.WRITE;
import static org.qfast.openerp.rpc.util.OeUtil.getCallWith;
import static org.qfast.openerp.rpc.util.OeUtil.postRequest;

/**
 * @author Ahmed El-mawaziny
 */
public class OeExecutor implements Serializable {

    private static final long serialVersionUID = 7528230097059877102L;
    private static volatile OeExecutor instance;
    private final String protocol;
    private final String host;
    private final int port;
    private final String password;
    private final String username;
    private final String database;
    private final Integer userId;
    private final URIBuilder url;
    private final String sessionId;
    private OeVersion version;
    private JsonObject jsonContext;
    private Map<String, Object> context;

    private OeExecutor(String protocol, String host, int port, String database, String username, String password)
            throws OeRpcException {
        this.database = database;
        this.username = username;
        this.password = password;
        this.protocol = protocol;
        this.port = port;
        this.host = host;
        this.url = new URIBuilder().setScheme(protocol).setHost(host).setPort(port);
        JsonObject loginResult = doLogin();
        this.jsonContext = getJsonContextFromLogin(loginResult);
        this.context = OeUtil.convertJsonToMap(jsonContext);
        this.sessionId = loginResult.get("session_id").getAsString();
        this.userId = loginResult.get("uid").getAsInt();
    }

    private OeExecutor(String host, int port, String database, String username, String password) throws OeRpcException {
        this("http", host, port, database, username, password);
    }

    private OeExecutor(String host, String database, String username, String password) throws OeRpcException {
        this("http", host, 8069, database, username, password);
    }

    public static OeExecutor getInstance(String protocol, String host, int port, String database, String username,
                                         String password) throws OeRpcException {
        if (instance == null) {
            synchronized (OeExecutor.class) {
                if (instance == null) {
                    instance = new OeExecutor(protocol, host, port, database, username, password);
                }
            }
        }
        return instance;
    }

    public synchronized static OeExecutor getNewInstance(String protocol, String host, int port, String database,
                                                         String username, String password) throws OeRpcException {
        instance = new OeExecutor(protocol, host, port, database, username, password);
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
        JsonObject response = postRequest(reqUrl, getCallWith(params));
        OeRpcException.checkJsonResponse(response);
        return response.getAsJsonObject("result");
    }

    public void logout() throws OeRpcException {
        String reqUrl = url.setPath(DESTROY.getPath()).setParameter("session_id", sessionId).toString();
        JsonObject params = new JsonObject();
        params.add("context", jsonContext);
        JsonObject response = postRequest(reqUrl, getCallWith(params));
        OeRpcException.checkJsonResponse(response);
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
        this.version = OeServerVersion.getInstance(protocol, host, port).getServerVersion();
        return version;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.jsonContext = OeUtil.parseAsJsonObject(context);
        this.context = context;
    }

    public JsonObject getJsonContext() {
        return jsonContext;
    }

    public void setJsonContext(JsonObject jsonContext) {
        this.context = OeUtil.convertJsonToMap(jsonContext);
        this.jsonContext = jsonContext;
    }

    public void updateContext(Map<String, Object> params) {
        updateJsonContext(params);
        context.putAll(params);
    }

    public void updateContext(String key, Object value) {
        jsonContext.addProperty(key, new Gson().toJson(value));
        context.put(key, value);
    }

    public void updateJsonContext(Map<String, Object> params) {
        for (String key : params.keySet()) {
            jsonContext.addProperty(key, new Gson().toJson(params.get(key)));
        }
    }

    public void updateJsonContext(JsonObject params) {
        OeUtil.margeJsonObject(params, jsonContext);
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

    public String getDatabase() {
        return database;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUrl() {
        return url.toString();
    }

    public Map<String, Object>[] searchRead(String model, List<Object> domain, String... columns) throws OeRpcException {
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

    public Map<String, Object>[] searchRead(String model, List<Object> domain, Integer offset, Integer limit, String order,
                                            String... columns) throws OeRpcException {
        return searchReadMap(model, domain, offset, limit, order, context, columns);
    }

    public String searchReadStr(String model, List<Object> domain, Integer offset, Integer limit, String order,
                                Map<String, Object> context, String... columns) throws OeRpcException {

        String reqUrl = url.setPath(SEARCH_READ.getPath()).setParameter("session_id", sessionId).toString();
        JsonObject params = new JsonObject();
        params.addProperty("model", model);

        if (columns != null && columns.length != 0) {
            params.add("fields", OeUtil.parseAsJsonArray(columns));
        } else {
            params.add("fields", new JsonArray());
        }

        if (domain != null) {
            params.add("domain", OeUtil.parseAsJsonArray(domain));
        } else {
            params.add("domain", new JsonArray());
        }

        if (context != null) {
            params.add("context", OeUtil.parseAsJsonObject(context));
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

        JsonObject response = postRequest(reqUrl, getCallWith(params));
        OeRpcException.checkJsonResponse(response);
        return response.getAsJsonObject("result").getAsJsonArray("records").toString();
    }

    public JsonArray searchRead(String model, List<Object> domain, Integer offset, Integer limit, String order,
                                Map<String, Object> context, String... columns) throws OeRpcException {
        return OeUtil.parseAsJsonArray(searchReadStr(model, domain, offset, limit, order, context, columns));
    }

    public Map<String, Object>[] searchReadMap(String model, List<Object> domain, Integer offset, Integer limit,
                                               String order, Map<String, Object> context, String... columns)
            throws OeRpcException {
        return OeUtil.convertJsonArrayToMapArray(searchRead(model, domain, offset, limit, order, context, columns));
    }

    public Long count(String model, List<Object> domain) throws OeRpcException {
        JsonArray argms = OeUtil.parseAsJsonArray(domain);
        Object result = execute(model, SEARCH_COUNT.getName(), argms);
        if (!OeUtil.isNULL(result)) {
            return Long.parseLong(result.toString());
        }
        return 0L;
    }

    public Long count(OeModel model, List<Object> domain) throws OeRpcException {
        return count(model.getName(), domain);
    }

    public Long create(String model, Map<String, Object> vals) throws OeRpcException {
        JsonArray argms = new JsonArray();
        argms.add(OeUtil.parseAsJsonElement(vals));
        Object result = execute(model, CREATE.getName(), argms);
        if (!OeUtil.isNULL(result)) {
            return Long.parseLong(result.toString());
        }
        return 0L;
    }

    public Boolean write(String model, Integer id, Map<String, Object> vals) throws OeRpcException {
        JsonArray argms = new JsonArray();
        argms.add(id);
        argms.add(OeUtil.parseAsJsonElement(vals));
        Object result = execute(model, WRITE.getName(), argms);
        return !OeUtil.isNULL(result) && Boolean.parseBoolean(result.toString());
    }

    public Long create(OeModel model, Map<String, Object> vals) throws OeRpcException {
        return create(model.getName(), vals);
    }

    public Object execute(String model, String method) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(jsonContext);
        return execute(model, method, args);
    }

    public Object execute(String model, String method, JsonArray args) throws OeRpcException {
        return execute(model, method, args, new JsonObject());
    }

    public Object execute(String fun, JsonObject jsonObj) throws OeRpcException {
        String reqUrl = url.setPath(fun).setParameter("session_id", sessionId).toString();
        return postRequest(reqUrl, getCallWith(jsonObj)).get("result");
    }

    public Object execute(String model, String method, JsonArray args, JsonObject kwargs) throws OeRpcException {
        String reqUrl = url.setPath(CALL_KW.getPath()).setParameter("session_id", sessionId).toString();
        JsonObject params = new JsonObject();
        params.addProperty("model", model);
        params.addProperty("method", method);
        params.add("args", args);
        params.add("kwargs", kwargs);
        params.add("context", jsonContext);
        if (isV70()) {
            params.addProperty("session_id", sessionId);
        }
        JsonObject response = postRequest(reqUrl, getCallWith(params));
        OeRpcException.checkJsonResponse(response);

        return response.get("result");
    }
}
