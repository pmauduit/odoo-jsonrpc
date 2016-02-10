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
import org.json.JSONArray;
import org.json.JSONObject;
import org.qfast.openerp.rpc.OeConst.JsonDataSet;
import org.qfast.openerp.rpc.OeConst.JsonSession;
import org.qfast.openerp.rpc.OeConst.OeModel;
import org.qfast.openerp.rpc.entity.OeVersion;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.qfast.openerp.rpc.OeConst.OeFun.CREARE;
import static org.qfast.openerp.rpc.OeConst.OeFun.SEARCH_COUNT;
import static org.qfast.openerp.rpc.OeConst.OeFun.WRITE;
import static org.qfast.openerp.rpc.util.OeUtil.getCallWith;
import static org.qfast.openerp.rpc.util.OeUtil.postRequest;

/**
 * @author Ahmed El-mawaziny
 */
public class OeExecutor implements Serializable {

    private static final long serialVersionUID = 7528230097059877102L;
    private final String protocol;
    private final String host;
    private final int port;
    private final String password;
    private final String username;
    private final String database;
    private final Integer userId;
    private final OeVersion version;
    private final String url;
    private final String sessionId;
    private final boolean v70;
    private JSONObject jSONContext;
    private Map<String, Object> context;

    public OeExecutor(String protocol, String host, int port, String database, String username, String password)
            throws OeRpcException {

        this.database = database;
        this.username = username;
        this.password = password;
        this.protocol = protocol;
        this.port = port;
        this.host = host;
        this.url = protocol + "://" + host + ":" + port + "/web";
        JSONObject loginResult = doLogin();
        this.jSONContext = getJsonContextFromLogin(loginResult);
        this.context = OeUtil.convertJsonToMap(jSONContext);
        this.sessionId = loginResult.getString("session_id");
        this.userId = loginResult.getInt("uid");
        this.version = getOeVersion();
        this.v70 = version.getVersionNumber() == 7 && version.getSubVersion() == 0;
    }

    public OeExecutor(String host, int port, String database, String username, String password) throws OeRpcException {
        this("http", host, port, database, username, password);
    }

    public OeExecutor(String host, String database, String username, String password) throws OeRpcException {
        this("http", host, 8069, database, username, password);
    }

    private JSONObject doLogin() throws OeRpcException {
        String reqUrl = url + "/" + JsonSession.AUTHENTICATE.getPath();
        JSONObject params = new JSONObject();
        params.put("db", database);
        params.put("login", username);
        params.put("password", password);
        JSONObject response = postRequest(reqUrl, getCallWith(params));
        OeRpcException.checkJsonResponse(response);
        return response.getJSONObject("result");
    }

    public void logout() throws OeRpcException {
        String reqUrl = url + "/" + JsonSession.DESTROY.getPath();
        JSONObject params = new JSONObject();
        params.put("context", jSONContext);
        JSONObject response = postRequest(reqUrl, getCallWith(params));
        OeRpcException.checkJsonResponse(response);
    }

    private JSONObject getJsonContextFromLogin(JSONObject result) {
        if (result != null) {
            String contextKeyName = "user_context";
            if (!result.has("user_context") && result.has("context")) {
                contextKeyName = "context";
            }
            return new JSONObject(result.getJSONObject(contextKeyName).toString());
        }
        return null;
    }

    private OeVersion getOeVersion() throws OeRpcException {
        return new OeServerVersion(protocol, host, port).getServerVersion();
    }

    public String getSessionId() {
        return sessionId;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        setJSONContext(new JSONObject(context));
        this.context = context;
    }

    public JSONObject getJSONContext() {
        return jSONContext;
    }

    public void setJSONContext(JSONObject jSONContext) {
        setContext(OeUtil.convertJsonToMap(jSONContext));
        this.jSONContext = jSONContext;
    }

    public void updateContext(Map<String, Object> params) {
        updateJSONContext(params);
        context.putAll(params);
    }

    public void updateContext(String key, Object value) {
        jSONContext.put(key, value);
        context.put(key, value);
    }

    public void updateJSONContext(Map<String, Object> params) {
        for (String key : params.keySet()) {
            jSONContext.put(key, params.get(key));
        }
    }

