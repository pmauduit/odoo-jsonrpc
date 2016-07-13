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
import com.odoo.rpc.exception.OeRpcException;
import com.odoo.rpc.json.util.OeJsonObject;
import com.odoo.rpc.json.util.OeJsonUtil;
import org.apache.http.client.utils.URIBuilder;

import java.io.Serializable;

import static com.odoo.rpc.OeConst.JsonDatabase.CREATE;
import static com.odoo.rpc.OeConst.JsonDatabase.DROP;
import static com.odoo.rpc.OeConst.JsonDatabase.DUPLICATE;
import static com.odoo.rpc.OeConst.JsonDatabase.GET_LIST;
import static com.odoo.rpc.json.util.HttpClient.postWithParams;

/**
 * OeDatabase class is for manage odoo databases
 *
 * @author Ahmed El-mawaziny
 * @since 1.0
 */
public class OeDatabase implements Serializable {

    private static final long serialVersionUID = 7443043973211482534L;
    private static volatile OeDatabase instance;
    private final String scheme;
    private final String host;
    private final int port;
    private final URIBuilder url;
    private final JsonObject emptyObject = new JsonObject();
    private final Object adminPwd;

    /**
     * Constructor to build uri
     *
     * @param scheme   http or https
     * @param host     host name or ip address
     * @param port     port number
     * @param adminPwd admin password
     */
    private OeDatabase(String scheme, String host, int port, Object adminPwd) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.adminPwd = adminPwd;
        this.url = new URIBuilder().setScheme(scheme).setHost(host).setPort(port);
    }

    /**
     * Constructor to build uri with no admin password
     *
     * @param scheme http or https
     * @param host   host name or ip address
     * @param port   port number
     */
    private OeDatabase(String scheme, String host, int port) {
        this(scheme, host, port, Boolean.FALSE);
    }

    /**
     * Constructor to build uri without admin password, http and 8069
     *
     * @param host host name or ip password
     */
    private OeDatabase(String host) {
        this("http", host, 8069);
    }

    /**
     * Constructor to build uri
     *
     * @param scheme   http or https
     * @param host     host name or ip address
     * @param port     port number
     * @param adminPwd admin password
     */
    public static OeDatabase getInstance(String scheme, String host, int port, Object adminPwd) {
        if (instance == null) {
            synchronized (OeDatabase.class) {
                if (instance == null) {
                    instance = new OeDatabase(scheme, host, port, adminPwd);
                }
            }
        }
        return instance;
    }

    /**
     * Constructor to build uri
     *
     * @param scheme   http or https
     * @param host     host name or ip address
     * @param port     port number
     * @param adminPwd admin password
     */
    public synchronized static OeDatabase getNewInstance(String scheme, String host, int port, Object adminPwd) {
        instance = new OeDatabase(scheme, host, port, adminPwd);
        return instance;
    }

    /**
     * Constructor to build uri with no admin password
     *
     * @param scheme http or https
     * @param host   host name or ip address
     * @param port   port number
     */
    public static OeDatabase getInstance(String scheme, String host, int port) {
        if (instance == null) {
            synchronized (OeDatabase.class) {
                if (instance == null) {
                    instance = new OeDatabase(scheme, host, port);
                }
            }
        }
        return instance;
    }

    /**
     * Constructor to build uri with no admin password
     *
     * @param scheme http or https
     * @param host   host name or ip address
     * @param port   port number
     */
    public synchronized static OeDatabase getNewInstance(String scheme, String host, int port) {
        instance = new OeDatabase(scheme, host, port);
        return instance;
    }

    /**
     * Constructor to build uri without admin password, http and 8069
     *
     * @param host host name or ip password
     */
    public static OeDatabase getInstance(String host) {
        if (instance == null) {
            synchronized (OeDatabase.class) {
                if (instance == null) {
                    instance = new OeDatabase(host);
                }
            }
        }
        return instance;
    }

    /**
     * Constructor to build uri without admin password, http and 8069
     *
     * @param host host name or ip password
     */
    public synchronized static OeDatabase getNewInstance(String host) {
        instance = new OeDatabase(host);
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

    private Object getAdminPwd() {
        return adminPwd;
    }

    /**
     * list all databases
     *
     * @return array of string with databases name
     * @throws OeRpcException
     */
    public final String[] doList() throws OeRpcException {
        String reqUrl = url.setPath(GET_LIST.getPath()).toString();
        JsonObject response = postWithParams(reqUrl, emptyObject);
        return OeJsonUtil.convertJsonArray(new OeJsonObject(response).getAsJsonArray("result"), String[].class);
    }

    /**
     * create new database
     *
     * @param databaseName the name of database
     * @param demo         insert demo data
     * @param lang         database language
     * @param password     database admin password
     * @return true if created
     * @throws OeRpcException
     */
    public boolean doCreate(String databaseName, boolean demo, String lang, String password) throws OeRpcException {
        String reqUrl = url.setPath(CREATE.getPath()).toString();

        JsonObject super_admin_pwd = new JsonObject();
        super_admin_pwd.addProperty("name", "super_admin_pwd");
        super_admin_pwd.addProperty("value", getAdminPwd().toString());

        JsonObject databaseNameObj = new JsonObject();
        databaseNameObj.addProperty("name", "db_name");
        databaseNameObj.addProperty("value", databaseName);

        JsonObject demoDataObj = new JsonObject();
        demoDataObj.addProperty("name", "demo_data");
        demoDataObj.addProperty("value", demo);

        JsonObject dbLangObj = new JsonObject();
        dbLangObj.addProperty("name", "db_lang");
        dbLangObj.addProperty("value", lang);

        JsonObject createAdminPwdObj = new JsonObject();
        createAdminPwdObj.addProperty("name", "create_admin_pwd");
        createAdminPwdObj.addProperty("value", password);

        JsonArray fieldsArr = new JsonArray();
        fieldsArr.add(super_admin_pwd);
        fieldsArr.add(databaseNameObj);
        fieldsArr.add(demoDataObj);
        fieldsArr.add(dbLangObj);
        fieldsArr.add(createAdminPwdObj);

        JsonObject fields = new JsonObject();
        fields.add("fields", fieldsArr);

        JsonObject response = postWithParams(reqUrl, fields);
        return new OeJsonObject(response).get("result").getAsBoolean();
    }

    /**
     * drop database
     *
     * @param databaseName database name to drop it
     * @return true if dropped
     * @throws OeRpcException
     */
    public boolean doDrop(String databaseName) throws OeRpcException {
        String reqUrl = url.setPath(DROP.getPath()).toString();
        JsonObject dropPwdObj = new JsonObject();
        dropPwdObj.addProperty("name", "drop_pwd");
        dropPwdObj.addProperty("value", getAdminPwd().toString());

        JsonObject dropDbObj = new JsonObject();
        dropDbObj.addProperty("name", "drop_db");
        dropDbObj.addProperty("value", databaseName);

        JsonArray fieldsArr = new JsonArray();
        fieldsArr.add(dropPwdObj);
        fieldsArr.add(dropDbObj);

        JsonObject fields = new JsonObject();
        fields.add("fields", fieldsArr);

        JsonObject response = postWithParams(reqUrl, fields);
        return new OeJsonObject(response).get("result").getAsBoolean();
    }

    /**
     * duplicate database with new name
     *
     * @param databaseName    database name to duplicate
     * @param newDatabaseName the duplicated database name
     * @return true if duplicated
     * @throws OeRpcException
     */
    public boolean doDuplicate(String databaseName, String newDatabaseName) throws OeRpcException {
        String reqUrl = url.setPath(DUPLICATE.getPath()).toString();

        JsonObject superAdminPwdObj = new JsonObject();
        superAdminPwdObj.addProperty("name", "super_admin_pwd");
        superAdminPwdObj.addProperty("value", getAdminPwd().toString());

        JsonObject dbOriginalNameObj = new JsonObject();
        dbOriginalNameObj.addProperty("name", "db_original_name");
        dbOriginalNameObj.addProperty("value", databaseName);

        JsonObject dbNameObj = new JsonObject();
        dbNameObj.addProperty("name", "db_name");
        dbNameObj.addProperty("value", newDatabaseName);

        JsonArray fieldsArr = new JsonArray();
        fieldsArr.add(superAdminPwdObj);
        fieldsArr.add(dbOriginalNameObj);
        fieldsArr.add(dbNameObj);

        JsonObject fields = new JsonObject();
        fields.add("fields", fieldsArr);

        JsonObject response = postWithParams(reqUrl, fields);
        return new OeJsonObject(response).get("result").getAsBoolean();
    }
}
