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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.qfast.openerp.rpc.exception.OeRpcException;
import org.qfast.openerp.rpc.util.OeUtil;
import static org.qfast.openerp.rpc.util.OeUtil.getCallWith;
import static org.qfast.openerp.rpc.util.OeUtil.postRequest;

/**
 * @author Ahmed El-mawaziny
 */
public final class OeDatabase implements Serializable {

    private final Client client;
    private Object adminPwd;
    private final String protocol;
    private final String host;
    private final int port;
    private final String url;
    private final JsonObject emptyObject = Json.createObjectBuilder().build();

    public OeDatabase(String protocol, String host, int port, Object adminPwd) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.adminPwd = adminPwd;
        this.client = ClientBuilder.newClient();
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
        JsonObject response = postRequest(client, reqUrl, getCallWith(emptyObject));
        OeRpcException.checkJsonResponse(response);
        return OeUtil.convertJsonArray(response.getJsonArray("result"), String[].class);
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

    private Object getAdminPwd() {
        if (adminPwd == null) {
            adminPwd = Boolean.FALSE;
        }
        return adminPwd;
    }

    public boolean doCreate(String datbaseName, boolean demo, String lang,
            String password) throws OeRpcException {
        String reqUrl = url + "/create";
        JsonObject fields = Json.createObjectBuilder()
                .add("fields", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("name", "super_admin_pwd")
                                .add("value", getAdminPwd().toString())
                                .build())
                        .add(Json.createObjectBuilder()
                                .add("name", "db_name")
                                .add("value", datbaseName)
                                .build())
                        .add(Json.createObjectBuilder()
                                .add("name", "demo_data")
                                .add("value", demo)
                                .build())
                        .add(Json.createObjectBuilder()
                                .add("name", "db_lang")
                                .add("value", lang)
                                .build())
                        .add(Json.createObjectBuilder()
                                .add("name", "create_admin_pwd")
                                .add("value", password)
                                .build())
                        .build())
                .build();
        JsonObject response = postRequest(client, reqUrl, getCallWith(fields));
        OeRpcException.checkJsonResponse(response);
        return response.getBoolean("result");
    }

    public boolean doDrop(String databaseName) throws OeRpcException {
        String reqUrl = url + "/drop";
        JsonObject fields = Json.createObjectBuilder()
                .add("fields", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("name", "drop_pwd")
                                .add("value", getAdminPwd().toString())
                                .build())
                        .add(Json.createObjectBuilder()
                                .add("name", "drop_db")
                                .add("value", databaseName)
                                .build())
                        .build())
                .build();
        JsonObject response = postRequest(client, reqUrl, getCallWith(fields));
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

    public boolean doDuplicate(String databaseName, String newDatabaseName)
            throws OeRpcException {
        String reqUrl = url + "/duplicate";
        JsonObject fields = Json.createObjectBuilder()
                .add("fields", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("name", "super_admin_pwd")
                                .add("value", getAdminPwd().toString())
                                .build())
                        .add(Json.createObjectBuilder()
                                .add("name", "db_original_name")
                                .add("value", databaseName)
                                .build())
                        .add(Json.createObjectBuilder()
                                .add("name", "db_name")
                                .add("value", newDatabaseName)
                                .build())
                        .build())
                .build();
        JsonObject response = postRequest(client, reqUrl, getCallWith(fields));
        OeRpcException.checkJsonResponse(response);
        return response.getBoolean("result");
    }
    private static final long serialVersionUID = 7443043973211482534L;
}
