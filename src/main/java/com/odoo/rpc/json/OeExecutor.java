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
import com.odoo.rpc.util.OeConst.OeModel;
import com.odoo.rpc.util.OeUtil;
import org.apache.http.client.utils.URIBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.odoo.rpc.json.util.HttpClient.postWithParams;
import static com.odoo.rpc.json.util.OeJEndPoint.DataSet.CALL_KW;
import static com.odoo.rpc.json.util.OeJEndPoint.DataSet.SEARCH_READ;
import static com.odoo.rpc.json.util.OeJEndPoint.Session.AUTHENTICATE;
import static com.odoo.rpc.json.util.OeJEndPoint.Session.DESTROY;
import static com.odoo.rpc.json.util.OeJUtil.convertJsonArrayToMapArray;
import static com.odoo.rpc.json.util.OeJUtil.convertJsonToMap;
import static com.odoo.rpc.json.util.OeJUtil.parseAsJsonArray;
import static com.odoo.rpc.json.util.OeJUtil.parseAsJsonElement;
import static com.odoo.rpc.json.util.OeJUtil.parseAsJsonObject;
import static com.odoo.rpc.util.OeConst.OeFun.CREATE;
import static com.odoo.rpc.util.OeConst.OeFun.SEARCH_COUNT;
import static com.odoo.rpc.util.OeConst.OeFun.UNLINK;
import static com.odoo.rpc.util.OeConst.OeFun.WRITE;