    @SuppressWarnings("unchecked")
    public void updateJSONContext(JSONObject params) {
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            jSONContext.put(key, params.get(key));
        }
    }

    public OeVersion getVersion() {
        return version;
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

    public Map<String, Object>[] doSearch(String model, List<Object> domain) throws OeRpcException {
        return doSearch(model, domain, (String) null);
    }

    public Map<String, Object>[] doSearch(String model, List<Object> domain, Integer limit) throws OeRpcException {
        return doSearch(model, domain, null, limit);
    }

    public Map<String, Object>[] doSearch(String model, List<Object> domain, String order) throws OeRpcException {
        return doSearch(model, domain, null, null, order);
    }

    public Map<String, Object>[] doSearch(String model, List<Object> domain, Integer offset, Integer limit)
            throws OeRpcException {

        return doSearch(model, domain, offset, limit, null);
    }

    public Map<String, Object>[] doSearch(String model, List<Object> domain, Integer offset, Integer limit, String order)
            throws OeRpcException {

        return doSearchMap(model, domain, offset, limit, order, context);
    }

    public String doSearchStr(String model, List<Object> domain, Integer offset, Integer limit, String order,
                              Map<String, Object> context) throws OeRpcException {

        String reqUrl = url + "/" + JsonDataSet.SEARCH_READ.getPath();
        JSONObject params = new JSONObject();
        params.put("model", model);

        if (domain != null) {
            params.put("domain", new JSONArray(new Gson().toJson(domain)));
        } else {
            params.put("domain", new JSONArray());
        }

        if (context != null) {
            params.put("context", new JSONObject(context));
        } else {
            params.put("context", new JSONObject());
        }

        params.put("offset", offset);
        params.put("limit", limit);

        if (order != null) {
            params.put("sort", order);
        } else {
            params.put("sort", "");
        }

        if (v70) {
            params.put("session_id", sessionId);
        }

        JSONObject response = postRequest(reqUrl, getCallWith(params));
        System.out.println(response);
        OeRpcException.checkJsonResponse(response);
        return response.getJSONObject("result").getJSONArray("records").toString();
    }

    public JSONArray doSearch(String model, List<Object> domain, Integer offset, Integer limit, String order,
                              Map<String, Object> context) throws OeRpcException {
        return new JSONArray(doSearchStr(model, domain, offset, limit, order, context));
    }

    public Map<String, Object>[] doSearchMap(String model, List<Object> domain, Integer offset, Integer limit,
                                             String order, Map<String, Object> context) throws OeRpcException {
        return OeUtil.convertJsonArrayToMapArray(doSearch(model, domain, offset, limit, order, context));
    }

    public Long doCount(String model, List<Object> domain) throws OeRpcException {
        JSONArray argms = new JSONArray();
        argms.put(domain);
        Object result = execute(model, SEARCH_COUNT.getName(), argms);
        if (!OeUtil.isNULL(result)) {
            return Long.parseLong(result.toString());
        }
        return 0L;
    }

    public Long doCount(OeModel model, List<Object> domain) throws OeRpcException {
        return doCount(model.getName(), domain);
    }

    public Long doCreate(String model, Map<String, Object> vals) throws OeRpcException {
        JSONArray argms = new JSONArray();
        argms.put(new JSONObject(new Gson().toJson(vals)));
        Object result = execute(model, CREARE.getName(), argms);
        if (!OeUtil.isNULL(result)) {
            return Long.parseLong(result.toString());
        }
        return 0L;
    }

    public Boolean doWrite(String model, Integer id, Map<String, Object> vals) throws OeRpcException {
        JSONArray argms = new JSONArray();
        argms.put(id);
        argms.put(new JSONObject(new Gson().toJson(vals)));
        Object result = execute(model, WRITE.getName(), argms);
        return !OeUtil.isNULL(result) && Boolean.parseBoolean(result.toString());
    }

    public Long doCreate(OeModel model, Map<String, Object> vals)
            throws OeRpcException {
        return doCreate(model.getName(), vals);
    }

    public Object execute(String model, String method) throws OeRpcException {
        JSONArray args = new JSONArray();
        args.put(jSONContext);
        return execute(model, method, args);
    }

    public Object execute(String model, String method, JSONArray args)
            throws OeRpcException {
        return execute(model, method, args, new JSONObject());
    }

    public Object execute(String url, JSONObject jSONObj) throws OeRpcException {
        String reqUrl = this.url + "/" + url;
        return postRequest(reqUrl, getCallWith(jSONObj)).get("result");
    }

    public Object execute(String model, String method, JSONArray args, JSONObject kwargs) throws OeRpcException {
        String reqUrl = url + "/" + JsonDataSet.CALL_KW.getPath();
        JSONObject params = new JSONObject();
        params.put("model", model);
        params.put("method", method);
        params.put("args", args);
        params.put("kwargs", kwargs);
        params.put("context", jSONContext);
        if (v70) {
            params.put("session_id", sessionId);
        }
        JSONObject response = postRequest(reqUrl, getCallWith(params));
        OeRpcException.checkJsonResponse(response);

        return response.get("result");
    }
}
