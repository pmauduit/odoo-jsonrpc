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
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static org.qfast.openerp.rpc.util.OeUtil.getCallWith;
import static org.qfast.openerp.rpc.util.OeUtil.postRequest;

/**
 * @author Ahmed El-mawaziny
 */
public final class OeDatabase implements Serializable {

    private static final long serialVersionUID = 7443043973211482534L;
    private final String protocol;
    private final String host;
    private final int port;
    private final String url;
    private final JSONObject emptyObject = new JSONObject();
    private Object adminPwd;

    public OeDatabase(String protocol, String host, int port, Object adminPwd) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.adminPwd = adminPwd;
        this.url = protocol + "://" + host + ":" + port + "/web/database";
    }

    public OeDatabase(String protocol, String host, int port) {
        this(protocol, host, port, Boolean.FALSE);
    }

    public OeDatabase(String host) {
        this("http", host, 8069);
    }

    public final String[] doList() throws OeRpcException {
        String reqUrl = url + "/get_list";
        JSONObject response = postRequest(reqUrl, getCallWith(emptyObject));
        OeRpcException.checkJsonResponse(response);
        return OeUtil.convertJsonArray(response.getJSONArray("result"), String[].class);
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

    private Object getAdminPwd() {
        if (adminPwd == null) {
            adminPwd = Boolean.FALSE;
        }
        return adminPwd;
    }

    public boolean doCreate(String databaseName, boolean demo, String lang, String password) throws OeRpcException {
        String reqUrl = url + "/create";

        JSONObject super_admin_pwd = new JSONObject();
        super_admin_pwd.put("name", "super_admin_pwd");
        super_admin_pwd.put("value", getAdminPwd().toString());

        JSONObject databaseNameObj = new JSONObject();
        databaseNameObj.put("name", "db_name");
        databaseNameObj.put("value", databaseName);

        JSONObject demoDataObj = new JSONObject();
        demoDataObj.put("name", "demo_data");
        demoDataObj.put("value", demo);

        JSONObject dbLangObj = new JSONObject();
        dbLangObj.put("name", "db_lang");
        dbLangObj.put("value", lang);

        JSONObject createAdminPwdObj = new JSONObject();
        createAdminPwdObj.put("name", "create_admin_pwd");
        createAdminPwdObj.put("value", password);

        JSONArray fieldsArr = new JSONArray();
        fieldsArr.put(super_admin_pwd);
        fieldsArr.put(databaseNameObj);
        fieldsArr.put(demoDataObj);
        fieldsArr.put(dbLangObj);
        fieldsArr.put(createAdminPwdObj);

        JSONObject fields = new JSONObject();
        fields.put("fields", fieldsArr);

        JSONObject response = postRequest(reqUrl, getCallWith(fields));
        OeRpcException.checkJsonResponse(response);
        return response.getBoolean("result");
    }

    public boolean doDrop(String databaseName) throws OeRpcException {
        String reqUrl = url + "/drop";
        JSONObject dropPwdObj = new JSONObject();
        dropPwdObj.put("name", "drop_pwd");
        dropPwdObj.put("value", getAdminPwd().toString());

        JSONObject dropDbObj = new JSONObject();
        dropDbObj.put("name", "drop_db");
        dropDbObj.put("value", databaseName);

        JSONArray fieldsArr = new JSONArray();
        fieldsArr.put(dropPwdObj);
        fieldsArr.put(dropDbObj);

        JSONObject fields = new JSONObject();
        fields.put("fields", fieldsArr);

        JSONObject response = postRequest(reqUrl, getCallWith(fields));
        OeRpcException.checkJsonResponse(response);
        return response.getBoolean("result");
    }

    public byte[] doDump(String databaseName) throws OeRpcException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void doDump(String databaseName, String backupPath)
            throws OeRpcException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void doRestore(String newDbName, File backupFile)
            throws IOException, OeRpcException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean doDuplicate(String databaseName, String newDatabaseName) throws OeRpcException {
        String reqUrl = url + "/duplicate";

        JSONObject superAdminPwdObj = new JSONObject();
        superAdminPwdObj.put("name", "super_admin_pwd");
        superAdminPwdObj.put("value", getAdminPwd().toString());

        JSONObject dbOriginalNameObj = new JSONObject();
        dbOriginalNameObj.put("name", "db_original_name");
        dbOriginalNameObj.put("value", databaseName);

        JSONObject dbNameObj = new JSONObject();
        dbNameObj.put("name", "db_name");
        dbNameObj.put("value", newDatabaseName);

        JSONArray fieldsArr = new JSONArray();
        fieldsArr.put(superAdminPwdObj);
        fieldsArr.put(dbOriginalNameObj);

        JSONObject fields = new JSONObject();
        fields.put("fields", fieldsArr);

        JSONObject response = postRequest(reqUrl, getCallWith(fields));
        OeRpcException.checkJsonResponse(response);
        return response.getBoolean("result");
    }
}