/**
 * Executor class is the main executor for all lib operations
 * <p>
 * search
 * read
 * write
 * create
 * delete
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
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

    /**
     * Constructor for creating connection with Odoo then login and get uid, session_id and context
     *
     * @param scheme   http or https
     * @param host     host name or ip address
     * @param port     port number
     * @param database database name
     * @param username login username
     * @param password login password
     * @throws OeRpcException if Odoo response with error
     */
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

    /**
     * Constructor for creating connection with Odoo then login and get uid, session_id and context
     *
     * @param host     host name or ip address
     * @param port     port number
     * @param database database
     * @param username login username
     * @param password login password
     * @throws OeRpcException if Odoo response with error
     */
    private OeExecutor(String host, int port, String database, String username, String password) throws OeRpcException {
        this("http", host, port, database, username, password);
    }

    /**
     * Constructor for creating connection with Odoo then login and get uid, session_id and context
     *
     * @param host     host name or ip address
     * @param database database name
     * @param username login username
     * @param password login password
     * @throws OeRpcException if Odoo response with error
     */
    private OeExecutor(String host, String database, String username, String password) throws OeRpcException {
        this("http", host, 8069, database, username, password);
    }

    /**
     * static for getting singleton instance for creating connection with Odoo then login and get uid, session_id
     * and context
     *
     * @param scheme   http or https
     * @param host     host name or ip address
     * @param port     port number
     * @param database database name
     * @param username login username
     * @param password login password
     * @return singleton instance
     * @throws OeRpcException if Odoo response with error
     */
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

    /**
     * static for getting singleton instance for creating connection with Odoo then login and get uid, session_id
     * and context
     *
     * @param host     host name or ip address
     * @param port     port number
     * @param database database name
     * @param username login username
     * @param password login password
     * @return singleton instance
     * @throws OeRpcException if Odoo response with error
     */
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

    /**
     * static for getting singleton instance for creating connection with Odoo then login and get uid, session_id
     * and context
     *
     * @param host     host name or password
     * @param database database name
     * @param username login username
     * @param password login password
     * @return singleton instance
     * @throws OeRpcException if Odoo response with error
     */
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

    /**
     * login to Odoo server with database, username and password
     *
     * @return Odoo response
     * @throws OeRpcException if Odoo response with error
     */
    private JsonObject doLogin() throws OeRpcException {
        String reqUrl = url.setPath(AUTHENTICATE.getPath()).setParameter("session_id", sessionId).toString();
        JsonObject params = new JsonObject();
        params.addProperty("db", database);
        params.addProperty("login", username);
        params.addProperty("password", password);
        JsonObject response = postWithParams(reqUrl, params);
        return new OeJsonObject(response).getAsJsonObject("result");
    }

    /**
     * logout the logged in user by destroying the session
     */
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

    /**
     * getting user context from Odoo login response
     *
     * @param result login response
     * @return user context as Json Object
     */
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

    /**
     * checking if the Odoo version is 7.0
     *
     * @return true is the version number is 7 and the sub version is 0
     * @throws OeRpcException if Odoo response with error
     */
    public boolean isV70() throws OeRpcException {
        version = getOeVersion();
        return version.getVersionNumber() == 7 && version.getSubVersion() == 0;
    }

    /**
     * getting Odoo version as {@link OeVersion}
     *
     * @return OeVersion
     * @throws OeRpcException if Odoo response with error
     */
    public OeVersion getOeVersion() throws OeRpcException {
        this.version = OeServerVersion.getInstance(scheme, host, port).getVersion();
        return version;
    }

    /**
     * getting session id
     *
     * @return string of session id
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * getting user context as map
     *
     * @return user context as map
     */
    public Map<String, Object> getContext() {
        return context;
    }

    /**
     * setting user context
     *
     * @param context user context as map
     */
    public void setContext(Map<String, Object> context) {
        this.jsonContext = parseAsJsonObject(context);
        this.context = context;
    }

    /**
     * getting user context as json object
     *
     * @return user context as json object
     */
    public JsonObject getJsonContext() {
        return jsonContext;
    }

    /**
     * setting user context as json object
     *
     * @param jsonContext user context as json object
     */
    public void setJsonContext(JsonObject jsonContext) {
        this.context = convertJsonToMap(jsonContext);
        this.jsonContext = jsonContext;
    }

    /**
     * update user context by Map
     *
     * @param params passing params for updating user context as Map
     */
    public void updateContext(Map<String, Object> params) {
        updateJsonContext(params);
        context.putAll(params);
    }

    /**
     * update user context with key and value
     *
     * @param key   key of new input in user context
     * @param value value of the new input in user context
     */
    public void updateContext(String key, Object value) {
        jsonContext.add(key, parseAsJsonElement(value));
        context.put(key, value);
    }

    private void updateJsonContext(Map<String, Object> params) {
        for (String key : params.keySet()) {
            jsonContext.add(key, parseAsJsonElement(params.get(key)));
        }
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

    /**
     * Search then read with specific criteria and columns in Odoo model
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param columns model columns
     * @return Odoo response result as {@code Map<String, Object>[]}
     * @throws OeRpcException if Odoo response with error
     * @see #searchRead(String, List, String, String...)
     */
    public Map<String, Object>[] searchReadMap(String model, List<Object> domain, String... columns)
            throws OeRpcException {
        return searchRead(model, domain, (String) null, columns);
    }

    /**
     * getting number of rows by searching then reading with specific criteria and columns in Odoo model
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param limit   number of rows
     * @param columns model columns
     * @return Odoo response result as {@code Map<String, Object>[]}
     * @throws OeRpcException if Odoo response with error
     * @see #searchRead(String, List, Integer, Integer, String...)
     */
    public Map<String, Object>[] searchRead(String model, List<Object> domain, Integer limit, String... columns)
            throws OeRpcException {
        return searchRead(model, domain, null, limit, columns);
    }

    /**
     * Search then read with specific criteria and columns in Odoo model then order these rows
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param order   order rows asc or desc
     * @param columns model columns
     * @return Odoo response result as {@code Map<String, Object>[]}
     * @throws OeRpcException if Odoo response with error
     * @see #searchRead(String, List, Integer, Integer, String, String...)
     */
    public Map<String, Object>[] searchRead(String model, List<Object> domain, String order, String... columns)
            throws OeRpcException {
        return searchRead(model, domain, null, null, order, columns);
    }

    /**
     * getting number of rows by searching then reading with specific criteria and columns in Odoo model
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param offset  Odoo sql query offset
     * @param limit   number of rows
     * @param columns model columns
     * @return Odoo response result as {@code Map<String, Object>[]}
     * @throws OeRpcException if Odoo response with error
     * @see #searchRead(String, List, Integer, Integer, String, String...)
     */
    public Map<String, Object>[] searchRead(String model, List<Object> domain, Integer offset, Integer limit,
                                            String... columns) throws OeRpcException {
        return searchRead(model, domain, offset, limit, null, columns);
    }

    /**
     * getting number of rows by searching then reading with specific criteria and columns in Odoo model
     * then order these rows
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param offset  Odoo sql query offset
     * @param limit   number of rows
     * @param order   order rows asc or desc
     * @param columns model columns
     * @return Odoo response result as {@code Map<String, Object>[]}
     * @throws OeRpcException if Odoo response with error
     * @see #searchReadMap(String, List, Integer, Integer, String, Map, String...)
     */
    public Map<String, Object>[] searchRead(String model, List<Object> domain, Integer offset, Integer limit,
                                            String order, String... columns) throws OeRpcException {
        return searchReadMap(model, domain, offset, limit, order, context, columns);
    }

    /**
     * getting number of rows by searching then reading with specific criteria and columns in Odoo model
     * then order these rows
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param offset  Odoo sql query offset
     * @param limit   number of rows
     * @param order   order rows asc or desc
     * @param context Odoo context
     * @param columns model columns
     * @return Odoo response result as json array
     * @throws OeRpcException if Odoo response with error
     * @see com.odoo.rpc.json.util.OeJUtil#parseAsJsonArray(Object)
     * @see #searchReadStr(String, List, Integer, Integer, String, Map, String...)
     */
    public JsonArray searchRead(String model, List<Object> domain, Integer offset, Integer limit, String order,
                                Map<String, Object> context, String... columns) throws OeRpcException {
        return parseAsJsonArray(searchReadStr(model, domain, offset, limit, order, context, columns));
    }

    /**
     * Search then read with specific criteria and columns in Odoo model
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param columns model columns
     * @return Odoo response result as json array
     * @throws OeRpcException if Odoo response with error
     * @see com.odoo.rpc.json.util.OeJUtil#parseAsJsonArray(Object)
     * @see #searchReadStr(String, List, Integer, Integer, String, Map, String...)
     */
    public JsonArray searchRead(String model, List<Object> domain, String... columns) throws OeRpcException {
        return parseAsJsonArray(searchReadStr(model, domain, null, null, null, context, columns));
    }

    /**
     * getting number of rows by searching then reading with specific criteria and columns in Odoo model
     * then order these rows
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param offset  Odoo sql query offset
     * @param limit   number of rows
     * @param order   order rows asc or desc
     * @param context Odoo context
     * @param columns model columns
     * @return Odoo response result as {@code Map<String, Object>[]}
     * @throws OeRpcException if Odoo response with error
     * @see com.odoo.rpc.json.util.OeJUtil#convertJsonArrayToMapArray(JsonArray)
     * @see #searchReadStr(String, List, Integer, Integer, String, Map, String...)
     */
    public Map<String, Object>[] searchReadMap(String model, List<Object> domain, Integer offset, Integer limit,
                                               String order, Map<String, Object> context, String... columns)
            throws OeRpcException {
        return convertJsonArrayToMapArray(searchRead(model, domain, offset, limit, order, context, columns));
    }

    /**
     * The main method for composing the search read url and all required parameters then send it to Odoo
     *
     * @param model   Odoo model
     * @param domain  search criteria
     * @param offset  Odoo sql query offset
     * @param limit   number of rows
     * @param order   order rows asc or desc
     * @param context Odoo context
     * @param columns model columns
     * @return Odoo response result as String
     * @throws OeRpcException if Odoo response with error
     */
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

    /**
     * Count Odoo model with specific search criteria
     *
     * @param model  Odoo model of type {@link OeModel}
     * @param domain search criteria
     * @return row count as long
     * @throws OeRpcException if Odoo response with error
     * @see OeModel
     * @see #count(String, List)
     */
    public Long count(OeModel model, List<Object> domain) throws OeRpcException {
        return count(model.getName(), domain);
    }

    /**
     * Count Odoo model with specific search criteria
     *
     * @param model  Odoo model of type String
     * @param domain search criteria
     * @return row count as long
     * @throws OeRpcException if Odoo response with error
     * @see #execute(String, String, JsonArray)
     */
    public Long count(String model, List<Object> domain) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(parseAsJsonElement(domain));
        Object result = execute(model, SEARCH_COUNT.getName(), args);
        if (!OeUtil.isNULL(result)) {
            return Long.parseLong(result.toString());
        }
        return 0L;
    }

    /**
     * Create new record in Odoo model
     *
     * @param model  Odoo model of type {@link OeModel}
     * @param values new record values
     * @return id of the new created record
     * @throws OeRpcException if Odoo response with error
     * @see #create(String, Map)
     */
    public Long create(OeModel model, Map<String, Object> values) throws OeRpcException {
        return create(model.getName(), values);
    }

    /**
     * Create new record in Odoo model
     *
     * @param model  Odoo model of type String
     * @param values new record values
     * @return id of the new created record
     * @throws OeRpcException if Odoo response with error
     * @see #execute(String, String, JsonArray)
     */
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

    /**
     * Update Odoo model record
     *
     * @param model  Odoo model
     * @param id     the record id to update
     * @param values the new record values
     * @return true if its updated successfully
     * @throws OeRpcException if Odoo response with error
     * @see #execute(String, String, JsonArray)
     */
    public Boolean write(String model, Object id, Map<String, Object> values) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(parseAsJsonElement(id));
        args.add(parseAsJsonElement(values));
        Object result = execute(model, WRITE.getName(), args);
        return !OeUtil.isNULL(result) && Boolean.parseBoolean(result.toString());
    }

    /**
     * Delete Odoo model record
     *
     * @param model Odoo model
     * @param ids   one or more ids to delete
     * @return true if its deleted successfully
     * @throws OeRpcException if Odoo response with error
     * @see #execute(String, String, JsonArray)
     */
    public Boolean unlike(String model, Long... ids) throws OeRpcException {
        JsonArray args = new JsonArray();
        args.add(parseAsJsonArray(ids));
        args.add(jsonContext);
        Object result = execute(model, UNLINK.getName(), args);
        return !OeUtil.isNULL(result) && Boolean.parseBoolean(result.toString());
    }

    /**
     * Execute specific method or function in Odoo model
     *
     * @param model  Odoo model
     * @param method the new of Odoo method to execute
     * @return Odoo result as {@code Map<String, Object>}
     * @throws OeRpcException if Odoo response with error
     * @see com.odoo.rpc.json.util.OeJUtil#convertJsonToMap(JsonObject)
     */
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
